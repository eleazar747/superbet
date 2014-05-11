package fr.ele.services.mapping;

import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.common.i18n.Exception;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import fr.ele.feeds.HtmlBetDto;
import fr.ele.feeds.HtmlBetDtos;
import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;
import fr.ele.services.mapping.betExplorer.AsianHandicapParser;
import fr.ele.services.mapping.betExplorer.MatchParser;
import fr.ele.services.mapping.betExplorer.OverUnderMatchParser;
import fr.ele.services.mapping.betExplorer.ResultMatchParser;
import fr.ele.services.repositories.BookMakerRepository;

@Service("BetExplorerSynchroniser")
public class BetExplorerSynchroniser extends AbstractSynchronizer<HtmlBetDtos> {

    private static final String URL_MATCH = "http://www.betexplorer.com/gres/ajax-matchodds.php?t=n&e=";

    private SynchronizerContext contextdefaut;

    @Autowired
    private Proxy proxy;

    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Override
    protected long convert(SynchronizerContext context, HtmlBetDtos dto) {
        long nb = 0L;
        contextdefaut = context;
        Sport sport = context.findSport(dto.getSport());
        Map<String, BookMaker> bookmakers = cacheBookmakers();
        LOGGER.debug("insert sport in database :" + dto.getSport() + new Date()
                + "\n");

        for (HtmlBetDto htmlBetDto : dto.getDtos()) {
            // Here transformation htmlbetDto to bet object
            Bet bet = new Bet();

            BetType betType = context.findBetType(htmlBetDto.getBetType());
            if (betType != null) {
                Match match = context.findOrCreateMatch(sport,
                        htmlBetDto.getDate(), htmlBetDto.getPlayer1(),
                        htmlBetDto.getPlayer2());

                RefKey refkey = context.findOrCreateRefKey(match, betType);
                BookMaker bookMaker = bookmakers.get(htmlBetDto.getBookmaker()
                        .substring(1, htmlBetDto.getBookmaker().length()));
                if (bookMaker != null) {
                    bet.setBookMaker(bookMaker);
                    bet.setOdd(htmlBetDto.getOdd());
                    bet.setRefKey(refkey);
                    bet.setDate(htmlBetDto.getDate());
                    bet.setCode(htmlBetDto.getSubType());
                    bet.setBookmakerBetId(htmlBetDto.getBookmakerId());
                    saveBet(bet);
                }
            }
        }
        LOGGER.debug("Finished sport in database :" + dto.getSport()
                + new Date() + "\n");
        return nb;
    }

    private Map<String, BookMaker> cacheBookmakers() {
        List<BookMaker> bookmakers = bookMakerRepository.findAll();
        Map<String, BookMaker> cache = new HashMap<>(bookmakers.size());
        for (BookMaker bookMaker : bookmakers) {
            cache.put(bookMaker.getCode(), bookMaker);
        }
        return cache;
    }

    private List<HtmlBetDto> parseSport(String httpRef, final String sportType,
            SynchronizerContext context, final MatchParser... parsers) throws Throwable {

        URL website = new URL(httpRef);
        URLConnection urlConnetion = website.openConnection(proxy);
        Document doc = Jsoup
                .parse(urlConnetion.getInputStream(), null, httpRef);

        // Search URL for each match
        final List<Future<List<HtmlBetDto>>> futures = new ArrayList<>();
        final ExecutorService service = Executors.newFixedThreadPool(100);
        final Date now = new Date();
        for (Element t : doc.select("tr")) {
            String test = t.attr("data-dt").replace(",", ":");
            if (!t.attr("class").equals("rtitle league-title")) {
                Element link = t.select("a").first();
                if (link != null) {
                    String linkHref = link.attr("href");
                    if (!linkHref.isEmpty() && StringUtils.isNotBlank(test)) {
                        Date date;
                        try {
                            DateFormat formatter = new SimpleDateFormat(
                                    "dd:MM:yyyy:hh:mm");
                            date = formatter.parse(test);
                            if (date.after(now)) {
                                String teams = t.select("td").get(1).text();
                                if (teams != null) {
                                    String extract = linkHref.substring(
                                            linkHref.length() - 9,
                                            linkHref.length() - 1);
                                    String linkOdd = URL_MATCH + extract;
                                    for (MatchParser parser : parsers) {
                                        ProcessHtmlDtos task = new ProcessHtmlDtos(
                                                parser, proxy, sportType,
                                                teams, linkOdd, date);
                                        futures.add(service.submit(task));
                                    }
                                }
                            }
                        } catch (ParseException e) {
                            LOGGER.error(e.getLocalizedMessage(), e);
                        }
                    }
                }
            }
        }
        service.shutdown();
        service.awaitTermination(300, TimeUnit.SECONDS);
        List<HtmlBetDto> dtos = new LinkedList<>();
        for (Future<List<HtmlBetDto>> future : futures) {
            try {
                List<HtmlBetDto> list = future.get();
                dtos.addAll(list);
            } catch (Throwable t) {
                LOGGER.error(t.getLocalizedMessage(), t);
            }
        }
        return dtos;
    }

    @Override
    protected Class<HtmlBetDtos> getDtoClass() {
        return HtmlBetDtos.class;
    }

    @Override
    public HtmlBetDtos unmarshall(String urlBase, String encoding) throws Exception {
        List<HtmlBetDto> dtos = new LinkedList<>();
        Date date = new Date();
        long year = date.getYear() + 1900;
        long month = date.getMonth() + 1;
        long day = date.getDate();

        try {
            LOGGER.info("Synchroniser Football starts at :" + new Date() + "\n");
            dtos.addAll(parseSport(urlBase + "soccer/?" + "year=" + year
                    + "&month=" + month + "&day=" + day, "Football",
                    contextdefaut, new OverUnderMatchParser(),
                    new ResultMatchParser(), new AsianHandicapParser()));
            LOGGER.info("Synchroniser Football finish at :" + new Date() + "\n");
            /**
             * LOGGER.info("Synchroniser Vollayball starts at :" + new Date() +
             * "\n"); dtos.addAll(parseNextMatch(
             * "http://www.betexplorer.com/next/volleyball/?" + "year=" + year +
             * "&month=" + month + "&day=" + day, VOLLEYBALL, contextdefaut, new
             * OverUnderMatchParser(), new WinnerMatchParser(), new
             * AsianHandicapParser()));
             * LOGGER.info("Synchroniser Volleyball finish at :" + new Date() +
             * "\n"); LOGGER.info("Synchroniser basketball starts at :" + new
             * Date() + "\n"); dtos.addAll(parseNextMatch(
             * "http://www.betexplorer.com/next/basketball/?" + "year=" + year +
             * "&month=" + month + "&day=" + day, BASKETBALL, contextdefaut, new
             * OverUnderMatchParser(), new WinnerMatchParser(), new
             * AsianHandicapParser()));
             * LOGGER.info("Synchroniser basketball finish at :" + new Date() +
             * "\n"); LOGGER.info("Synchroniser handball starts at :" + new
             * Date() + "\n"); dtos.addAll(parseNextMatch(
             * "http://www.betexplorer.com/next/handball/?" + "year=" + year +
             * "&month=" + month + "&day=" + day, HANDBALL, contextdefaut, new
             * OverUnderMatchParser(), new ResultMatchParser(), new
             * AsianHandicapParser()));
             * LOGGER.info("Synchroniser handball finish at :" + new Date() +
             * "\n");
             **/
        } catch (Throwable e) {
            e.printStackTrace();
        }

        HtmlBetDtos htmlDtos = new HtmlBetDtos();
        htmlDtos.setDtos(dtos);
        return htmlDtos;
    }

    private static class ProcessHtmlDtos implements Callable<List<HtmlBetDto>> {

        private static final Logger LOG = LoggerFactory
                .getLogger(ProcessHtmlDtos.class);

        private final Date date;

        private final String sport, teams, url;

        private final MatchParser parser;

        private final Proxy proxy;

        private ProcessHtmlDtos(MatchParser parser, Proxy proxy, String sport,
                String teams, String url, Date date) {
            this.date = date;
            this.sport = sport;
            this.teams = teams;
            this.url = url;
            this.parser = parser;
            this.proxy = proxy;
        }

        @Override
        public List<HtmlBetDto> call() {
            long time = System.currentTimeMillis();
            String work = MessageFormat.format(
                    "Work url:{0}, sport:{1}, teams:{2}", url, sport, teams);
            try {
                LOG.debug("Start work {}", work);
                return parser.parseMatchId(proxy, url, teams, sport, date);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } finally {
                LOG.debug("End work {} in {}ms", work,
                        System.currentTimeMillis() - time);
            }
        }
    }

}
