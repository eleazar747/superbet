package fr.ele.services.mapping;

import java.io.IOException;
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

    }

    public void parseLigue1() throws IOException {
        String sport = "Football";
        doc = Jsoup
                .connect(
                        "http://www.betvictor.com/sports/en/football/fra-division-1/coupons/100/6270510/0/4529/0/MPE/0/0/0/30/1")
                .get();
        readDataOver(doc.select("tr"), sport);
        doc = Jsoup
                .connect(
                        "http://www.betvictor.com/sports/en/football/fra-division-1/coupons/100/6270510/0/4428/0/MPE/0/0/0/30/1")
                .get();
        readDataOver(doc.select("tr"), sport);
    }

    public void parseLigue2() throws IOException {
        String sport = "Football";
        doc = Jsoup
                .connect(
                        "http://www.betvictor.com/sports/en/football/fra-division-2/coupons/100/7006510/0/4429/0/MPE/0/0/0/30/1")
                .get();
        readDataOver(doc.select("tr"), sport);
        doc = Jsoup
                .connect(
                        "http://www.betvictor.com/sports/en/football/fra-division-2/coupons/100/7006510/0/4428/0/MPE/0/0/0/30/1")
                .get();
        readDataOver(doc.select("tr"), sport);
    }

    public void parseBundesliga() throws IOException {
        String sport = "Football";
        doc = Jsoup
                .connect(
                        "http://www.betvictor.com/sports/en/football/ger-bundesliga/coupons/100/6316510/0/4529/0/MPE/0/0/0/30/1")
                .get();

        readDataOver(doc.select("tr"), sport);
        doc = Jsoup
                .connect(
                        "http://www.betvictor.com/sports/en/football/fra-division-2/coupons/100/7006510/0/4428/0/MPE/0/0/0/30/1")
                .get();
        readDataOver(doc.select("tr"), sport);
    }

    public void parseBundesliga2() throws IOException {
        String sport = "Football";
        doc = Jsoup
                .connect(
                        "http://www.betvictor.com/sports/en/football/ger-bundesliga-2/coupons/100/5170510/0/4429/0/MPE/0/0/0/30/1")
                .get();

        readDataOver(doc.select("tr"), sport);
        doc = Jsoup
                .connect(
                        "http://www.betvictor.com/sports/en/football/fra-division-2/coupons/100/7006510/0/4428/0/MPE/0/0/0/30/1")
                .get();
        readDataOver(doc.select("tr"), sport);
        // ecrirText(text, "src/betvictor/betvictorBundesliga2");
    }

    private void readDataOver(Elements elements, String sSport) {
        Elements e = doc.select("tr");
        String ligne = "";

        for (Element t : e) {
            String dates = null;
            String player[] = null;
            String player1 = null;
            String player2 = null;
            double odd = 0;
            String betTypes = null;
            String subBetTypes = null;
            ligne = t.text();
            if (ligne.substring(0, 4).equalsIgnoreCase("Time")) {
                // rien
            } else {

                for (Element d : t.select("td")) {
                    System.out.println(d.text() + d.attributes().get("class"));

                    if (d.attributes().get("class").equals("date")) {
                        Elements sub = d.select("span");
                        dates = sub.get(0).attributes().get("data-time");
                    }
                    Sport sport = context.findSport(sSport);
                    if (d.attributes().get("class").equals("event_description")) {
                        player = d.text().split(" v ");
                        player1 = context.findTeam(sport, player[0]);
                        player2 = context.findTeam(sport, player[1]);

                    }

                    if (d.attributes().get("class").equals("outcome")) {
                        String[] sOdd = d.text().split("/");
                        Elements sub = d.select("span");
                        betTypes = sub.get(0).attributes()
                                .get("data-market_description");
                        subBetTypes = sub.get(0).attributes()
                                .get("data-outcome_description");
                        odd = Double.valueOf(sOdd[0]) / Double.valueOf(sOdd[1])
                                + 1;

                        if (player1 != null && player2 != null) {
                            if (sport != null) {
                                BetType betType = context.findBetType(betTypes);
                                if (betType != null) {

                                    Date date = new Date();
                                    date.setTime(Long.valueOf(dates));
                                    Match match = context.findOrCreateMatch(
                                            sport, date, player1, player2);
                                    RefKey refKey = context.findOrCreateRefKey(
                                            match, betType);
                                    Bet bet = new Bet();
                                    bet.setOdd(odd);
                                    bet.setRefKey(refKey);
                                    bet.setCode(subBetTypes);
                                    bet.setDate(context
                                            .getSynchronizationDate());
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
