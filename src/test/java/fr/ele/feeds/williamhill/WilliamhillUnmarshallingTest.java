package fr.ele.feeds.williamhill;



import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import junit.framework.Assert;
import org.junit.Test;
import fr.ele.feeds.wiliamhill.dto.Oxip;

public class WilliamhillUnmarshallingTest {

	 @Test
	    public void test() throws Throwable {
	        JAXBContext jaxbContext = JAXBContext.newInstance(Oxip.class);
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        Source source = new StreamSource(
	                WilliamhillUnmarshallingTest.class
	                        .getResourceAsStream("WilliamHill.xml"));
	        Oxip oxip = (Oxip) unmarshaller.unmarshal(source);
	        Assert.assertNotNull(oxip);

	    }


}
