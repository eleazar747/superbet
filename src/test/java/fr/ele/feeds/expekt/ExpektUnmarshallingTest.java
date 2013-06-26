package fr.ele.feeds.expekt;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import junit.framework.Assert;

import org.junit.Test;

import fr.ele.feeds.expekt.dto.PunterOdds;

public class ExpektUnmarshallingTest {

    @Test
    public void test() throws Throwable {
        JAXBContext jaxbContext = JAXBContext.newInstance(PunterOdds.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Source source = new StreamSource(
                ExpektUnmarshallingTest.class
                        .getResourceAsStream("exportServlet.xml"));
        PunterOdds punterOdds = (PunterOdds) unmarshaller.unmarshal(source);
        Assert.assertNotNull(punterOdds);

    }

}
