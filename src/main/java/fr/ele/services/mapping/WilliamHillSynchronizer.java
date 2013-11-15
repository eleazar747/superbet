package fr.ele.services.mapping;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import fr.ele.feeds.wiliamhill.dto.Market;
import fr.ele.feeds.wiliamhill.dto.Oxip;
import fr.ele.feeds.wiliamhill.dto.Participant;
import fr.ele.feeds.wiliamhill.dto.Type;
import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;

@Service("WilliamHillSynchronizer")
public class WilliamHillSynchronizer extends AbstractSynchronizer<Oxip> {

	@Override
	protected Class<Oxip> getDtoClass() {
		// TODO Auto-generated method stub
		return Oxip.class;
	}

	@Override
	protected long convert(SynchronizerContext context, Oxip oxip) {

		long nb = 0L;
		for (Type type : oxip.getResponse().getWilliamhill().getClazz()
				.getType()) {
			nb += convert(context, type);
		}

		return nb;
	}

	protected long convert(SynchronizerContext context, Type type) {
		long nb = 0L;
		Sport sport = context.findSport("Football");

		if (sport == null) {
			return nb = 0L;
		}

		for (Market market : type.getMarket()) {
			nb += convert(context, market, sport);
		}

		return nb;

	}

	private long convert(SynchronizerContext context, Market market, Sport sport) {
		long nb = 0;
		String[] matchCode = market.getName().toString().split(" - ");
		playerprint(matchCode[0]);
		String[] player = matchCode[0].split(" v ");
		if (player.length > 1) {
			String team1 = context.findTeam(sport, player[0]);
			String team2 = context.findTeam(sport, player[1]);
			Match match = context.findOrCreateMatch(sport, market
					.getBetTillDate().toGregorianCalendar().getTime(), team1,
					team2);

			BetType betType = context.findBetType(matchCode[1]);
			if (betType == null) {
				return nb = 0L;
			}

			RefKey refKey = context.findOrCreateRefKey(match, betType);

			for (Participant particpant : market.getParticipant()) {
				nb += convert(context, particpant, refKey, match);
			}
		}
		return nb;
	}

	private long convert(SynchronizerContext context, Participant participant,
			RefKey refKey, Match match) {
		Bet bet = new Bet();
		bet.setOdd(participant.getOddsDecimal());
		bet.setRefKey(refKey);
		bet.setCode(participant.getName());
		bet.setDate(context.getSynchronizationDate());
		bet.setBookmakerBetId(String.valueOf(participant.getId()));
		bet.setBookMaker(context.getBookMaker());
		saveBet(bet);
		return 1L;
	}

	private void playerprint(String match) {
		HashMap<String, String> hMap = new HashMap<String, String>();
		String[] team = match.split(" v ");
		for (String str : team) {
			if (hMap.containsKey(str) == false) {
				System.out.println(str);
			}
		}

	}
}