package fr.ele.services.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ele.feeds.nordicbet.dto.Game;
import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.feeds.nordicbet.dto.OutcomeSet;
import fr.ele.model.DataMapping;
import fr.ele.model.RefEntityType;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.RefKeyRepository;
import fr.ele.services.repositories.SportRepository;

@Service
public class NordicbetSynchronizer {

	@Autowired
	private DataMappingRepository dataMappingRepository;

	@Autowired
	private SportRepository sportRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private BetTypeRepository betTypeRepository;

	@Autowired
	private BookMakerRepository bookMakerRepository;

	@Autowired
	private BetRepository betRepository;

	@Autowired
	private RefKeyRepository refKeyRepository;

	public void convert(Odds odds) {
		for (Game game : odds.getGame()) {
			convert(game);
		}

	}

	private void convert(Game game) {

		String sportNordicbetCode = game.getSport();
		BookMaker bookMaker = bookMakerRepository.findByCode("nordicbet");
		DataMapping sportMapping = dataMappingRepository
				.findOne(DataMappingRepository.Queries.findModelByBookMaker(
						RefEntityType.SPORT, bookMaker, sportNordicbetCode));
		if (sportMapping == null) {
			return;
		}
		Sport sport = sportRepository.findByCode(sportMapping.getModelCode());

		String matchCode = game.getName().toString().toLowerCase()
				.replaceAll(" ", "").replaceAll("-", "**");

		Match match = matchRepository.findByCode(matchCode);
		if (match == null) {
			match = new Match();
			match.setSport(sport);

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-DD HH:mm:ss");
			try {
				match.setDate(formatter.parse(game.getGameStartTime()
						.replaceAll("CEST", "")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			match.setCode(matchCode);
			matchRepository.save(match);
		}

		for (OutcomeSet outcomeset : game.getOutcomeSet()) {
			convert(outcomeset, game);
		}

	}

	private void convert(OutcomeSet outcomeset, Game game) {
		String sBet = outcomeset.getName().toString()
				.replaceAll(game.getName().toString(), "");

		BetType betType = findBetType(sBet);
		if (betType != null) {

		}

	}

	private BetType findBetType(String nordicbetBetType) {
		BookMaker bookMaker = bookMakerRepository.findByCode("nordicbet");
		DataMapping modelMapping = dataMappingRepository
				.findOne(DataMappingRepository.Queries.findModelByBookMaker(
						RefEntityType.BET_TYPE, bookMaker, nordicbetBetType));
		if (modelMapping == null) {
			return null;
		}
		return betTypeRepository.findByCode(modelMapping.getModelCode());
	}
}
