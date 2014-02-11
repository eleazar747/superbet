package fr.ele.integration;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.HtmlBetDtos;
import fr.ele.services.mapping.BetExplorerSynchroniser;

public class BetExplorerIntegration extends AbstractSuperbetIntegrationTest {
	@Autowired
	private BetExplorerSynchroniser betExplorerSynchroniser;

	@Override
	@Before
	public void initializeDatas() {
		super.initializeDatas();
	}

	@Test
	public void testParse() {

		try {
			HtmlBetDtos odds = betExplorerSynchroniser.unmarshall(
					"http://www.betexplorer.com/next/volleyball/", null);
			betExplorerSynchroniser.synchronize("betexplorer", odds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
