package fr.ele.services.mapping.betExplorer;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.ele.feeds.HtmlBetDto;

public class ResultMatchParser extends MatchParser {

	private Element elementTmp;

	@Override
	protected List<HtmlBetDto> doParse(Elements elements, Element t,
			String match, String sport, Date date) {
		// Match Winner structure <tr> 3 elements : 1/nothing :
		// 2/ Home Winner 3/ Away Winner
		String bookie = "";
		Element tnode = t.select("th").first();
		Elements link = tnode.select("a");
		if (link != null) {
			Element tnode2 = link.select("span").first();

			if (tnode2 != null) {
				Elements link2 = tnode2.select("span");

				if (link2 != null) {
					bookie = link2.text().replaceAll(" ", "")
							.replaceAll("/", "").replaceAll("th", "")
							.replaceAll("span", "").replaceAll("<a", "")
							.replaceAll("a>", "").replace("(www)", "")
							.replaceAll("<", "").replaceAll(">", "")
							.replace(" \\", "").replace("\\", "");
				}
			}
		}

		List<HtmlBetDto> bets = new LinkedList<HtmlBetDto>();
		String betType = null;
		Iterator<Element> it = elements.iterator();
		if (extractActiveOdd(elements.toString()) == false) {
			if (it.hasNext()) {
				it.next();
				betType = "1x2";
				if (it.hasNext()) {
					elementTmp = it.next();
					String odd = extractOdd(elementTmp);

					if (extractActiveOdd(elementTmp) == false) {
						createOdd(match, bookie, bets, betType, odd, "1",
								sport, date);
					}
					if (it.hasNext()) {
						elementTmp = it.next();

						odd = extractOdd(elementTmp);

						createOdd(match, bookie, bets, betType, odd, "x",
								sport, date);

						if (it.hasNext()) {
							elementTmp = it.next();
							odd = extractOdd(elementTmp);

							createOdd(match, bookie, bets, betType, odd, "2",
									sport, date);

						}
					}
				}
			}
		}
		return bets;

	}

	@Override
	protected String getUrlExtension() {
		return "&b=1x2";
	}

}
