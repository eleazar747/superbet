package fr.ele.feeds.nordicbet;



import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import junit.framework.Assert;

import org.junit.Test;

import fr.ele.feeds.nordicbet.dto.Odds;

public class NordicbetUnwmarshallingTest {

	 @Test
	    public void test() throws Throwable {
	        JAXBContext jaxbContext = JAXBContext.newInstance(Odds.class);
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        Source source = new StreamSource(
	                NordicbetUnwmarshallingTest.class
	                        .getResourceAsStream("nordicbet.xml"));
	        Odds odds = (Odds) unmarshaller.unmarshal(source);
	        Assert.assertNotNull(odds);

	    }


}
