package fr.ele.services.mapping;

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
public class bwinSynchroniser extends AbstractSynchronizer<ROOT> {

	@Override
	protected long convert(SynchronizerContext context, ROOT dto) {
		// TODO Auto-generated method stub
		long nb = 0L;
		EVENTS event = dto.getROOT2().getEVENTS();

		for (E e : event.getE()) {
			convert(context, e);
		}

		return nb;
	}

	private long convert(SynchronizerContext context, E e) {
		long nb = 0L;

		Sport sport = context.findSport(e.getSID().toString());
		if (sport != null) {

			Match match = context.findOrCreateMatch(sport, e.getN().toString()
					.toLowerCase().replaceAll(" ", "").replaceAll("-", "**"), e
					.getStdEventDateUTC().toGregorianCalendar().getTime());

			for (G g : e.getG()) {
				convert(context, g, match);
			}
		}

		return nb;
	}

	private long convert(SynchronizerContext context, G g, Match match) {
		long nb = 0L;

		BetType betType = context.findBetType(g.getN());

		for (R r : g.getR()) {
			convert(context, r, match, betType);
		}
		return nb;
	}

	private long convert(SynchronizerContext context, R r, Match match,
			BetType betType) {
		long nb = 0L;
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

}
