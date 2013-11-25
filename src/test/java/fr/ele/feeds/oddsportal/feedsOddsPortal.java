package fr.ele.feeds.oddsportal;

import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class feedsOddsPortal {

	@Test
	public void test() throws Throwable {
		InputStream input = feedsOddsPortal.class
				.getResourceAsStream(("odds.html"));
		Document doc = Jsoup.parse(input, "UTF-8", "www.google.Fr");

		System.out.println("hhhh");

	}
}
