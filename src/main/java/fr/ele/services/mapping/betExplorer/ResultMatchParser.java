package fr.ele.services.mapping.betExplorer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.services.mapping.SynchronizerContext;

public class ResultMatchParser extends MatchParser {

    @Override
    protected List<Bet> doParse(Elements elements, Element t, Match match,
            SynchronizerContext context) {
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

        List<Bet> bets = new LinkedList<Bet>();
        BetType betType = null;
        Iterator<Element> it = elements.iterator();
        it.next();
        betType = context.findBetType("1x2");

        String odd = extractOdd(it.next());
        createOdd(match, context, bookie, bets, betType, odd, "1");
        odd = extractOdd(it.next());
        createOdd(match, context, bookie, bets, betType, odd, "x");
        odd = extractOdd(it.next());
        createOdd(match, context, bookie, bets, betType, odd, "2");
        return bets;

    }

    @Override
    protected String getUrlExtension() {
        return "&b=1x2";
    }

}
