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
import fr.ele.services.mapping.betExplorer.WinnerMatchParser;

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
		for (HtmlBetDto htmlBetDto : dto.getDtos()) {
			// Here transformation htmlbetDto to bet object

			System.out
					.println(htmlBetDto.getBetType() + " "
							+ htmlBetDto.getSubType() + " "
							+ htmlBetDto.getBookmaker());
			Bet bet = new Bet();
			Sport sport = context.findSport(htmlBetDto.getSport());
			BetType betType = context.findBetType(htmlBetDto.getBetType());
			Match match = context.findOrCreateMatch(sport,
					htmlBetDto.getDate(), htmlBetDto.getPlayer1(),
					htmlBetDto.getPlayer2());
			context.setBookmaker(htmlBetDto.getBookmaker());
			RefKey refkey = context.findOrCreateRefKey(match, betType);
			bet.setOdd(htmlBetDto.getOdd());
			bet.setRefKey(refkey);
			bet.setBookmakerBetId(htmlBetDto.getBookmakerId());
			saveBet(bet);
		}

		return nb;
	}

	private List<HtmlBetDto> parseNextMatch(String httpRef, String sportType,
			SynchronizerContext context, MatchParser... parsers)
			throws Throwable {
		// TODO Auto-generated method stub
		List<HtmlBetDto> bets = new LinkedList<HtmlBetDto>();
		URL website = new URL(httpRef);
		URLConnection urlConnetion = website.openConnection(getProxy());
		Document doc = Jsoup
				.parse(urlConnetion.getInputStream(), null, httpRef);

		// Document doc = Jsoup.connect(httpRef).get();
		org.jsoup.select.Elements e = doc.select("tr");
		// Define sport
		// Sport sport = context.findSport(sportType);

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
									String linkOdd = URL_MATCH + extract;
									for (MatchParser parser : parsers) {
										bets.addAll(parser.parseMatchId(
												linkOdd, teams, sportType));

									}

								}
							}
						}
					}
				}
			}
		}

		return bets;
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
		LOGGER.info("Synchroniser Volleyball starts at :" + new Date() + "\n");
		try {
			dtos.addAll(parseNextMatch(
					"http://www.betexplorer.com/next/volleyball/", VOLLEYBALL,
					contextdefaut, new OverUnderMatchParser(),
					new WinnerMatchParser(), new AsianHandicapParser()));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("Synchroniser Volleyball finish at :" + new Date() + "\n");
		/**
		 * LOGGER.info("Synchroniser Handball starts at :" + new Date() + "\n");
		 * parseNextMatch("http://www.betexplorer.com/next/handball/", HANDBALL,
		 * context, new OverUnderMatchParser(), new ResultMatchParser(), new
		 * AsianHandicapParser());
		 * LOGGER.info("Synchroniser Handball finish at :" + new Date() + "\n");
		 * 
		 * LOGGER.info("Synchroniser Basketball starts at :" + new Date() +
		 * "\n"); parseNextMatch("http://www.betexplorer.com/next/basketball/",
		 * BASKETBALL, context, new ResultMatchParser(), new
		 * OverUnderMatchParser(), new AsianHandicapParser());
		 * 
		 * LOGGER.info("Synchroniser Basketball finish at :" + new Date() +
		 * "\n");
		 * 
		 * LOGGER.info("Synchroniser Football starts at :" + new Date() + "\n");
		 * parseNextMatch("http://www.betexplorer.com/next/soccer/", FOOTBALL,
		 * context, new ResultMatchParser(), new OverUnderMatchParser());
		 * LOGGER.info("Synchroniser Football finish at :" + new Date() + "\n");
		 * /** LOGGER.info("Synchroniser Hockey starts at :" + new Date() +
		 * "\n"); parseNextMatch("http://www.betexplorer.com/next/hockey/",
		 * HOCKEY, context, new ResultMatchParser(), new
		 * OverUnderMatchParser());
		 * LOGGER.info("Synchroniser Hockey finish at :" + new Date() + "\n");
		 * 
		 * LOGGER.info("Synchroniser Baseball starts at :" + new Date() + "\n");
		 * parseNextMatch("http://www.betexplorer.com/next/baseball/", BASEBALL,
		 * context, new ResultMatchParser(), new OverUnderMatchParser());
		 * LOGGER.info("Synchroniser Baseball finish at :" + new Date() + "\n");
		 */
		HtmlBetDtos zob = new HtmlBetDtos();
		zob.setDtos(dtos);
		return zob;
	}

}
