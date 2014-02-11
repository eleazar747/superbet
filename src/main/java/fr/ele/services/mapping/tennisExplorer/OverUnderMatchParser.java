package fr.ele.services.mapping.tennisExplorer;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.services.mapping.SynchronizerContext;

public class OverUnderMatchParser extends MatchParser {
	private String bookie;
	private BetType betType;

	@Override
	protected List<Bet> doParse(Element t, Match match,
			SynchronizerContext context) {
		// TODO Auto-generated method stub
		List<Bet> bets = new LinkedList<Bet>();
		Elements elements = t.select("tr");
		for (Element element : elements) {
			if (element.attr("class").equals("odds-type")) {
				String sbetType = element.select("td").text();
				betType = context.findBetType(sbetType);
			}
			Elements tnode3 = element.select("td");
			if (element.attr("class").equals("one")
					|| element.attr("class").equals("two")) {
				for (Element tnode3element : tnode3) {
					if (tnode3element.attr("class").equals("first tl")) {
						bookie = tnode3element.text();
					}

					if (tnode3element.attr("class").equals("k1") || tnode3element.attr("class").equals("k1 best-betrate")) {
						String odd = tnode3element.text();
						createOdd(match, context, bookie, bets, betType, odd,
								"Over");
					}
					if (tnode3element.attr("class").equals("k2") || tnode3element.attr("class").equals("k2 best-betrate")) {
						String odd = tnode3element.text();
						createOdd(match, context, bookie, bets, betType, odd,
								"Under");
					}

				}
			}
		}

		return bets;
	}

}
