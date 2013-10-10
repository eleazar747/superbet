package fr.ele.integration;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.nordicbet.NordicbetUnwmarshallingTest;
import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.services.mapping.*;

public class BetvictorIntegrationTest extends AbstractSuperbetIntegrationTest{
	@Autowired
	private BetVictorSynchroniser betvictorSynchroniser;
	
	@Override
	@Before
	public void initializeDatas() {
		super.initializeDatas();
	}
	
	
	@Test
	public void TestParse() throws IOException {
		 InputStream inputStream = new BufferedInputStream(
	                NordicbetUnwmarshallingTest.class
	                        .getResourceAsStream("/fr/ele/feeds/nordicbet/nordicbet.xml"));
	        Odds odds = betvictorSynchroniser.unmarshall(inputStream);
	        betvictorSynchroniser.synchronize("betvictor", odds);
	}
	
	
}
