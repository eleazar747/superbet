package fr.ele.integration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.betclick.BetclickUnmarshallingTest;
import fr.ele.feeds.betclick.dto.SportsBcDto;
import fr.ele.services.mapping.BetclickSynchronizer;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.MatchRepository;

public class BetclickIntegrationTest extends AbstractSuperbetIntegrationTest {

	@Autowired
	private BetclickSynchronizer betclickSynchronizer;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private BookMakerRepository bookMakerRepository;

	@Override
	@Before
	public void initializeDatas() {
		super.initializeDatas();
	}

	@Test
	public void test() throws Throwable {

		String Code = "arsenal**astonvilla";

		JAXBContext jaxbContext = JAXBContext.newInstance(SportsBcDto.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Source source = new StreamSource(
				BetclickUnmarshallingTest.class
						.getResourceAsStream("/fr/ele/feeds/betclick/odds_en.xml"));

		betclickSynchronizer.convert((SportsBcDto) unmarshaller
				.unmarshal(source));

		Assert.assertNotNull(matchRepository.findByCode(Code));
	}

}
