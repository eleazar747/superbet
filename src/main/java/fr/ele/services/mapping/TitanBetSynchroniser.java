package fr.ele.services.mapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;

@Service("TitanBetSynchroniser")
public class TitanBetSynchroniser extends AbstractSynchronizer<Odds> {
    SynchronizerContext context;

    private final String matchResult = "Match Result";

    private BufferedWriter w;

    String player1 = null;

    String player2 = null;

    @Override
    protected long convert(SynchronizerContext contextTemp, Odds dto) {
        try {
            context = contextTemp;
            try {
                TitanFootball();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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

    public void TitanFootball() throws IOException, ParseException {
        // ######## Europe ########
        // France

        parseMatchResult("Football",
                "http://sports.titanbet.com/en/t/19327/France---Ligue-1",
                matchResult);
        // Italie
        parseMatchResult("SerieA",
                "http://sports.titanbet.com/en/t/19159/Italy---Serie-A",
                matchResult);
        parseMatchResult("SerieB",
                "http://sports.titanbet.com/en/t/19328/Italy---Serie-B",
                matchResult);

        // Allemagne
        parseMatchResult("Bundesliga",
                "http://sports.titanbet.com/en/t/19158/Germany---Bundesliga-1",
                matchResult);
        parseMatchResult("Bundesliga2",
                "http://sports.titanbet.com/en/t/19158/Germany---Bundesliga-2",
                matchResult);
        parseMatchResult("Bundesliga3",
                "http://sports.titanbet.com/en/t/19158/Germany---Bundesliga-3",
                matchResult);
        parseMatchResult("Regionalliga",
                "http://sports.titanbet.com/en/t/24363/Germany---Regionalliga",
                matchResult);
        // Espagne
        parseMatchResult("Laliga",
                "http://sports.titanbet.com/en/t/19160/La-Liga", matchResult);
        parseMatchResult("LaligaSegunda",
                "http://sports.titanbet.com/en/t/19298/Segunda-Division",
                matchResult);
        parseMatchResult("SegundaB",
                "http://sports.titanbet.com/en/t/21471/Segunda-B", matchResult);
        // UK
        parseMatchResult("PremierLeague",
                "http://sports.titanbet.com/en/t/19157/Eng---Premier-League",
                matchResult);
        parseMatchResult("Championship",
                "http://sports.titanbet.com/en/t/19156/Eng---Championship",
                matchResult);
        parseMatchResult("ENGLeagueOne",
                "http://sports.titanbet.com/en/t/19326/Eng---League-One",
                matchResult);
        parseMatchResult("ENGLeagueTwo",
                "http://sports.titanbet.com/en/t/19326/Eng---League-Two",
                matchResult);
        parseMatchResult("SCOPremierLeague",
                "http://sports.titanbet.com/en/t/19208/Sco---Premier-League",
                matchResult);
        parseMatchResult("SCOChampionship",
                "http://sports.titanbet.com/en/t/19879/Sco---Championship",
                matchResult);
        parseMatchResult("SCODivision1",
                "http://sports.titanbet.com/en/t/19569/Sco---Division-1",
                matchResult);
        parseMatchResult("SCODivision2",
                "http://sports.titanbet.com/en/t/19569/Sco---Division-2",
                matchResult);
        parseMatchResult("WalesPremierLeague",
                "http://sports.titanbet.com/en/t/19881/Wales---Premier-League",
                matchResult);

        // Ireland
        parseMatchResult(
                "IrelandPremierLeague",
                "http://sports.titanbet.com/en/t/19427/Ireland---Eircom-Premier-League",
                matchResult);
        parseMatchResult("IrelandDivision1",
                "http://sports.titanbet.com/en/t/19343/Ireland---Division-1",
                matchResult);
        // Autriche
        parseMatchResult("AustriaBundesliga",
                "http://sports.titanbet.com/en/t/19347/Austria---Bundesliga",
                matchResult);
        parseMatchResult(
                "AustriaErsteDivision",
                "http://sports.titanbet.com/en/t/19371/Austria---Erste-Division",
                matchResult);
        // Belgique

        // ################### Amerique du SUD ###################
        // Argentine
        parseMatchResult(
                "ARGPrimeraDivision",
                "http://sports.titanbet.com/en/t/19296/Argentina---Primera-Division",
                matchResult);
        // Bresil
        parseMatchResult("BresilSerieA",
                "http://sports.titanbet.com/en/t/19297/Brazil---Serie-A",
                matchResult);
        parseMatchResult("BresilSerieB",
                "http://sports.titanbet.com/en/t/19297/Brazil---Serie-B",
                matchResult);
        /*
         * parse("", ""); parse("", ""); parse("", ""); parse("", "");
         */
        // Bresil
        // ##### COUPE ##########
        parseMatchResult(
                "ChampionsLeague",
                "http://sports.titanbet.com/en/t/21548/UEFA---Champions-League-Group-Betting",
                matchResult);
        parseMatchResult(
                "EuropaLeague",
                "http://sports.titanbet.com/en/t/21548/UEFA---Champions-League-Group-Betting",
                matchResult);
    }

    private void parseMatchResult(String compete, String path, String betTypes) throws IOException,
            ParseException {
        String date = "";
        String hour;
        String team1, team2;
        URL website = new URL(path);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                "94.175.237.71", 8080));
        URLConnection urlConnetion = website.openConnection(proxy);
        Document doc = Jsoup.parse(urlConnetion.getInputStream(), null, path);
        org.jsoup.select.Elements e = doc.select("tr");
        player1 = null;
        player2 = null;
        for (Element t : e) {
            date = t.select("span.date").text();
            date = formatDat(date);
            hour = t.select("span.time").text();
            team1 = t.select("span.seln-name").get(0).text();
            team2 = t.select("span.seln-name").get(2).text();
            String cote = "";
            Sport sport = context.findSport(compete);
            player1 = context.findTeam(sport, team1);
            player2 = context.findTeam(sport, team2);

            try {
                new File("/fr/ele/feeds/bwin/teamNametitanbet.txt");
                w = new BufferedWriter(new FileWriter("teamNametitanbet.txt"));
            } catch (Throwable ee) {
                ee.printStackTrace();
            }
            playerprint(team1 + " - " + team2);
            if (player1 != null && player2 != null) {
                SimpleDateFormat formatter = new SimpleDateFormat(
                        "dd/MM/yyyy HH:mm");
                date += "/" + (new Date().getYear() + 1900) + " " + hour;

                Date dates = formatter.parse(date);
                Match match = context.findOrCreateMatch(sport, player1 + "**"
                        + player2, dates);
                if (sport != null) {
                    for (int i = 0; i < 3; i++) {
                        if (i == 0) {
                            cote = t.select("span.price.dec").get(i).text();

                            convert(dates, cote, match, betTypes, player1);

                        }
                        if (i == 1) {
                            cote = t.select("span.price.dec").get(i).text();
                            convert(dates, cote, match, betTypes, "Draw");
                        }
                        if (i == 2) {
                            cote = t.select("span.price.dec").get(i).text();
                            convert(dates, cote, match, betTypes, player2);
                        }
                    }
                }
            }
        }
    }

    private String formatDat(String format) {
        String retour = "";
        int month;
        String m = "";
        String[] array = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec"};
        String tab[] = format.split(" ");
        for (int i = 0; i < array.length; i++) {
            if (tab[1].equalsIgnoreCase(array[i])) {
                month = i + 1;
                if (month < 10) {
                    m = "0" + month;
                } else {
                    m = "" + month;
                }
            }
        }
        retour = tab[0] + "/" + m;
        return retour;

    }

    protected void convert(Date date, String odd, Match match, String betTypes,
            String subBetType) {

        BetType betType = context.findBetType(betTypes);
        if (betType != null) {

            RefKey refKey = context.findOrCreateRefKey(match, betType);
            Bet bet = new Bet();
            bet.setOdd(Long.valueOf(odd));
            bet.setRefKey(refKey);
            bet.setCode(subBetType);
            bet.setDate(context.getSynchronizationDate());
            bet.setBookMaker(context.getBookMaker());
            bet.setBookmakerBetId("dummy");
            saveBet(bet);
        }

    }

    private void playerprint(String match) {
        HashMap<String, String> hMap = new HashMap<String, String>();
        String[] team = match.split(" - ");
        for (String str : team) {
            if (hMap.containsKey(str) == false) {
                try {
                    w.write(str);
                    w.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
/*
 * example <tr class="mkt mkt-7048046 " data-mkt_id="7048046"> <td
 * class="event-favourite"> <span class="favourite-button"
 * title="Add or remove this event from your favourites." data-ref_key="EVENT"
 * data-ref_id="413824">���</span> </td> <td class="time "> <div
 * class="ev ev-413824" data-ev_id="413824"> <span class="time">19:30</span>
 * <span class="date">18 Oct</span> </div> </td> <td class="will-be-inplay">
 * <span title="Live In-Play" class="will-be-inplay-inside"> <span
 * class="will-be-inplay-image "></span> </span> </td> <td class="seln "> <div>
 * <button type="button" name="add-to-slip" title="Stade Reims" class="price
 * price-28498515-LP seln-28498515 mkt-7048046 ev-413824
 * " value="Mjg0OTg1MTU6OC81Ojow
 * "> <span> <span><span class="favourite-button" title="Add or remove this team
 * from your favourites." data-ref_key="TEAM" data-ref_id="1194
 * ">���</span></span> <span class="
 * seln-name"> <span>Stade Reims</span> </span> <span class="price
 * frac">8/5</span> <span class="price dec">2.60</span> <span class="price
 * us">+160.0</span> </span> </button> </div> </td> <td
 * class="seln seln_sort-D"> <div> <button type="button" name="add-to-slip"
 * title="Draw" class="price price-28498516-LP seln-28498516 mkt-7048046
 * ev-413824 " value="Mjg0OTg1MTY6Mi8xOjow"> <span> <span class="seln-name
 * "> <span>X</span> </span> <span class="price
 * frac">2/1</span> <span class="price dec">3.00</span> <span class="price
 * us">+200.0</span> </span> </button> </div> </td> <td class="seln "> <div>
 * <button type="button" name="add-to-slip" title="Toulouse" class="price
 * price-28498514-LP seln-28498514 mkt-7048046 ev-413824
 * " value="Mjg0OTg1MTQ6Ny80Ojow
 * "> <span> <span><span class="favourite-button" title="Add or remove this team
 * from your favourites." data-ref_key="TEAM" data-ref_id="857
 * ">���</span></span> <span class="
 * seln-name"> <span>Toulouse</span> </span> <span class="price
 * frac">7/4</span> <span class="price dec">2.75</span> <span class="price
 * us">+175.0</span> </span> </button> </div> </td> <td class="mkt-count"> <a
 * title="Number of markets" href="/en/e/413824/Stade-Reims-v-Toulouse"> +86
 * </a> </td> <td class="stats"> <!-- --></td> </tr>
 */
