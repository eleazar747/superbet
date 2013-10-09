package fr.ele.services.mapping;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;

@Service("BetVictorSynchroniser")
public class BetVictorSynchroniser extends AbstractSynchronizer<Odds> {
	private Document doc;

	
	private SynchronizerContext context;

	public void betVictorSynchroniser() throws IOException {
		parseLigue1();
		parseLigue2();
		parseBundesliga();
		parseBundesliga2();
		//parseBundesliga3();
	}

	public void parseLigue1() throws IOException {

		doc = Jsoup
				.connect(
						"http://www.betvictor.com/sports/en/football/fra-division-1/coupons/100/6270510/0/4529/0/MPE/0/0/0/30/1")
				.get();
		String sport = "Football";
		readDataOver(doc.select("tr"), sport);

	}

	public void parseLigue2() throws IOException {

		doc = Jsoup
				.connect(
						"http://www.betvictor.com/sports/en/football/fra-division-2/coupons/100/7006510/0/4428/0/MPE/0/0/0/30/1")
				.get();
		String sport = "Football";
		readDataOver(doc.select("tr"), sport);
	}

	public void parseBundesliga() throws IOException {

		doc = Jsoup
				.connect(
						"http://www.betvictor.com/sports/en/football/ger-bundesliga/coupons/100/6316510/0/4428/0/MPE/0/0/0/30/1")
				.get();
		String sport = "Football";
		readDataOver(doc.select("tr"), sport);
	}

	public void parseBundesliga2() throws IOException {

		doc = Jsoup
				.connect(
						"http://www.betvictor.com/sports/en/football/ger-bundesliga-2/coupons/100/5170510/0/4428/0/MPE/0/0/0/30/1")
				.get();
		String sport = "Football";
		readDataOver(doc.select("tr"), sport);

		// ecrirText(text, "src/betvictor/betvictorBundesliga2");
	}

	public void parseBundesliga3() throws IOException {
		
		String sport = "Football";
		readDataOver(doc.select("tr"), sport);
	}

	/*
	 * public void ecrirText(String text, String path) throws IOException{
	 * PrintWriter pw=new PrintWriter(new FileWriter(path, false));
	 * BufferedWriter bw=new BufferedWriter(pw); bw.write(text); bw.close(); }
	 */

	private void readDataOver(Elements elements, String sSport) {
		Elements e = doc.select("tr");
		String ligne = "";
		
		for (Element t : e) {
			 String dates= null;
			 String player= null;
			 double odd=0;
			 String betTypes = null;
			ligne = t.text();
			if (ligne.substring(0, 4).equalsIgnoreCase("Time")) {
				// rien
			} else {

				for (Element d : t.select("td")) {
					System.out.println(d.text() + d.attributes().get("class"));
					if (d.attributes().get("class").equals("group_date")) {
						dates = d.text();
					}
					if (d.attributes().get("class").equals("date")) {
						Elements sub=d.select("span");
						dates = sub.get(0).attributes().get("data-time");
					}
					if (d.attributes().get("class").equals("event_description")) {
						player = d.text();
					}
					if (d.attributes().get("class").equals("outcome_ou")) {
						betTypes = d.text();
					}
					if (d.attributes().get("class").equals("outcome")) {
						String[] sOdd=d.text().split("/");
						
						odd = Double.valueOf(sOdd[0])/Double.valueOf(sOdd[1])+1;
				
						Sport sport = context.findSport(sSport);
						if(sport!=null){
							BetType betType = context.findBetType(betTypes);
							if(betType!=null){
								
								
								
								Date date=new Date();
								date.setTime(Long.valueOf(dates));
								Match match = context.findOrCreateMatch(sport,
										player, date);
								RefKey refKey = context.findOrCreateRefKey(match, betType);
								Bet bet = new Bet();
								bet.setOdd(odd);
								bet.setRefKey(refKey);
								bet.setCode("1111");
								bet.setDate(context.getSynchronizationDate());
								bet.setBookMaker(context.getBookMaker());
								bet.setBookmakerBetId("dummy");
								saveBet(bet);
							}
						}
						
					}

				}
				
			}

		}
	}

	@Override
	protected long convert(SynchronizerContext contextTemp, Odds dto) {
		// TODO Auto-generated method stub
		try {
			context = contextTemp;
			betVictorSynchroniser();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0L;
	}

	@Override
	protected Class<Odds> getDtoClass() {
		// TODO Auto-generated method stub
		return Odds.class;
	}
}
