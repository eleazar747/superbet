package fr.ele.services.mapping.tennisExplorer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;
import fr.ele.services.mapping.SynchronizerContext;

public abstract class MatchParser {
	private BookMaker bookmaker;
	private String player1, player2;

	public List<Bet> parseMatchId(String httpRef, SynchronizerContext context,
			Sport sport, Date date) throws Throwable {
		setBookmaker(context);
		// search Team :

		List<Bet> bets = new LinkedList<Bet>();

		/**URL website = new URL(httpRef);
		URLConnection urlConnetion = website.openConnection(getProxy());
		Document docmatch = Jsoup.parse(urlConnetion.getInputStream(), null,
				httpRef);

		*/
		Document docmatch = Jsoup.connect(httpRef).get();
		if (docmatch.select("tr").isEmpty() == false) {
			org.jsoup.select.Elements e = docmatch.select("tbody");
			Match match = new Match();
			// One bet Type, one Bookmaker, one match
			for (Element t : e) {
				// Start find match
				if (t != null) {
					if (t.select("tr").first().attr("class").equals("head")
							&& t.select("tr").select("td").size() > 1) {
						player1 = "";
						player2 = "";

						Element ee = t.select("tr").first();
						for (Element uu : ee.select("td")) {
							if (uu.attr("class").equals("k1")) {
								player1 = uu.text();

							}

							if (uu.attr("class").startsWith("k2")) {
								player2 = uu.text();

							}
						}
						if (player1.equals("") == false
								|| player2.equals("") == false) {
							match = context.findOrCreateMatch(sport, date,
									player1, player2);
							bets.addAll(doParse(t, match, context));

						}
					}
					// end find match and proceess for bettype match result

					if (t.select("tr").first().attr("class")
							.equals("odds-type")) {

						bets.addAll(doParse(t, match, context));

					}
				}
			}
		}

		return bets;
	}

	protected abstract List<Bet> doParse(Element t, Match match,
			SynchronizerContext context);

	protected Bet convert(String odd, Match match, BetType betType,
			String subBetType, SynchronizerContext context) {
		RefKey refKey = context.findOrCreateRefKey(match, betType);
		Bet bet = new Bet();
		bet.setOdd(Double.valueOf(odd));
		bet.setRefKey(refKey);
		bet.setCode(subBetType);
		bet.setDate(context.getSynchronizationDate());
		bet.setBookMaker(context.getBookMaker());
		bet.setBookmakerBetId("dummy");
		return bet;
	}

	protected void createOdd(Match match, SynchronizerContext context,
			String bookie, List<Bet> bets, BetType betType, String odd,
			String subType) {
		if (betType != null) {
			if (bookie.equals("") == false) {
				context.setBookmaker((String) bookie.subSequence(1,
						bookie.length()));
				if (context.getBookMaker() != null && !odd.isEmpty()) {
					bets.add(convert(odd, match, betType, subType, context));
				}
			}
			context.setBookmaker(getBookmaker());
		}
	}

	protected String extractOdd(Element element) {
		return element.attr("data-odd").replaceAll("/", "").replaceAll("<", "")
				.replaceAll(">", "").replace("\\", "").replace("\"", "");
	}

	protected boolean extractActiveOdd(Element element) {
		return element.toString().contains("notactive");
	}

	protected boolean extractActiveOdd(String element) {
		return element.contains("notactive");
	}

	private void setBookmaker(SynchronizerContext context) {
		bookmaker = context.getBookMaker();
	}

	private BookMaker getBookmaker() {
		return bookmaker;
	}
}
