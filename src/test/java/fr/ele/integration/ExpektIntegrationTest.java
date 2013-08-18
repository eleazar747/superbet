package fr.ele.integration;

import java.io.BufferedInputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        String Code = "richardgasquet**marcelgranollers";

        JAXBContext jaxbContext = JAXBContext.newInstance(PunterOdds.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BufferedInputStream inputStream = new BufferedInputStream(
                ExpektUnmarshallingTest.class
                        .getResourceAsStream("/fr/ele/feeds/expekt/exportServlet.xml"));
        expektSynchronizer.convert((PunterOdds) unmarshaller
                .unmarshal(inputStream));

        Assert.assertNotNull(matchRepository.findByCode(Code));
        List<Bet> bets = betRepository.findAll();
        Assert.assertNotNull(bets);
        System.out.println(bets.size());
        Assert.assertTrue(bets.size() > 0);

    }

}
