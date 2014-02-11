package fr.ele.services.mapping;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import fr.ele.feeds.wiliamhill.dto.Oxip;
import fr.ele.model.Bet;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.Sport;
import fr.ele.services.mapping.betExplorer.AsianHandicapParser;
import fr.ele.services.mapping.betExplorer.MatchParser;
import fr.ele.services.mapping.betExplorer.OverUnderMatchParser;
import fr.ele.services.mapping.betExplorer.ResultMatchParser;
import fr.ele.services.mapping.betExplorer.WinnerMatchParser;

@Service("BetExplorerSynchroniser")
public class BetExplorerSynchroniser extends AbstractSynchronizer<Oxip> {

	private static final String URL_MATCH = "http://www.betexplorer.com/gres/ajax-matchodds.php?t=n&e=";

	private final String HANDBALL = "Handball";

	private final String VOLLEYBALL = "Volleyball";

	private final String BASKETBALL = "Basketball";

	private final String FOOTBALL = "Football";

	private final String HOCKEY = "Hockey";

	private final String BASEBALL = "Baseball";

	@Override
	protected long convert(SynchronizerContext context, Oxip dto) {
		long nb = 0L;

		try {

			LOGGER.info("Synchroniser Volleyball starts at :" + new Date()
					+ "\n");
			parseNextMatch("http://www.betexplorer.com/next/volleyball/",
					VOLLEYBALL, context, new OverUnderMatchParser(),
					new WinnerMatchParser(), new AsianHandicapParser());
			LOGGER.info("Synchroniser Volleyball finish at :" + new Date()
					+ "\n");

			LOGGER.info("Synchroniser Handball starts at :" + new Date() + "\n");
			parseNextMatch("http://www.betexplorer.com/next/handball/",
					HANDBALL, context, new OverUnderMatchParser(),
					new ResultMatchParser(), new AsianHandicapParser());
			LOGGER.info("Synchroniser Handball finish at :" + new Date() + "\n");

			LOGGER.info("Synchroniser Basketball starts at :" + new Date()
					+ "\n");
			parseNextMatch("http://www.betexplorer.com/next/basketball/",
					BASKETBALL, context, new ResultMatchParser(),
					new OverUnderMatchParser(), new AsianHandicapParser());

			LOGGER.info("Synchroniser Basketball finish at :" + new Date()
					+ "\n");
			/**
			 * LOGGER.info("Synchroniser Football starts at :" + new Date() +
			 * "\n"); parseNextMatch("http://www.betexplorer.com/next/soccer/",
			 * FOOTBALL, context, new ResultMatchParser(), new
			 * OverUnderMatchParser());
			 * LOGGER.info("Synchroniser Football finish at :" + new Date() +
			 * "\n"); /** LOGGER.info("Synchroniser Hockey starts at :" + new
			 * Date() + "\n");
			 * parseNextMatch("http://www.betexplorer.com/next/hockey/", HOCKEY,
			 * context, new ResultMatchParser(), new OverUnderMatchParser());
			 * LOGGER.info("Synchroniser Hockey finish at :" + new Date() +
			 * "\n");
			 * 
			 * LOGGER.info("Synchroniser Baseball starts at :" + new Date() +
			 * "\n");
			 * parseNextMatch("http://www.betexplorer.com/next/baseball/",
			 * BASEBALL, context, new ResultMatchParser(), new
			 * OverUnderMatchParser());
			 * LOGGER.info("Synchroniser Baseball finish at :" + new Date() +
			 * "\n");
			 */
			// for
			// Volleyball
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nb;
	}

	private void parseNextMatch(String httpRef, String sportType,
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
		Sport sport = context.findSport(sportType);

		// Search URL for each match
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
									Match match = context
											.findOrCreateMatch(sport, date,
													players[0], players[1]);
									List<Bet> bets = new LinkedList<Bet>();
									String linkOdd = URL_MATCH + extract;
									for (MatchParser parser : parsers) {
										bets.addAll(parser.parseMatchId(
												linkOdd, match, context));

									}
									// if (sportType.toUpperCase().equals(
									// "BASKETBALL")
									// || sportType.toUpperCase().equals(
									// "VOLLEYBALL")
									// || sportType.toUpperCase().equals(
									// "HOCKEY")) {
									// parser = new ResultMatchParser();
									// }
									// if (sportType.toUpperCase().equals(
									// "FOOTBALL")
									// || sportType.toUpperCase().equals(
									// "BASKETBALL")
									// || sportType.toUpperCase().equals(
									// "VOLLEYBALL")
									// || sportType.toUpperCase().equals(
									// "HANDBALL")
									// || sportType.toUpperCase().equals(
									// "HOCKEY")) {
									// String linkOdd = URL_MATCH + extract;
									// parser = new OverUnderMatchParser();
									// bets.addAll(parser.parseMatchId(
									// linkOdd, match, context));
									// }
									// if (sportType.toUpperCase().equals(
									// "FOOTBALL")
									// || sportType.toUpperCase().equals(
									// "HANDBALL")
									// || sportType.toUpperCase().equals(
									// "HOCKEY")) {
									// String linkOdd = URL_MATCH + extract;
									// parser = new WinnerMatchParser();
									// bets.addAll(parser.parseMatchId(
									// linkOdd, match, context));
									// }
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

	private Proxy getProxy() throws Throwable {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"gecd-proxy.equities.net.intra", 8080));
		return proxy;
	}

	@Override
	protected Class<Oxip> getDtoClass() {
		// TODO Auto-generated method stub
		return Oxip.class;
	}

}
