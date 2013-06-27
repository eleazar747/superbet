package fr.ele.feeds.betclick;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import junit.framework.Assert;

import org.junit.Test;

import fr.ele.feeds.betclick.dto.SportBcDto;
import fr.ele.feeds.betclick.dto.SportsBcDto;

public class BetclickUnmarshallingTest {

    @Test
    public void test() throws Throwable {
        JAXBContext jaxbContext = JAXBContext.newInstance(SportsBcDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Source source = new StreamSource(
                BetclickUnmarshallingTest.class
                        .getResourceAsStream("odds_en.xml"));
        SportsBcDto sports = (SportsBcDto) unmarshaller.unmarshal(source);
        Assert.assertNotNull(sports);
        Assert.assertNotNull(sports.getSport());
        List<SportBcDto> sportList = sports.getSport();
        Assert.assertEquals(18, sportList.size());
        for (SportBcDto sport : sportList) {
            System.out.println(sport.getName());
        }
    }

}
