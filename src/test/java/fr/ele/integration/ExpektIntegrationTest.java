package fr.ele.integration;

import java.io.BufferedInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import fr.ele.feeds.expekt.ExpektUnmarshallingTest;
import fr.ele.feeds.expekt.dto.PunterOdds;
import fr.ele.model.Bet;
import fr.ele.services.mapping.ExpektSynchronizer;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.MatchRepository;

public class ExpektIntegrationTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private ExpektSynchronizer expektSynchronizer;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private BetRepository betRepository;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void test() throws Throwable {
        String code = "richardgasquet**marcelgranollers";

        BufferedInputStream inputStream = new BufferedInputStream(
                ExpektUnmarshallingTest.class
                        .getResourceAsStream("/fr/ele/feeds/expekt/exportServlet.xml"));
        PunterOdds odds = expektSynchronizer.unmarshall(inputStream, null);
        expektSynchronizer.synchronize("expekt", odds);

        Assert.assertNotNull(matchRepository.findByCode(code));
        List<Bet> bets = Lists.newArrayList(betRepository.findAll());
        Assert.assertNotNull(bets);
        Assert.assertEquals(312, bets.size());
        Set<String> bookmakerUniqueIds = new HashSet<String>(bets.size());
        for (Bet bet : bets) {
            bookmakerUniqueIds.add(bet.getBookmakerBetId());
            // System.err.println(bet.getRefKey().getMatch().getCode());
        }
        // TODO find unique id used by expekt
        // Assert.assertEquals(bets.size(), bookmakerUniqueIds.size());
    }

}
