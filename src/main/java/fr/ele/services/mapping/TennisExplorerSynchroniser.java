package fr.ele.services.mapping;

import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.model.Bet;
import fr.ele.model.ref.Sport;
import fr.ele.services.mapping.tennisExplorer.MatchParser;
import fr.ele.services.mapping.tennisExplorer.OverUnderMatchParser;
import fr.ele.services.mapping.tennisExplorer.ResultMatchParser;

@Service("TennisExplorerSynchroniser")
public class TennisExplorerSynchroniser extends AbstractSynchronizer<Odds> {

    private static final String URL_MATCH = "http://www.tennisexplorer.com/next/?type=all&";

    private final String TENNIS = "Tennis";

    private final DateFormat formatter = new SimpleDateFormat(
            "yyyy:MM:dd HH:mm");

    @Autowired
    private Proxy proxy;

    @Override
    protected long convert(SynchronizerContext context, Odds dto) {
        // TODO Auto-generated method stub
        long nb = 0L;
        // genrate url_request to retrieve all match of day
        Date date = new Date();
        long year = date.getYear() + 1900;
        long month = date.getMonth() + 1;
        long day = date.getDate();

        try {
            parseNextMatch(URL_MATCH, TENNIS, context, year, month, day,
                    new ResultMatchParser(), new OverUnderMatchParser());
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return nb;
    }

    private void parseNextMatch(String httpRef, String sportType,
            SynchronizerContext context, long year, long month, long day,
            MatchParser... parsers) throws Throwable {
        // TODO Auto-generated method stub
        httpRef = httpRef + "year=" + year + "&month=" + month + "&day=" + day;

        URL website = new URL(httpRef);
        URLConnection urlConnetion = website.openConnection(proxy);
        Document doc = Jsoup
                .parse(urlConnetion.getInputStream(), null, httpRef);

        Elements e = doc.select("tr");
        // Define sport
        Sport sport = context.findSport(sportType);

        // Search URL for each match
        for (Element t : e) {
            if (isActiveLink(t)) {

                Elements ele = t.select("td");

                for (Element p : ele) {
                    if (p.attr("class").equals("first time")) {
                        String strTmp = year + ":" + month + ":" + day + " "
                                + p.text();
                        System.out.println(p.text());
                        if (p.text().contains("--") == false) {
                            Date date = formatter.parse(strTmp);
                            Date time = new Date();

                            if (date.compareTo(time) > 0) {
                                Iterator<Element> it = ele.iterator();
                                while (it.hasNext()) {
                                    Element elementIt = it.next();

                                    if (elementIt.attr("rowspan").equals("2")) {
                                        Element link = elementIt.select("a")
                                                .first();
                                        if (link != null
                                                && link.attr("href").contains(
                                                        "detail")) {
                                            String linkHref = "http://www.tennisexplorer.com"
                                                    + link.attr("href");

                                            List<Bet> bets = new LinkedList<Bet>();
                                            for (fr.ele.services.mapping.tennisExplorer.MatchParser parser : parsers) {
                                                bets.addAll(parser
                                                        .parseMatchId(linkHref,
                                                                context, sport,
                                                                date));

                                            }
                                            for (Bet bet : bets) {
                                                saveBet(bet);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    protected Class<Odds> getDtoClass() {
        // TODO Auto-generated method stub
        return Odds.class;
    }

    private boolean isActiveLink(Element element) {

        return element.toString().contains("rowspan");

    }

}
