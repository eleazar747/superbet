package fr.ele.integration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

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

		JAXBContext jaxbContext = JAXBContext.newInstance(Odds.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Source source = new StreamSource(
				NordicbetUnwmarshallingTest.class
						.getResourceAsStream("/fr/ele/feeds/nordicbet/nordicbet.xml"));

		nordicbetSynchronizer.convert((Odds) unmarshaller.unmarshal(source));

		Assert.assertNotNull(matchRepository.findByCode(Code));
	}

}
