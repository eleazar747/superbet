package fr.ele.integration;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.nordicbet.NordicbetUnwmarshallingTest;
import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.services.mapping.TitanBetSynchroniser;

public class TitanBetIntegrationTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private TitanBetSynchroniser titanBetSynchroniser;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void testParse() {
        InputStream inputStream = new BufferedInputStream(
                NordicbetUnwmarshallingTest.class
                        .getResourceAsStream("/fr/ele/feeds/nordicbet/nordicbet.xml"));
        Odds odds = titanBetSynchroniser.unmarshall(inputStream, null);
        titanBetSynchroniser.synchronize("titanbet", odds);
    }

}
