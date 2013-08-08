package fr.ele.services.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ele.feeds.expekt.dto.Alternative;
import fr.ele.feeds.expekt.dto.Category;
import fr.ele.feeds.expekt.dto.Description;
import fr.ele.feeds.expekt.dto.Game;
import fr.ele.feeds.expekt.dto.PunterOdds;
import fr.ele.model.Bet;
import fr.ele.model.DataMapping;
import fr.ele.model.RefEntityType;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.RefKeyRepository;
import fr.ele.services.repositories.SportRepository;

@Service
public class ExpektSynchronizer {

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

	public void convert(PunterOdds punterodds) {
		for (Game game : punterodds.getGame()) {
			convert(game);
		}

	}

	private void convert(Game game) {
		Description description = game.getDescription();
		Category category = (Category) description.getContent().get(1);
		String[] vDescription = ParseExpektDescription(description);
		String sporExpektCode = category.getId().toString().substring(0, 3);
		BookMaker bookMaker = bookMakerRepository.findByCode("expekt");
		DataMapping sportMapping = dataMappingRepository
				.findOne(DataMappingRepository.Queries.findModelByBookMaker(
						RefEntityType.SPORT, bookMaker, sporExpektCode));
		if (sportMapping == null) {
			return;
		}
		Sport sport = sportRepository.findByCode(sportMapping.getModelCode());

		String matchCode = vDescription[0];

		Match match = matchRepository.findByCode(matchCode);
		if (match == null) {
			match = new Match();
			match.setSport(sport);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			try {
				match.setDate(formatter.parse(game.getDate().toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			match.setCode(matchCode);
			matchRepository.save(match);
		}

		DataMapping betMapping = dataMappingRepository
				.findOne(DataMappingRepository.Queries.findModelByBookMaker(
						RefEntityType.BET_TYPE, bookMaker, vDescription[1]));
		if (betMapping != null) {
			BetType betType = findBetType(betMapping.getModelCode());
			RefKey refKey = refKeyRepository.findOne(RefKeyRepository.Queries
					.findRefKey(betType, match));

			if (refKey == null) {
				refKey = new RefKey();
				refKey.setBetType(betType);
				refKey.setMatch(match);
				refKeyRepository.save(refKey);
			}

			for (Alternative alternative : game.getAlternatives()
					.getAlternative()) {

				convert(alternative, refKey, match);
			}
		}
	}

	private void convert(Alternative alternative, RefKey refKey, Match match) {

		Bet bet = new Bet();
		bet.setOdd(alternative.getOdds());
		bet.setRefKey(refKey);
		bet.setCode(alternative.getValue());
		bet.setDate(match.getDate());
		BookMaker bookMaker = bookMakerRepository.findByCode("expekt");
		bet.setBookMaker(bookMaker);
		betRepository.save(bet);
	}

	private BetType findBetType(String expektBetType) {
		BookMaker bookMaker = bookMakerRepository.findByCode("expekt");
		DataMapping modelMapping = dataMappingRepository
				.findOne(DataMappingRepository.Queries.findModelByBookMaker(
						RefEntityType.BET_TYPE, bookMaker, expektBetType));
		if (modelMapping == null) {
			return null;
		}
		return betTypeRepository.findByCode(modelMapping.getModelCode());
	}

	private String[] ParseExpektDescription(Description description) {
		String[] strtmp = null;
		if (description.getContent().get(2).toString().contains(":")) {
			strtmp = description.getContent().get(2).toString().split(":");
			strtmp[0] = strtmp[0].replaceAll(" ", "").replaceAll("-", "vs")
					.toLowerCase();
			strtmp[1] = strtmp[1].replaceAll(" ", "").toLowerCase();

		} else {

			strtmp = new String[2];
			strtmp[0] = description.getContent().get(2).toString()
					.toLowerCase().replaceAll(" ", "").replaceAll("-", "vs");
			strtmp[1] = "Match Result";
		}
		return strtmp;
	}
}