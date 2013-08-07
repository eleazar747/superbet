package fr.ele.integration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.feeds.expekt.ExpektUnmarshallingTest;
import fr.ele.feeds.expekt.dto.PunterOdds;
import fr.ele.services.mapping.ExpektSynchronizer;

public class ExpektIntegrationTest extends AbstractSuperbetIntegrationTest {
	@Autowired
	private ExpektSynchronizer expektSynchronizer;

	@Override
	@Before
	public void initializeDatas() {
		super.initializeDatas();
	}

	@Test
	public void test() throws Throwable {
		JAXBContext jaxbContext = JAXBContext.newInstance(PunterOdds.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Source source = new StreamSource(
				ExpektUnmarshallingTest.class
						.getResourceAsStream("exportServlet.xml"));

		expektSynchronizer.convert((PunterOdds) unmarshaller.unmarshal(source));

	}

}
