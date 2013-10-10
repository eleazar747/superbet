package fr.ele.services.mapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import fr.ele.feeds.bwin.dto.E;
import fr.ele.feeds.bwin.dto.EVENTS;
import fr.ele.feeds.bwin.dto.G;
import fr.ele.feeds.bwin.dto.R;
import fr.ele.feeds.bwin.dto.ROOT;
import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;

@Service("bwinSynchroniser")
public class BwinSynchroniser extends AbstractSynchronizer<ROOT> {

	private BufferedWriter w;

	@Override
	protected long convert(SynchronizerContext context, ROOT dto) {
		// TODO Auto-generated method stub
		long nb = 0L;
		try {
			File file = new File("/fr/ele/feeds/bwin/teamNameBwin.txt");
			w = new BufferedWriter(new FileWriter("teamNameBwin.txt"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EVENTS event = dto.getROOT2().getEVENTS();

		for (E e : event.getE()) {
			convert(context, e);
		}
		try {
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nb;
	}

	private long convert(SynchronizerContext context, E e) {
		long nb = 0L;

		Sport sport = context.findSport(e.getSID().toString());
		if (sport != null) {
			playerprint(e.getN().toString());
			if (e.getN().toString().contains(" - ")) {
				String team[] = e.getN().toString().split(" - ");
				String player1 = context.findTeam(team[0]);
				String player2 = context.findTeam(team[1]);
				String matchCode = player1 + "**" + player2;

				Match match = context.findOrCreateMatch(sport, matchCode, e
						.getStdEventDateUTC().toGregorianCalendar().getTime());

				for (G g : e.getG()) {
					convert(context, g, match);
				}
			}
		}

		return nb;
	}

	private long convert(SynchronizerContext context, G g, Match match) {
		long nb = 0L;

		BetType betType = context.findBetType(g.getN());
		if (betType != null) {
			for (R r : g.getR()) {
				convert(context, r, match, betType);
			}
		}
		return nb;
	}

	private long convert(SynchronizerContext context, R r, Match match,
			BetType betType) {

		RefKey refKey = context.findOrCreateRefKey(match, betType);
		Bet bet = new Bet();

		bet.setOdd(r.getO());
		bet.setRefKey(refKey);
		bet.setCode(r.getN());

		bet.setBookmakerBetId(String.valueOf(r.getDBID()));
		bet.setDate(context.getSynchronizationDate());
		bet.setBookMaker(context.getBookMaker());
		saveBet(bet);
		return 1L;
	}

	@Override
	protected Class<ROOT> getDtoClass() {
		// TODO Auto-generated method stub
		return ROOT.class;
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
