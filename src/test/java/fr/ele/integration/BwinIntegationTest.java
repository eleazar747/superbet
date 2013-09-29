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

import fr.ele.feeds.bwin.dto.ROOT;
import fr.ele.model.Bet;

import fr.ele.services.mapping.BookMakerSynchronizerService;
import fr.ele.services.mapping.bwinSynchroniser;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.MatchRepository;

public class BwinIntegationTest extends AbstractSuperbetIntegrationTest{

    @Autowired
    private bwinSynchroniser BwinSynchroniser;
    
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
				BwinIntegationTest.class
						.getResourceAsStream("/fr/ele/feeds/bwin/bwin.xml"));
		ROOT root = BwinSynchroniser.unmarshall(inputStream);
		BwinSynchroniser.synchronize("bwin", root);

	}

}
