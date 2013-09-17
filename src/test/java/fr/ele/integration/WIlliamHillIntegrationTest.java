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

import fr.ele.feeds.wiliamhill.dto.Oxip;
import fr.ele.model.Bet;
import fr.ele.model.ref.BookMaker;
import fr.ele.services.mapping.WilliamHillSynchronizer;
import fr.ele.services.mapping.BookMakerSynchronizerService;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.MatchRepository;

public class WIlliamHillIntegrationTest  extends AbstractSuperbetIntegrationTest{

	@Autowired
    private WilliamHillSynchronizer williamHillSynchronizer;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private BookMakerSynchronizerService bookMakerSynchronizerService;

    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void test() throws Throwable {
        BufferedInputStream inputStream = new BufferedInputStream(
                BetclickIntegrationTest.class
                        .getResourceAsStream("/fr/ele/feeds/williamhill/WilliamHill.xml"));
        Oxip dto = williamHillSynchronizer.unmarshall(inputStream);
        williamHillSynchronizer.synchronize("williamhill", dto);

        // Assert.assertNotNull(matchRepository.findByCode(code));
        List<Bet> bets = Lists.newArrayList(betRepository.findAll());
        Assert.assertNotNull(bets);
        Assert.assertEquals(665, bets.size());
        Set<String> bookmakerUniqueIds = new HashSet<String>(bets.size());
        for (Bet bet : bets) {
            bookmakerUniqueIds.add(bet.getBookmakerBetId());
            // System.err.println(bet.getRefKey().getMatch().getCode());
        }
        Assert.assertEquals(bets.size(), bookmakerUniqueIds.size());

    }

    @Test
    public void test2() {
        BookMaker bookMaker = bookMakerRepository.findByCode("betclic");
        bookMakerSynchronizerService.synchronize(bookMaker);
    }
	
	
	
}
