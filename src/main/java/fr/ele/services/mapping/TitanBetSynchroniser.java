package fr.ele.services.mapping;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import fr.ele.feeds.nordicbet.dto.Odds;

@Service("TitanBetSynchroniser")
public class TitanBetSynchroniser extends AbstractSynchronizer<Odds> {

	@Override
	protected long convert(SynchronizerContext context, Odds dto) {
		// TODO Auto-generated method stub
		long nb = 0L;

		return nb;
	}

	@Override
	protected Class<Odds> getDtoClass() {
		// TODO Auto-generated method stub
		return Odds.class;
	}

	public void TitanFootball() throws IOException, ParseException {
		// ######## Europe ########
		// France
		parse("Ligue1",
				"http://sports.titanbet.com/en/t/19327/France---Ligue-1");
		// Italie
		parse("SerieA", "http://sports.titanbet.com/en/t/19159/Italy---Serie-A");
		parse("SerieB", "http://sports.titanbet.com/en/t/19328/Italy---Serie-B");

		// Allemagne
		parse("Bundesliga",
				"http://sports.titanbet.com/en/t/19158/Germany---Bundesliga-1");
		parse("Bundesliga2",
				"http://sports.titanbet.com/en/t/19158/Germany---Bundesliga-2");
		parse("Bundesliga3",
				"http://sports.titanbet.com/en/t/19158/Germany---Bundesliga-3");
		parse("Regionalliga",
				"http://sports.titanbet.com/en/t/24363/Germany---Regionalliga");
		// Espagne
		parse("Laliga", "http://sports.titanbet.com/en/t/19160/La-Liga");
		parse("LaligaSegunda",
				"http://sports.titanbet.com/en/t/19298/Segunda-Division");
		parse("SegundaB", "http://sports.titanbet.com/en/t/21471/Segunda-B");
		// UK
		parse("PremierLeague",
				"http://sports.titanbet.com/en/t/19157/Eng---Premier-League");
		parse("Championship",
				"http://sports.titanbet.com/en/t/19156/Eng---Championship");
		parse("ENGLeagueOne",
				"http://sports.titanbet.com/en/t/19326/Eng---League-One");
		parse("ENGLeagueTwo",
				"http://sports.titanbet.com/en/t/19326/Eng---League-Two");
		parse("SCOPremierLeague",
				"http://sports.titanbet.com/en/t/19208/Sco---Premier-League");
		parse("SCOChampionship",
				"http://sports.titanbet.com/en/t/19879/Sco---Championship");
		parse("SCODivision1",
				"http://sports.titanbet.com/en/t/19569/Sco---Division-1");
		parse("SCODivision2",
				"http://sports.titanbet.com/en/t/19569/Sco---Division-2");
		parse("WalesPremierLeague",
				"http://sports.titanbet.com/en/t/19881/Wales---Premier-League");

		// Ireland
		parse("IrelandPremierLeague",
				"http://sports.titanbet.com/en/t/19427/Ireland---Eircom-Premier-League");
		parse("IrelandDivision1",
				"http://sports.titanbet.com/en/t/19343/Ireland---Division-1");
		// Autriche
		parse("AustriaBundesliga",
				"http://sports.titanbet.com/en/t/19347/Austria---Bundesliga");
		parse("AustriaErsteDivision",
				"http://sports.titanbet.com/en/t/19371/Austria---Erste-Division");
		// Belgique

		// ################### Amerique du SUD ###################
		// Argentine
		parse("ARGPrimeraDivision",
				"http://sports.titanbet.com/en/t/19296/Argentina---Primera-Division");
		// Bresil
		parse("BresilSerieA",
				"http://sports.titanbet.com/en/t/19297/Brazil---Serie-A");
		parse("BresilSerieB",
				"http://sports.titanbet.com/en/t/19297/Brazil---Serie-B");
		/*
		 * parse("", ""); parse("", ""); parse("", ""); parse("", "");
		 */
		// Bresil
		// ##### COUPE ##########
		parse("ChampionsLeague",
				"http://sports.titanbet.com/en/t/21548/UEFA---Champions-League-Group-Betting");
		parse("EuropaLeague",
				"http://sports.titanbet.com/en/t/21548/UEFA---Champions-League-Group-Betting");
	}

	private void parse(String compete, String path) throws IOException,
			ParseException {
		String ligne = "";
		String text = "";
		String date = "";
		String hour;
		String team1, team2;
		String cote1, coteX, cote2;
		org.jsoup.select.Elements element;
		URL website = new URL(path);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"94.175.237.71", 8080));
		URLConnection urlConnetion = website.openConnection(proxy);
		Document doc = Jsoup.parse(urlConnetion.getInputStream(), null, path);
		org.jsoup.select.Elements e = doc.select("tr");
		for (Element t : e) {
			date = t.select("span.date").text();
			date = formatDat(date);
			hour = t.select("span.time").text();
			team1 = t.select("span.seln-name").get(0).text();
			team2 = t.select("span.seln-name").get(2).text();
			cote1 = t.select("span.price.dec").get(0).text();
			coteX = t.select("span.price.dec").get(1).text();
			cote2 = t.select("span.price.dec").get(2).text();
			ligne = compete + "\t" + date + "\t" + hour + "\t" + team1 + "\t"
					+ team2 + "\t" + cote1 + "\t" + coteX + "\t" + cote2;
			text += ligne + "\n";
		}
		System.out.println(text);
	}

	private String formatDat(String format) {
		String retour = "";
		int month;
		String m = "";
		String[] array = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
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
}
