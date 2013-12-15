package fr.ele.integration;

import java.io.BufferedInputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.intertops.dto.Result;
import fr.ele.services.mapping.IntertopsSynchronizer;
import fr.ele.services.repositories.MatchRepository;

public class IntertopsIntegrationTest extends AbstractSuperbetIntegrationTest {

    @Autowired
    private IntertopsSynchronizer intertopsSynchronizer;

    @Autowired
    private MatchRepository matchRepository;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void test() throws Throwable {

        BufferedInputStream inputStream = new BufferedInputStream(
                BetclickIntegrationTest.class
                        .getResourceAsStream("/fr/ele/feeds/intertops/intertops.xml"));

        Result result = intertopsSynchronizer.unmarshall(inputStream, null);
        intertopsSynchronizer.synchronize("intertops", result);

        // Assert.assertNotNull(matchRepository.findByCode(Code));
    }

}
