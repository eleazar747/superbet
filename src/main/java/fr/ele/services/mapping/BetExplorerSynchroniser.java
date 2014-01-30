package fr.ele.services.mapping;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;

@Service("BetExplorerSynchroniser")
public class BetExplorerSynchroniser extends AbstractSynchronizer<Odds> {

	private final String matchId = "matchid=";
	private int matchCount = 0;
	private final String FOOTBALL = "Football";
	private final String BASEBALL = "Basketball";
	private final String HANDBALL = "Handball";
	private final String VOLLEYBALL = "Volleyball";
	private final String HOCKEY = "Hockey";

	@Override
	protected long convert(SynchronizerContext context, Odds dto) {
		// TODO Auto-generated method stub
		long nb = 0L;

		try {
			// parseNextMatch("http://www.betexplorer.com/next/soccer/",
			// FOOTBALL,
			// context); // For
			// basketball
			parseNextMatch("http://www.betexplorer.com/next/volleyball/",
					VOLLEYBALL, context);
			// //
			// for
			// Volleyball
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nb;
	}

	private void parseNextMatch(String httpRef, String sportType,
			SynchronizerContext context) throws Throwable {
		// TODO Auto-generated method stub
		URL website = new URL(httpRef);
		URLConnection urlConnetion = website.openConnection(getProxy(httpRef));
		Document doc = Jsoup
				.parse(urlConnetion.getInputStream(), null, httpRef);
		// Document doc = Jsoup.connect(httpRef).get();
		org.jsoup.select.Elements e = doc.select("tr");
		// Define sport
		Sport sport = context.findSport(sportType);

		// Search URL for each match
		for (Element t : e) {
			String test = t.attr("data-dt").replace(",", ":");

			if (t.attr("class").equals("rtitle league-title") == false) {
				Element link = t.select("a").first();

				if (link != null) {
					String linkHref = link.attr("href");
					String Exist = t.select("span.Postponed").text();
					if (linkHref.isEmpty() == false) {

						DateFormat formatter = new SimpleDateFormat(
								"dd:MM:yyyy:hh:mm");
						Date date = formatter.parse(test);
						Date timeSQL = new Date();
						if (date.compareTo(timeSQL) > 0) {
							matchCount++;
							String extract = linkHref.substring(
									linkHref.length() - 9,
									linkHref.length() - 1);
							// before, need to retrive match team1 vs team 2;
							String linkOdd1 = "http://www.betexplorer.com/gres/ajax-matchodds.php?t=n&e="
									+ extract + "&b=ou";
							parseMatchId(linkOdd1, sport, context);
							/*
							 * String linkOdd2 =
							 * "http://www.betexplorer.com/gres/ajax-matchodds.php?t=n&e="
							 * + extract + "&b=1x2"; parseMatchId(linkOdd2,
							 * context); String linkOdd3 =
							 * "http://www.betexplorer.com/gres/ajax-matchodds.php?t=n&e="
							 * + extract + "&b=ou"; parseMatchId(linkOdd3,
							 * context);
							 */
							System.out.println(matchCount + "," + linkOdd1);
						}

					}
				}
			}
		}
	}

	private long parseMatchId(String httpRef, Sport sport,
			SynchronizerContext context) throws Throwable {
		long nb = 0L;
		URL website = new URL(httpRef);
		URLConnection urlConnetion = website.openConnection(getProxy(httpRef));
		Document docmatch = Jsoup.parse(urlConnetion.getInputStream(), null,
				httpRef);
		// Document docmatch = Jsoup.connect(httpRef).get();
		if (docmatch.select("tr").isEmpty() == false) {
			org.jsoup.select.Elements e = docmatch.select("tr");

			// One bet Type, one Bookmaker, one match

			for (Element t : e) {
				org.jsoup.select.Elements f = t.select("td");
				parseOverUnder(f, t);

			}
		}

		return nb;
	}

	private void parseOverUnder(org.jsoup.select.Elements elements, Element t) {
		// Over Under structure <tr> 4 elements : 1/nothing : 2/ref over under
		// 3/ odd Over 4/odd under
		Element tnode = t.select("th").first();
		String hstr = tnode.attr("span.class");
		int count = 0;

		for (Element r : elements) {
			count++;
			if (count == 2) {

				String str = r.text().replaceAll("/", "").replaceAll("td", "")
						.replaceAll("sets", "").replaceAll("<", "")
						.replaceAll(">", "").replace(" \\", "");
				str = "Over/Under " + str;
				System.out.println(str);

			}
			if (count == 3) {

				String odd1 = r.attr("data-odd").replaceAll("/", "")
						.replaceAll("<", "").replaceAll(">", "")
						.replace("\\", "").replace("\"", "");
				System.out.println(odd1);
			}
			if (count == 4) {

				String odd2 = r.attr("data-odd").replaceAll("/", "")
						.replaceAll("<", "").replaceAll(">", "")
						.replace("\\", "").replace("\"", "");

				System.out.println(odd2);

			}

		}
		/**
		 * Match match = context.findOrCreateMatch(sport, matchBcDto
		 * .getStartDate().toGregorianCalendar().getTime(), player1, player2);
		 * 
		 * BetType betType = context.findBetType(betName);
		 */

	}

	@Override
	protected Class<Odds> getDtoClass() {
		// TODO Auto-generated method stub
		return Odds.class;
	}

	private Proxy getProxy(String httpRef) throws Throwable {

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"gecd-proxy.equities.net.intra", 8080));

		return proxy;

	}

	// Optional : maybe to create a historical.
	private void convert(Date date, String odd, Match match, String betTypes,
			String subBetType, SynchronizerContext context) {

		BetType betType = context.findBetType(betTypes);
		if (betType != null) {

			RefKey refKey = context.findOrCreateRefKey(match, betType);
			Bet bet = new Bet();
			bet.setOdd(Long.valueOf(odd));
			bet.setRefKey(refKey);
			bet.setCode(subBetType);
			bet.setDate(context.getSynchronizationDate());
			bet.setBookMaker(context.getBookMaker());
			bet.setBookmakerBetId("dummy");
			saveBet(bet);
		}

	}
}
