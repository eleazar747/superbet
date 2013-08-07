package fr.ele.services.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ele.feeds.expekt.dto.Category;
import fr.ele.feeds.expekt.dto.Description;
import fr.ele.feeds.expekt.dto.Game;
import fr.ele.feeds.expekt.dto.PunterOdds;
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

		String sporExpektCode = category.getId().toString().substring(0, 3);
		BookMaker bookMaker = bookMakerRepository.findByCode("expekt");
		DataMapping sportMapping = dataMappingRepository
				.findOne(DataMappingRepository.Queries.findModelByBookMaker(
						RefEntityType.SPORT, bookMaker, sporExpektCode));
		if (sportMapping == null) {
			return;
		}
		Sport sport = sportRepository.findByCode(sportMapping.getModelCode());

		String matchCode = computeMatchCode(sport, description, category);

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

	}

	/**
	 * private void convert(Sport sport, Match match, BetType betType, Choice
	 * choice) { RefKey refKey =
	 * refKeyRepository.findOne(RefKeyRepository.Queries .findRefKey(betType,
	 * match)); if (refKey == null) { refKey = new RefKey();
	 * refKey.setBetType(betType); refKey.setMatch(match);
	 * refKeyRepository.save(refKey); }
	 * 
	 * Bet bet = new Bet(); bet.setOdd(choice.getOdd().doubleValue());
	 * bet.setRefKey(refKey); BookMaker bookMaker =
	 * bookMakerRepository.findByCode("Expekt"); bet.setBookMaker(bookMaker);
	 * betRepository.save(bet); }
	 */

	private BetType findBetType(String betclickBetType) {
		BookMaker bookMaker = bookMakerRepository.findByCode("expekt");
		DataMapping modelMapping = dataMappingRepository
				.findOne(DataMappingRepository.Queries.findModelByBookMaker(
						RefEntityType.SPORT, bookMaker, betclickBetType));
		if (modelMapping == null) {
			return null;
		}
		return betTypeRepository.findByCode(modelMapping.getModelCode());
	}

	private String computeMatchCode(Sport sport, Description description,
			Category category) {
		StringBuilder sb = new StringBuilder(sport.getCode());
		sb.append(category.getValue().replaceAll(" ", ""));
		sb.append('_');

		if (description.getContent().get(2).toString().contains(":")) {
			String[] strtmp = description.getContent().get(2).toString()
					.split(":");
			sb.append(strtmp[0].replaceAll(" ", "").replaceAll("-", "vs"));

		} else {
			sb.append(description.getContent().get(2).toString()
					.replaceAll(" ", "").replaceAll("-", "vs"));
		}

		return sb.toString();
	}
}
