package fr.ele.services.mapping.tennisExplorer;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.services.mapping.SynchronizerContext;

public class ResultMatchParser extends MatchParser {

	private Element elementTmp;
	private String bookie;

	@Override
	protected List<Bet> doParse(Element t, Match match,
			SynchronizerContext context) {
		// Match Winner structure <tr> 3 elements : 1/nothing :
		// 2/ Home Winner 3/ Away Winner String bookie = "";
		Elements tnode = t.select("tr");
		List<Bet> bets = new LinkedList<Bet>();
		BetType betType = context.findBetType("1x2");
		;

		for (Element tnodeelement : tnode) {
			Elements tnode3 = tnodeelement.select("td");
			if (tnodeelement.attr("class").equals("one")
					|| tnodeelement.attr("class").equals("two")) {
				for (Element tnode3element : tnode3) {
					if (tnode3element.attr("class").equals("first tl")) {
						bookie = tnode3element.text();
					}

					if (tnode3element.attr("class").equals("k1")) {
						String odd = tnode3element.text();
						createOdd(match, context, bookie, bets, betType, odd,
								"1");
					}
					if (tnode3element.attr("class").equals("k2")) {
						String odd = tnode3element.text();
						createOdd(match, context, bookie, bets, betType, odd,
								"2");
					}

				}
			}
		}
		Elements tnode3 = t.select("td");
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

		/**
		 * Iterator<Element> it = elements.iterator(); if
		 * (extractActiveOdd(elements.toString()) == false) { if (it.hasNext())
		 * { it.next(); betType = context.findBetType("1x2"); if (it.hasNext())
		 * { elementTmp = it.next(); String odd = extractOdd(elementTmp);
		 * 
		 * if (extractActiveOdd(elementTmp) == false) { createOdd(match,
		 * context, bookie, bets, betType, odd, "1"); } if (it.hasNext()) {
		 * elementTmp = it.next();
		 * 
		 * odd = extractOdd(elementTmp);
		 * 
		 * createOdd(match, context, bookie, bets, betType, odd, "x");
		 * 
		 * if (it.hasNext()) { elementTmp = it.next(); odd =
		 * extractOdd(elementTmp);
		 * 
		 * createOdd(match, context, bookie, bets, betType, odd, "2");
		 * 
		 * } } } } }
		 */
		return bets;

	}

	@Override
	protected String getUrlExtension() {
		return "&b=1x2";
	}

}
