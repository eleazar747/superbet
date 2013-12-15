package fr.ele.integration;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.nordicbet.NordicbetUnwmarshallingTest;
import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.services.mapping.oddsPortalSynchroniser;

public class oddsPortalintegrationTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private oddsPortalSynchroniser oddsPortalSynchroniser;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void TestParse() {
        InputStream inputStream = new BufferedInputStream(
                NordicbetUnwmarshallingTest.class
                        .getResourceAsStream("nordicbet.xml"));
        Odds odds = oddsPortalSynchroniser.unmarshall(inputStream, null);
        oddsPortalSynchroniser.synchronize("oddsportal", odds);
    }

}
