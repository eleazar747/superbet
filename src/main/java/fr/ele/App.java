package fr.ele;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import fr.ele.feeds.betclick.dto.SportsBcDto;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Throwable {
        JAXBContext jaxbContext = JAXBContext.newInstance(SportsBcDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Object unmarshal = unmarshaller.unmarshal(new URL(
                "http://xml.cdn.betclic.com/odds_en.xml"));

        System.out.println(unmarshal);
    }
}
