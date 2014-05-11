package fr.ele.integration;

import java.util.Calendar;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.HtmlBetDtos;
import fr.ele.services.bets.BetSearch;
import fr.ele.services.bets.BetService;
import fr.ele.services.bets.SureBet;
import fr.ele.services.mapping.BetExplorerSynchroniser;

public class BetExplorerIntegrationTest extends AbstractSuperbetIntegrationTest {
    @Autowired
    private BetExplorerSynchroniser betExplorerSynchroniser;

    @Autowired
    private BetService betService;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void testParse() throws Throwable {
        HtmlBetDtos odds = betExplorerSynchroniser.unmarshall(
                "http://www.betexplorer.com/next/", null);
        betExplorerSynchroniser.synchronize("betexplorer", odds);
        BetSearch search = new BetSearch();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        search.setFrom(calendar.getTime());
        calendar.add(Calendar.DATE, 2);
        search.setTo(calendar.getTime());
        Iterator<SureBet> bets = betService.findSureBets(search);
        while (bets.hasNext()) {
            SureBet sureBet = bets.next();
            if (sureBet.isSureBet()) {
                System.out.println(sureBet.getValue());
            }
        }
    }
}
