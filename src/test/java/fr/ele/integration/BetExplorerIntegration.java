package fr.ele.integration;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.wiliamhill.dto.Oxip;
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
		InputStream inputStream = new BufferedInputStream(
				WIlliamHillIntegrationTest.class
						.getResourceAsStream("/fr/ele/feeds/nordicbet/nordicbet.xml"));
		Oxip odds = betExplorerSynchroniser.unmarshall(inputStream, null);
		betExplorerSynchroniser.synchronize("betexplorer", odds);
	}
}
