package fr.ele.services.mapping;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.cxf.common.i18n.Exception;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import fr.ele.feeds.HtmlBetDto;
import fr.ele.feeds.HtmlBetDtos;
import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;
import fr.ele.services.mapping.betExplorer.AsianHandicapParser;
import fr.ele.services.mapping.betExplorer.MatchParser;
import fr.ele.services.mapping.betExplorer.OverUnderMatchParser;
import fr.ele.services.mapping.betExplorer.ResultMatchParser;

@Service("BetExplorerSynchroniser")
public class BetExplorerSynchroniser extends AbstractSynchronizer<HtmlBetDtos> {

	private static final String URL_MATCH = "http://www.betexplorer.com/gres/ajax-matchodds.php?t=n&e=";

	private final String HANDBALL = "Handball";

	private final String VOLLEYBALL = "Volleyball";

	private final String BASKETBALL = "Basketball";

	private final String FOOTBALL = "Football";

	private final String HOCKEY = "Hockey";

	private final String BASEBALL = "Baseball";

	private SynchronizerContext contextdefaut;

	@Override
	protected long convert(SynchronizerContext context, HtmlBetDtos dto) {
		long nb = 0L;
		contextdefaut = context;
		Sport sport = context.findSport(dto.getSport());
		LOGGER.debug("insert sport in database :" + dto.getSport() + new Date()
				+ "\n");

		TreeMap<String, HtmlBetDto> sortHtml = dto.getDtosBetType();

		// Iterator<> it=sortHtml.
		for (int i = 0; i < sortHtml.size(); i++) {
			// if(sortHtml.)

		}

		for (HtmlBetDto htmlBetDto : dto.getDtos()) {
			// Here transformation htmlbetDto to bet object

			System.out
					.println(htmlBetDto.getBetType() + " "
							+ htmlBetDto.getSubType() + " "
							+ htmlBetDto.getBookmaker());
			Bet bet = new Bet();

			// loop on betType

			BetType betType = context.findBetType(htmlBetDto.getBetType());
			if (betType != null) {
				Match match = context.findOrCreateMatch(sport,
						htmlBetDto.getDate(), htmlBetDto.getPlayer1(),
						htmlBetDto.getPlayer2());

				RefKey refkey = context.findOrCreateRefKey(match, betType);
				context.setBookmaker(htmlBetDto.getBookmaker().substring(1,
						htmlBetDto.getBookmaker().length()));
				if (context.getBookMaker() != null) {
					bet.setBookMaker(context.getBookMaker());
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

	private List<HtmlBetDto> parseNextMatch(String httpRef, String sportType,
			SynchronizerContext context, MatchParser... parsers)
			throws Throwable {
		// TODO Auto-generated method stub

		URL website = new URL(httpRef);
		URLConnection urlConnetion = website.openConnection(getProxy());
		Document doc = Jsoup
				.parse(urlConnetion.getInputStream(), null, httpRef);

		// Document doc = Jsoup.connect(httpRef).get();
		org.jsoup.select.Elements e = doc.select("tr");
		// Define sport
		// Sport sport = context.findSport(sportType);

		// Search URL for each match
		List<Callable<List<HtmlBetDto>>> runnables = new ArrayList<Callable<List<HtmlBetDto>>>();
		for (Element t : e) {
			String test = t.attr("data-dt").replace(",", ":");

			if (t.attr("class").equals("rtitle league-title") == false) {
				Element link = t.select("a").first();

				if (link != null) {
					String linkHref = link.attr("href");
					t.select("span.Postponed").text();
					if (linkHref.isEmpty() == false) {

						DateFormat formatter = new SimpleDateFormat(
								"dd:MM:yyyy:hh:mm");
						if (test.isEmpty() == false || test.equals("") == false) {
							Date date = formatter.parse(test);
							Date timeSQL = new Date();
							if (date.compareTo(timeSQL) > 0) {
								String extract = linkHref.substring(
										linkHref.length() - 9,
										linkHref.length() - 1);
								// before, need to retrive match team1 vs team
								// 2;

								String teams = t.select("td").get(1).text();
								if (teams != null) {
									String[] players = teams.split(" - ");
									String linkOdd = URL_MATCH + extract;

									for (MatchParser parser : parsers) {

										runnables.add(new ProcessHtmlDtos(
												parser, sportType, teams,
												linkOdd, date));

										/**
										 * hread thread = new Thread( new
										 * ProcessHtmlDtos(parser, sportType,
										 * teams, linkOdd, date));
										 * thread.start(); thread.join(); /**
										 * bets.addAll(parser
										 * .parseMatchId(linkOdd, teams,
										 * sportType, date));
										 */

									}

								}
							}
						}
					}
				}
			}
		}
		ExecutorService service = Executors.newFixedThreadPool(100);
		List<Future<List<HtmlBetDto>>> futures = new ArrayList<>(
				runnables.size());
		for (Callable<List<HtmlBetDto>> job : runnables) {
			futures.add(service.submit(job));
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

	private Proxy getProxy() throws Throwable {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"gecd-proxy.equities.net.intra", 8080));
		return proxy;
	}

	@Override
	protected Class<HtmlBetDtos> getDtoClass() {
		return HtmlBetDtos.class;
	}

	@Override
	public HtmlBetDtos unmarshall(String urlBase, String encoding)
			throws Exception {
		List<HtmlBetDto> dtos = new LinkedList<HtmlBetDto>();
		Date date = new Date();
		long year = date.getYear() + 1900;
		long month = date.getMonth() + 1;
		long day = date.getDate();

		try {
			LOGGER.info("Synchroniser Football starts at :" + new Date() + "\n");
			dtos.addAll(parseNextMatch(
					"http://www.betexplorer.com/next/soccer/?" + "year=" + year
							+ "&month=" + month + "&day=" + day, FOOTBALL,
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
			 * "\n");
			 * 
			 * LOGGER.info("Synchroniser basketball starts at :" + new Date() +
			 * "\n"); dtos.addAll(parseNextMatch(
			 * "http://www.betexplorer.com/next/basketball/?" + "year=" + year +
			 * "&month=" + month + "&day=" + day, BASKETBALL, contextdefaut, new
			 * OverUnderMatchParser(), new WinnerMatchParser(), new
			 * AsianHandicapParser()));
			 * LOGGER.info("Synchroniser basketball finish at :" + new Date() +
			 * "\n");
			 * 
			 * LOGGER.info("Synchroniser handball starts at :" + new Date() +
			 * "\n"); dtos.addAll(parseNextMatch(
			 * "http://www.betexplorer.com/next/handball/?" + "year=" + year +
			 * "&month=" + month + "&day=" + day, HANDBALL, contextdefaut, new
			 * OverUnderMatchParser(), new ResultMatchParser(), new
			 * AsianHandicapParser()));
			 * LOGGER.info("Synchroniser handball finish at :" + new Date() +
			 * "\n");
			 **/
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HtmlBetDtos zob = new HtmlBetDtos();
		zob.setDtos(dtos);
		return zob;
	}

	private class ProcessHtmlDtos implements Callable<List<HtmlBetDto>> {

		private final Date date;
		private final String sport, teams, url;
		private final MatchParser parser;

		private ProcessHtmlDtos(MatchParser parser, String sport, String teams,
				String url, Date date) {
			this.date = date;
			this.sport = sport;
			this.teams = teams;
			this.url = url;
			this.parser = parser;
		}

		@Override
		public List<HtmlBetDto> call() {
			try {
				return (parser.parseMatchId(this.url, this.teams, this.sport,
						this.date));
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}
	}

}
