package fr.ele.services.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.feeds.oddsportal.feedsOddsPortal;

@Service("oddsPortalSynchroniser")
public class oddsPortalSynchroniser extends AbstractSynchronizer<Odds> {
	private SynchronizerContext context;
	private String player1 = null;
	private final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";
	private String player2 = null;

	@Override
	protected long convert(SynchronizerContext contextTemp, Odds dto) {
		// TODO Auto-generated method stub
		context = contextTemp;
		try {
			OddsPortal();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	private void OddsPortal() throws IOException, ParseException {
		// parseOdds("http://www.oddsportal.com/sure-bets/");
		/**
		 * if (System.getProperty(SPRING_PROFILES_ACTIVE).equals(
		 * ApplicationProfiles.TEST)) {
		 */

		InputStream input = feedsOddsPortal.class
				.getResourceAsStream(("/fr/ele/feeds/oddsportal/odds.html"));
		Document doc = Jsoup.parse(input, "UTF-8", "www.google.Fr");
		parseOdds(doc);
		/**
		 * } else { URL website = new
		 * URL("http://www.oddsportal.com/sure-bets/"); Proxy proxy = new
		 * Proxy(Proxy.Type.HTTP, new InetSocketAddress( "94.175.237.71",
		 * 8080)); URLConnection urlConnetion = website.openConnection(proxy);
		 * Document doc = Jsoup.parse(urlConnetion.getInputStream(), null,
		 * "http://www.oddsportal.com/sure-bets/"); parseOdds(doc);
		 * 
		 * }
		 */

	}

	private void parseOdds(Document doc) throws IOException, ParseException {
		String date = "";
		String hour;
		String team1, team2;
		/**
		 * URL website = new URL(path); Proxy proxy = new Proxy(Proxy.Type.HTTP,
		 * new InetSocketAddress( "94.175.237.71", 8080)); URLConnection
		 * urlConnetion = website.openConnection(proxy); Document doc =
		 * Jsoup.parse(urlConnetion.getInputStream(), null, path);
		 */
		org.jsoup.select.Elements e = doc.select("tr");
		player1 = null;
		player2 = null;
		for (Element t : e) {

			date = t.select("span.date").text();
			// date = formatDat(date);
			hour = t.select("span.time").text();
			// team1 = t.select("span.seln-name").get(0).text();
			// team2 = t.select("span.seln-name").get(2).text();
			String cote = "";
			// Sport sport = context.findSport(compete);
			// player1 = context.findTeam(sport, team1);
			// player2 = context.findTeam(sport, team2);
		}

	}
}

/***
 * <table class=" table-main">
 * <colgroup><col width="50" /><col width="*" /><col width="20" /><col
 * width="50" /><col width="75" /><col width="75" /><col width="75" /><col
 * width="30" /><col width="30" /><col width="5" /></colgroup><tbody>
 * <tr class="dark center" xtid="14890">
 * <th class="first2 tl" colspan="10"><a class="bfl sicona s5"
 * href="/american-football/">American Football</a><span class="bflp">»</span><a
 * class="bfl" href="/american-football/usa/"><span
 * class="ficon f-200">&nbsp;</span>USA</a><span class="bflp">»</span><a
 * href="/american-football/usa/nfl/">NFL</a></th>
 * </tr>
 * <th colspan="5">Home/Away, FT including OT</th>
 * <th class="center">1</th>
 * <th class="center">2</th>
 * <th class="center">Profit</th>
 * <th colspan="2"></th>
 * <tr class="odd" xeid="xzmpq2zc">
 * <td class="table-time datet t1385430000-6-1-0-0 "></td>
 * <td class="" colspan="4"><a href=
 * "/american-football/usa/nfl-2013-2014/washington-redskins-san-francisco-49ers-xzmpq2zc/#home-away;1"
 * >Washington Redskins - San Francisco 49ers</a></td>
 * <td class="center"><a href="/bookmaker/888sport/surebet/" title="888sport"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l27">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="xz9fxz9"></div></td>
 * <td class="center"><a href="/bookmaker/paf/surebet/" title="Paf"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l101">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="azo8fazo8"></div></td>
 * <td class="center bold">6.4%</td>
 * <td><span class="calc" xparam="Click here to calculate stakes" onclick=
 * "popupOpen('/sure-bets-calculator/eod/eyJ4em1wcTJ6YyI6eyIzIjpbeyIwLjAwIjp7IjEiOlt7Im9kZHMiOjIuOSwicHJvdmlkZXIiOjI3fSx7Im9kZHMiOjEuNjgsInByb3ZpZGVyIjoxMDF9XX19XX19/from/sure-bets/');"
 * >&nbsp;</span></td>
 * <tdclass="check ch1" xparam="FRESH ODDS<br />
 * (Last sure bet update: 2 min ago)~2"><span></span></td>
 * </tr>
 * <tr class="dark center" xtid="15444">
 * <th class="first2 tl" colspan="10"><a class="bfl sicona s1"
 * href="/soccer/">Soccer</a><span class="bflp">»</span><a class="bfl"
 * href="/soccer/czech-republic/"><span class="ficon f-62">&nbsp;</span>Czech
 * Republic</a><span class="bflp">»</span><a
 * href="/soccer/czech-republic/youth-league/">Youth League</a></th>
 * </tr>
 * <th colspan="4">1X2</th>
 * <th class="center">1</th>
 * <th class="center">X</th>
 * <th class="center">2</th>
 * <th class="center">Profit</th>
 * <th colspan="2"></th>
 * <tr class="odd" xeid="hALZNYSl">
 * <td class="table-time datet t1385373600-6-1-0-0 "></td>
 * <td class="name table-participant" colspan="3"><a href=
 * "/soccer/czech-republic/youth-league-2013-2014/ostrava-slavia-prague-hALZNYSl/#1X2;2"
 * >Ostrava U21 - Slavia Prague U21</a></td>
 * <td class="center"><a href="/bookmaker/betvictor/surebet/" title="BetVictor"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l76">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="czefcze"></div></td>
 * <td class="center"><a href="/bookmaker/oddsring/surebet/" title="Oddsring"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l393">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="tz0eftz0e"></div></td>
 * <td class="center"><a href="/bookmaker/mybet/surebet/" title="myBet"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l41">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="xzcfxzc"></div></td>
 * <td class="center bold">3.4%</td>
 * <td><span class="calc" xparam="Click here to calculate stakes" onclick=
 * "popupOpen('/sure-bets-calculator/eod/eyJoQUxaTllTbCI6eyIxIjpbeyIwLjAwIjp7IjIiOlt7Im9kZHMiOjMuNSwicHJvdmlkZXIiOjc2fSx7Im9kZHMiOjQuMDUsInByb3ZpZGVyIjozOTN9LHsib2RkcyI6Mi4zLCJwcm92aWRlciI6NDF9XX19XX19/from/sure-bets/');"
 * >&nbsp;</span></td>
 * <tdclass="check ch1" xparam="FRESH ODDS<br />
 * (Last sure bet update: 4 min ago)~2"><span></span></td>
 * </tr>
 * <tr class="dark center" xtid="18146">
 * <th class="first2 tl" colspan="10"><a class="bfl sicona s3"
 * href="/basketball/">Basketball</a><span class="bflp">»</span><a class="bfl"
 * href="/basketball/lithuania/"><span
 * class="ficon f-116">&nbsp;</span>Lithuania</a><span class="bflp">»</span><a
 * href="/basketball/lithuania/lkl/">LKL</a></th>
 * </tr>
 * <th colspan="5">Home/Away, FT including OT</th>
 * <th class="center">1</th>
 * <th class="center">2</th>
 * <th class="center">Profit</th>
 * <th colspan="2"></th>
 * <tr class="odd" xeid="nqaeSmti">
 * <td class="table-time datet t1385398800-6-1-0-0 "></td>
 * <td class="name table-participant" colspan="4"><a href=
 * "/basketball/lithuania/lkl-2013-2014/zalgiris-kaunas-lietkabelis-nqaeSmti/#home-away;1"
 * >Zalgiris Kaunas - Lietkabelis</a></td>
 * <td class="center"><a href="/bookmaker/sportsbettingonline/surebet/"
 * title="SportsBettingOnline"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l385">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="az0ofaz0o"></div></td>
 * <td class="center"><a href="/bookmaker/paddy-power/surebet/"
 * title="Paddy Power" onclick="window.open(this.href); return false;"><span
 * class="logos l60">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="tafta"></div></td>
 * <td class="center bold">3.3%</td>
 * <td><span class="calc" xparam="Click here to calculate stakes" onclick=
 * "popupOpen('/sure-bets-calculator/eod/eyJucWFlU210aSI6eyIzIjpbeyIwLjAwIjp7IjEiOlt7Im9kZHMiOjEuMDYsInByb3ZpZGVyIjozODV9LHsib2RkcyI6NDEsInByb3ZpZGVyIjo2MH1dfX1dfX0=/from/sure-bets/');"
 * >&nbsp;</span></td>
 * <tdclass="check ch1" xparam="FRESH ODDS<br />
 * (Last sure bet update: 0 min ago)~2"><span></span></td>
 * </tr>
 * <tr class="dark center" xtid="14890">
 * <th class="first2 tl" colspan="10"><a class="bfl sicona s5"
 * href="/american-football/">American Football</a><span class="bflp">»</span><a
 * class="bfl" href="/american-football/usa/"><span
 * class="ficon f-200">&nbsp;</span>USA</a><span class="bflp">»</span><a
 * href="/american-football/usa/nfl/">NFL</a></th>
 * </tr>
 * <th colspan="4">Over/Under 47.5, FT including OT</th>
 * <th class="center">Total</th>
 * <th class="center">Over</th>
 * <th class="center">Under</th>
 * <th class="center">Profit</th>
 * <th colspan="2"></th>
 * <tr class="odd" xeid="jq0WfaZ8">
 * <td class="table-time datet t1385931900-6-1-0-0 "></td>
 * <td class="name table-participant" colspan="3"><a href=
 * "/american-football/usa/nfl-2013-2014/buffalo-bills-atlanta-falcons-jq0WfaZ8/#over-under;1;47.50;0"
 * >Buffalo Bills - Atlanta Falcons</a></td>
 * <td class="center">47.5</td>
 * <td class="center"><a href="/bookmaker/pinnacle-sports/surebet/"
 * title="Pinnacle Sports" onclick="window.open(this.href); return false;"><span
 * class="logos l18">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="xzxfxzx"></div></td>
 * <td class="center"><a href="/bookmaker/betonline/surebet/" title="BetOnline"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l78">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="az9afaz9a"></div></td>
 * <td class="center bold">2.2%</td>
 * <td><span class="calc" xparam="Click here to calculate stakes" onclick=
 * "popupOpen('/sure-bets-calculator/eod/eyJqcTBXZmFaOCI6eyIyIjpbeyI0Ny41MCI6eyIxIjpbeyJvZGRzIjoyLjIsInByb3ZpZGVyIjoxOH0seyJvZGRzIjoxLjkxLCJwcm92aWRlciI6Nzh9XX19XX19/from/sure-bets/');"
 * >&nbsp;</span></td>
 * <tdclass="check ch1" xparam="FRESH ODDS<br />
 * (Last sure bet update: 0 min ago)~2"><span></span></td>
 * </tr>
 * <tr class="dark center" xtid="10605">
 * <th class="first2 tl" colspan="10"><a class="bfl sicona s1"
 * href="/soccer/">Soccer</a><span class="bflp">»</span><a class="bfl"
 * href="/soccer/england/"><span
 * class="ficon f-198">&nbsp;</span>England</a><span class="bflp">»</span><a
 * href="/soccer/england/league-one/">League One</a></th>
 * </tr>
 * <th colspan="4">Over/Under 1.5, 1st Half</th>
 * <th class="center">Total</th>
 * <th class="center">Over</th>
 * <th class="center">Under</th>
 * <th class="center">Profit</th>
 * <th colspan="2"></th>
 * <tr class="odd" xeid="C6SDqcpe">
 * <td class="table-time datet t1385495100-6-1-0-0 "></td>
 * <td class="name table-participant" colspan="3"><a href=
 * "/soccer/england/league-one-2013-2014/shrewsbury-oldham-C6SDqcpe/#over-under;3;1.50;0"
 * >Shrewsbury - Oldham</a></td>
 * <td class="center">1.5</td>
 * <td class="center"><a href="/bookmaker/coral/surebet/" title="Coral"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l71">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="czefcze"></div></td>
 * <td class="center"><a href="/bookmaker/boylesports/surebet/"
 * title="Boylesports" onclick="window.open(this.href); return false;"><span
 * class="logos l30">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="azttfaztt"></div></td>
 * <td class="center bold">2.0%</td>
 * <td><span class="calc" xparam="Click here to calculate stakes" onclick=
 * "popupOpen('/sure-bets-calculator/eod/eyJDNlNEcWNwZSI6eyIyIjpbeyIxLjUwIjp7IjMiOlt7Im9kZHMiOjMuNSwicHJvdmlkZXIiOjcxfSx7Im9kZHMiOjEuNDQsInByb3ZpZGVyIjozMH1dfX1dfX0=/from/sure-bets/');"
 * >&nbsp;</span></td>
 * <tdclass="check ch1" xparam="FRESH ODDS<br />
 * (Last sure bet update: 2 min ago)~2"><span></span></td>
 * </tr>
 * <th colspan="4">1X2</th>
 * <th class="center">1</th>
 * <th class="center">X</th>
 * <th class="center">2</th>
 * <th class="center">Profit</th>
 * <th colspan="2"></th>
 * <tr class="odd" xeid="MyA46Ki8">
 * <td class="table-time datet t1385495100-6-1-0-0 "></td>
 * <td class="name table-participant" colspan="3"><a
 * href="/soccer/england/league-one-2013-2014/carlisle-crewe-MyA46Ki8/#1X2;2"
 * >Carlisle - Crewe</a></td>
 * <td class="center"><a href="/bookmaker/pinnacle-sports/surebet/"
 * title="Pinnacle Sports" onclick="window.open(this.href); return false;"><span
 * class="logos l18">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="xzxafxzxa"></div></td>
 * <td class="center"><a href="/bookmaker/bet365/surebet/" title="bet365"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l16">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="czofczo"></div></td>
 * <td class="center"><a href="/bookmaker/boylesports/surebet/"
 * title="Boylesports" onclick="window.open(this.href); return false;"><span
 * class="logos l30">&nbsp;</span></a><div class="odds-nowrp" xodd="tft"></div></td>
 * <td class="center bold">2.0%</td>
 * <td><span class="calc" xparam="Click here to calculate stakes" onclick=
 * "popupOpen('/sure-bets-calculator/eod/eyJNeUE0NktpOCI6eyIxIjpbeyIwLjAwIjp7IjIiOlt7Im9kZHMiOjIuMjEsInByb3ZpZGVyIjoxOH0seyJvZGRzIjozLjYsInByb3ZpZGVyIjoxNn0seyJvZGRzIjo0LCJwcm92aWRlciI6MzB9XX19XX19/from/sure-bets/');"
 * >&nbsp;</span></td>
 * <tdclass="check ch1" xparam="FRESH ODDS<br />
 * (Last sure bet update: 2 min ago)~2"><span></span></td>
 * </tr>
 * <tr class="dark center" xtid="10162">
 * <th class="first2 tl" colspan="10"><a class="bfl sicona s1"
 * href="/soccer/">Soccer</a><span class="bflp">»</span><a class="bfl"
 * href="/soccer/europe/"><span class="ficon f-6">&nbsp;</span>Europe</a><span
 * class="bflp">»</span><a href="/soccer/europe/europa-league/">Europa
 * League</a></th>
 * </tr>
 * <th colspan="4">Over/Under 1.5, 1st Half</th>
 * <th class="center">Total</th>
 * <th class="center">Over</th>
 * <th class="center">Under</th>
 * <th class="center">Profit</th>
 * <th colspan="2"></th>
 * <tr class="odd" xeid="MqEeSxnA">
 * <td class="table-time datet t1385669100-6-1-0-0 "></td>
 * <td class="name table-participant" colspan="3"><a href=
 * "/soccer/europe/europa-league-2013-2014/ferreira-fiorentina-MqEeSxnA/#over-under;3;1.50;0"
 * >Ferreira - Fiorentina</a></td>
 * <td class="center">1.5</td>
 * <td class="center"><a href="/bookmaker/coral/surebet/" title="Coral"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l71">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="czefcze"></div></td>
 * <td class="center"><a href="/bookmaker/bwin/surebet/" title="bwin"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l2">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="azttfaztt"></div></td>
 * <td class="center bold">2.0%</td>
 * <td><span class="calc" xparam="Click here to calculate stakes" onclick=
 * "popupOpen('/sure-bets-calculator/eod/eyJNcUVlU3huQSI6eyIyIjpbeyIxLjUwIjp7IjMiOlt7Im9kZHMiOjMuNSwicHJvdmlkZXIiOjcxfSx7Im9kZHMiOjEuNDQsInByb3ZpZGVyIjoyfV19fV19fQ==/from/sure-bets/');"
 * >&nbsp;</span></td>
 * <tdclass="check ch1" xparam="FRESH ODDS<br />
 * (Last sure bet update: 0 min ago)~2"><span></span></td>
 * </tr>
 * <tr class="dark center" xtid="11688">
 * <th class="first2 tl" colspan="10"><a class="bfl sicona s1"
 * href="/soccer/">Soccer</a><span class="bflp">»</span><a class="bfl"
 * href="/soccer/spain/"><span class="ficon f-176">&nbsp;</span>Spain</a><span
 * class="bflp">»</span><a href="/soccer/spain/primera-division/">Primera
 * Division</a></th>
 * </tr>
 * <th colspan="4">Over/Under 1, 1st Half</th>
 * <th class="center">Total</th>
 * <th class="center">Over</th>
 * <th class="center">Under</th>
 * <th class="center">Profit</th>
 * <th colspan="2"></th>
 * <tr class="odd" xeid="QqHfXixf">
 * <td class="table-time datet t1385913600-6-1-0-0 "></td>
 * <td class="name table-participant" colspan="3"><a href=
 * "/soccer/spain/primera-division-2013-2014/granada-cf-sevilla-QqHfXixf/#over-under;3;1.00;0"
 * >Granada CF - Sevilla</a></td>
 * <td class="center">1</td>
 * <td class="center"><a href="/bookmaker/sbobet/surebet/" title="SBOBET"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l75">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="xzxcfxzxc"></div></td>
 * <td class="center"><a href="/bookmaker/canbet/surebet/" title="Canbet"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l12">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="az8pfaz8p"></div></td>
 * <td class="center bold">1.7%</td>
 * <td><span class="calc" xparam="Click here to calculate stakes" onclick=
 * "popupOpen('/sure-bets-calculator/eod/eyJRcUhmWGl4ZiI6eyIyIjpbeyIxLjAwIjp7IjMiOlt7Im9kZHMiOjIuMjMsInByb3ZpZGVyIjo3NX0seyJvZGRzIjoxLjg3LCJwcm92aWRlciI6MTJ9XX19XX19/from/sure-bets/');"
 * >&nbsp;</span></td>
 * <tdclass="check ch1" xparam="FRESH ODDS<br />
 * (Last sure bet update: 2 min ago)~2"><span></span></td>
 * </tr>
 * <tr class="dark center" xtid="18303">
 * <th class="first2 tl" colspan="10"><a class="bfl sicona s3"
 * href="/basketball/">Basketball</a><span class="bflp">»</span><a class="bfl"
 * href="/basketball/ukraine/"><span
 * class="ficon f-195">&nbsp;</span>Ukraine</a><span class="bflp">»</span><a
 * href="/basketball/ukraine/superleague/">Superleague</a></th>
 * </tr>
 * <th colspan="5">Home/Away, FT including OT</th>
 * <th class="center">1</th>
 * <th class="center">2</th>
 * <th class="center">Profit</th>
 * <th colspan="2"></th>
 * <tr class="odd" xeid="Gr5tgk2q">
 * <td class="table-time datet t1385395200-6-1-0-0 "></td>
 * <td class="name table-participant" colspan="4"><a href=
 * "/basketball/ukraine/superleague-2013-2014/bc-kiev-goverla-Gr5tgk2q/#home-away;1"
 * >BC Kiev - Goverla</a></td>
 * <td class="center"><a href="/bookmaker/pinnacle-sports/surebet/"
 * title="Pinnacle Sports" onclick="window.open(this.href); return false;"><span
 * class="logos l18">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="cz0cfcz0c"></div></td>
 * <td class="center"><a href="/bookmaker/sportingbet/surebet/"
 * title="Sportingbet" onclick="window.open(this.href); return false;"><span
 * class="logos l38">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="azecfazec"></div></td>
 * <td class="center bold">1.7%</td>
 * <td><span class="calc" xparam="Click here to calculate stakes" onclick=
 * "popupOpen('/sure-bets-calculator/eod/eyJHcjV0Z2sycSI6eyIzIjpbeyIwLjAwIjp7IjEiOlt7Im9kZHMiOjMuMDMsInByb3ZpZGVyIjoxOH0seyJvZGRzIjoxLjUzLCJwcm92aWRlciI6Mzh9XX19XX19/from/sure-bets/');"
 * >&nbsp;</span></td>
 * <tdclass="check ch1" xparam="FRESH ODDS<br />
 * (Last sure bet update: 3 min ago)~2"><span></span></td>
 * </tr>
 * <tr class="dark center" xtid="11696">
 * <th class="first2 tl" colspan="10"><a class="bfl sicona s1"
 * href="/soccer/">Soccer</a><span class="bflp">»</span><a class="bfl"
 * href="/soccer/belgium/"><span
 * class="ficon f-32">&nbsp;</span>Belgium</a><span class="bflp">»</span><a
 * href="/soccer/belgium/jupiler-league/">Jupiler League</a></th>
 * </tr>
 * <th colspan="4">1X2</th>
 * <th class="center">1</th>
 * <th class="center">X</th>
 * <th class="center">2</th>
 * <th class="center">Profit</th>
 * <th colspan="2"></th>
 * <tr class="odd" xeid="b1VmupLu">
 * <td class="table-time datet t1385838000-6-1-0-0 "></td>
 * <td class="name table-participant" colspan="3"><a href=
 * "/soccer/belgium/jupiler-league-2013-2014/mons-lokeren-b1VmupLu/#1X2;2">Mons
 * - Lokeren</a></td>
 * <td class="center"><a href="/bookmaker/mybet/surebet/" title="myBet"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l41">&nbsp;</span></a><div class="odds-nowrp" xodd="tft"></div></td>
 * <td class="center"><a href="/bookmaker/bwin/surebet/" title="bwin"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l2">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="cztfczt"></div></td>
 * <td class="center"><a href="/bookmaker/sbobet/surebet/" title="SBOBET"
 * onclick="window.open(this.href); return false;"><span
 * class="logos l75">&nbsp;</span></a><div class="odds-nowrp"
 * xodd="xzxpfxzxp"></div></td>
 * <td class="center bold">1.6%</td>
 * <td><span class="calc" xparam="Click here to calculate stakes" onclick=
 * "popupOpen('/sure-bets-calculator/eod/eyJiMVZtdXBMdSI6eyIxIjpbeyIwLjAwIjp7IjIiOlt7Im9kZHMiOjQsInByb3ZpZGVyIjo0MX0seyJvZGRzIjozLjQsInByb3ZpZGVyIjoyfSx7Im9kZHMiOjIuMjcsInByb3ZpZGVyIjo3NX1dfX1dfX0=/from/sure-bets/');"
 * >&nbsp;</span></td>
 * <tdclass="check ch1" xparam="FRESH ODDS<br />
 * (Last sure bet update: 2 min ago)~2"><span></span></td>
 * </tr>
 * </tbody>
 * </table>
 */

