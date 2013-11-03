package fr.ele.feeds.bwin;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import junit.framework.Assert;

import org.junit.Test;

import fr.ele.feeds.bwin.dto.ROOT;

public class bwinTest {

    @Test
    public void test() throws Throwable {
        JAXBContext jaxbContext = JAXBContext.newInstance(ROOT.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Source source = new StreamSource(
                bwinTest.class.getResourceAsStream("bwin.xml"));
        ROOT root = (ROOT) unmarshaller.unmarshal(source);
        Assert.assertNotNull(root);
    }

}
