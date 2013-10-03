package fr.ele.integration;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.nordicbet.NordicbetUnwmarshallingTest;
import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.services.mapping.NordicbetSynchronizer;
import fr.ele.services.repositories.MatchRepository;

public class NordicbetIntegrationTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private NordicbetSynchronizer nordicbetSynchronizer;

    @Autowired
    private MatchRepository matchRepository;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void test() throws Throwable {
       String Code = "richardgasquet**marcelgranollers";
        InputStream inputStream = new BufferedInputStream(
                NordicbetUnwmarshallingTest.class
                        .getResourceAsStream("/fr/ele/feeds/nordicbet/nordicbet.xml"));
        Odds odds = nordicbetSynchronizer.unmarshall(inputStream);
        nordicbetSynchronizer.synchronize("nordicbet", odds);

        Assert.assertNotNull(matchRepository.findByCode(Code));
    }
    @Test
    public void testfindTeam() throws Throwable {
    	
      String Code = "richardgasquet**marcelgranollers";
    InputStream inputStream = new BufferedInputStream(
            NordicbetUnwmarshallingTest.class
                    .getResourceAsStream("/fr/ele/feeds/nordicbet/nordicbet.xml"));
    Odds odds = nordicbetSynchronizer.unmarshall(inputStream);
    nordicbetSynchronizer.synchronize("nordicbet", odds);
    
    
    }
}
