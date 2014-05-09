package fr.ele.services.mapping.betExplorer;

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

import fr.ele.feeds.HtmlBetDto;
import fr.ele.model.ref.BookMaker;
import fr.ele.services.mapping.SynchronizerContext;

public abstract class MatchParser {
	private BookMaker bookmaker;

	public List<HtmlBetDto> parseMatchId(String httpRef, String match,
			String sport, Date date) throws Throwable {

		List<HtmlBetDto> bets = new LinkedList<HtmlBetDto>();
	/**	URL website = new URL(httpRef + getUrlExtension());
		URLConnection urlConnetion = website.openConnection(getProxy());
		Document docmatch = Jsoup.parse(urlConnetion.getInputStream(), null,
				httpRef + getUrlExtension());
		*/
		Document docmatch = Jsoup.connect(httpRef + getUrlExtension()).get();
		if (docmatch.select("tr").isEmpty() == false) {
			org.jsoup.select.Elements e = docmatch.select("tr");
			// One bet Type, one Bookmaker, one match
			for (Element t : e) {
				bets.addAll(doParse(t.select("td"), t, match, sport, date));
			}
		}

		return bets;
	}

	protected abstract String getUrlExtension();

	protected abstract List<HtmlBetDto> doParse(
			org.jsoup.select.Elements elements, Element t, String match,
			String sport, Date date);

	protected HtmlBetDto convert(String odd, String match, String betType,
			String subBetType, String bookmaker, String sport, Date date) {

		// RefKey refKey = context.findOrCreateRefKey(match, betType);

		HtmlBetDto htmlBetDto = new HtmlBetDto();
		htmlBetDto.setSport(sport);
		htmlBetDto.setOdd(Double.valueOf(odd));
		htmlBetDto.setBookmaker(bookmaker);// a voir
		htmlBetDto.setSubType(subBetType);
		htmlBetDto.setMatch(match);
		htmlBetDto.setBetType(betType);
		// bet.setRefKey(refKey);
		htmlBetDto.setDate(date);
		htmlBetDto.setBookmakerId("dummy");
		return htmlBetDto;
	}

	protected void createOdd(String match, String bookie,
			List<HtmlBetDto> htmlBetDtos, String betType, String odd,
			String subType, String sport, Date date) {
		if (betType != null) {
			if (bookie.equals("") == false) {
				if (!odd.isEmpty()) {
					htmlBetDtos.add(convert(odd, match, betType, subType,
							bookie, sport, date));
				}
			}

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

	private Proxy getProxy() throws Throwable {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"gecd-proxy.equities.net.intra", 8080));
		return proxy;
	}

	private void setBookmaker(SynchronizerContext context) {
		this.bookmaker = context.getBookMaker();
	}

	private BookMaker getBookmaker() {
		return this.bookmaker;
	}
}
