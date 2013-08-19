package fr.ele.integration;

import java.io.BufferedInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.betclick.dto.SportsBcDto;
import fr.ele.model.Bet;
import fr.ele.services.mapping.BetclickSynchronizer;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.MatchRepository;

public class BetclickIntegrationTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private BetclickSynchronizer betclickSynchronizer;

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
        JAXBContext jaxbContext = JAXBContext.newInstance(SportsBcDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BufferedInputStream inputStream = new BufferedInputStream(
                BetclickIntegrationTest.class
                        .getResourceAsStream("/fr/ele/feeds/betclick/odds_en.xml"));
        betclickSynchronizer.synchronize((SportsBcDto) unmarshaller
                .unmarshal(inputStream));

        // Assert.assertNotNull(matchRepository.findByCode(code));
        List<Bet> bets = betRepository.findAll();
        Assert.assertNotNull(bets);
        Assert.assertEquals(665, bets.size());
        Set<String> bookmakerUniqueIds = new HashSet<String>(bets.size());
        for (Bet bet : bets) {
            bookmakerUniqueIds.add(bet.getBookmakerBetId());
            // System.err.println(bet.getRefKey().getMatch().getCode());
        }
        Assert.assertEquals(bets.size(), bookmakerUniqueIds.size());

    }

}
