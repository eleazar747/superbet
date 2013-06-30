package fr.ele.feeds.intertops;



import javax.xml.bind.JAXBContext;

import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import junit.framework.Assert;

import org.junit.Test;

import fr.ele.feeds.intertops.dto.Result;

public class intertopslnmarshallingTest {

	@Test
	public void test() throws Throwable {
		JAXBContext jaxbContext = JAXBContext.newInstance(Result.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Source source = new StreamSource(
                intertopslnmarshallingTest.class
                        .getResourceAsStream("intertops.xml"));
        Result result = (Result) unmarshaller.unmarshal(source);
        Assert.assertNotNull(result);

	}

}
