package fr.ele.feeds.betclick;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import junit.framework.Assert;

import org.junit.Test;

import fr.ele.feeds.betclick.dto.BetBcDto;
import fr.ele.feeds.betclick.dto.BetsBcDto;
import fr.ele.feeds.betclick.dto.EventBcDto;
import fr.ele.feeds.betclick.dto.MatchBcDto;
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

    @Test
    public void test2() throws Throwable {
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
        Set<String> rez = new HashSet<String>();
        for (SportBcDto sport : sportList) {
            List<EventBcDto> events = sport.getEvent();
            for (EventBcDto event : events) {
                List<MatchBcDto> matches = event.getMatch();
                for (MatchBcDto match : matches) {
                    BetsBcDto bets = match.getBets();
                    for (BetBcDto bet : bets.getBet()) {
                        rez.add(bet.getName());
                        // System.out.println(bet.getName());
                    }
                }
            }
            // System.out.println(sport.getName());
        }
        for (String s : rez) {
            System.out.println(s);
        }
    }

}
