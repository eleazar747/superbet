package fr.ele.services.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import fr.ele.feeds.intertops.dto.Cat;
import fr.ele.feeds.intertops.dto.Data;
import fr.ele.feeds.intertops.dto.L;
import fr.ele.feeds.intertops.dto.M;
import fr.ele.feeds.intertops.dto.Result;
import fr.ele.feeds.intertops.dto.S;
import fr.ele.feeds.intertops.dto.T;
import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;

@Service("IntertopsSynchronizer")
public class IntertopsSynchronizer extends AbstractSynchronizer<Result> {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh-mm");

	@Override
	protected long convert(SynchronizerContext context, Result result) {

		Data data = result.getData();
		for (S s : data.getS()) {
			convert(context, s);
		}
		return 0;
	}

	@Override
	protected Class<Result> getDtoClass() {
		// TODO Auto-generated method stub
		return Result.class;
	}

	private long convert(SynchronizerContext context, S s) {
		Cat cat = s.getCat();

		long nb = 0L;
		Sport sport = context.findSport(s.getN().toString());

		if (sport != null) {

			for (M m : cat.getM()) {
				nb += convert(context, sport, m);
			}

		}

		/*
		 * Date date= for (M m : cat.getContent().get(0)) {
		 * 
		 * }
		 * 
		 * String matchCode = computeMatchCode(m.get(0)); Sport sport= Date
		 * date= Match match = context.findOrCreateMatch(sport, matchCode,
		 * date);
		 */
		return nb;
	}

	private long convert(SynchronizerContext context, Sport sport, M m) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM");
		long nb = 0L;
		String matchCode = computeMatchCode(m);
		Date date;
		try {
			date = formatter.parse(m.getDt().replaceAll("T", " "));
			Match match = context.findOrCreateMatch(sport, matchCode, date);
			for (T t : m.getT()) {

				nb += convert(context, sport, match, t);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nb;
	}

	private long convert(SynchronizerContext context, Sport sport, Match match,
			T t) {
		long nb = 0L;
		BetType betType = context.findBetType(t.getN());
		RefKey refkey = context.findOrCreateRefKey(match, betType);

		for (L l : t.getL()) {
			nb += convert(context, refkey, l, t);

		}

		return nb;
	}

	private long convert(SynchronizerContext context, RefKey refKey, L l, T t) {

		Bet bet = new Bet();
		bet.setOdd(l.getO());
		bet.setRefKey(refKey);

		bet.setCode(t.getN());

		bet.setBookmakerBetId(String.valueOf(l.getId()));
		bet.setDate(context.getSynchronizationDate());
		bet.setBookMaker(context.getBookMaker());
		saveBet(bet);

		return 1L;
	}

	private String computeMatchCode(M m) {
		String strtmp = m.getN().toString().toLowerCase()
				.replaceAll(" v ", "**").replaceAll(" ", "");
		return strtmp;
	}

}
