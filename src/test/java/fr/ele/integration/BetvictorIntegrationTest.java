package fr.ele.integration;


import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ele.services.mapping.*;

public class BetvictorIntegrationTest {

	@Test
	public void TestParse() throws IOException {
		new BetVictorSynchroniser();
	}
	
	
}
