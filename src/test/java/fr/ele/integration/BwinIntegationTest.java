package fr.ele.integration;

import java.io.BufferedInputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.bwin.dto.ROOT;
import fr.ele.services.mapping.BookMakerSynchronizerService;
import fr.ele.services.mapping.BwinSynchroniser;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.MatchRepository;

public class BwinIntegationTest extends AbstractSuperbetIntegrationTest {

	@Autowired
	private BwinSynchroniser BwinSynchroniser;

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
