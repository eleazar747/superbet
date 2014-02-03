package fr.ele.services.mapping.betExplorer;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.services.mapping.SynchronizerContext;

public abstract class MatchParser {

	public List<Bet> parseMatchId(String httpRef, Match match,
			SynchronizerContext context) throws Throwable {
		List<Bet> bets = new LinkedList<Bet>();
		URL website = new URL(httpRef + getUrlExtension());
		URLConnection urlConnetion = website.openConnection(getProxy());
		Document docmatch = Jsoup.parse(urlConnetion.getInputStream(), null,
				httpRef + getUrlExtension());
		// Document docmatch = Jsoup.connect(httpRef + getUrlExtension()).get();
		if (docmatch.select("tr").isEmpty() == false) {
			org.jsoup.select.Elements e = docmatch.select("tr");
			// One bet Type, one Bookmaker, one match
			for (Element t : e) {
				bets.addAll(doParse(t.select("td"), t, match, context));
			}
		}

		return bets;
	}

	protected abstract String getUrlExtension();

	protected abstract List<Bet> doParse(org.jsoup.select.Elements elements,
			Element t, Match match, SynchronizerContext context);

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
			context.setBookmaker("betexplorer");
		}
	}

	protected String extractOdd(Element element) {
		return element.attr("data-odd").replaceAll("/", "").replaceAll("<", "")
				.replaceAll(">", "").replace("\\", "").replace("\"", "");
	}

	protected boolean extractActiveOdd(Element element) {
		return element.toString().contains("notactive");
	}

	private Proxy getProxy() throws Throwable {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"gecd-proxy.equities.net.intra", 8080));
		return proxy;
	}
}
