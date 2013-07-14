package fr.ele.services.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ele.feeds.betclick.dto.BetBcDto;
import fr.ele.feeds.betclick.dto.BetBcDto.Choice;
import fr.ele.feeds.betclick.dto.EventBcDto;
import fr.ele.feeds.betclick.dto.MatchBcDto;
import fr.ele.feeds.betclick.dto.SportBcDto;
import fr.ele.feeds.betclick.dto.SportsBcDto;
import fr.ele.model.Bet;
import fr.ele.model.RefEntityType;
import fr.ele.model.impl.BetImpl;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;
import fr.ele.model.ref.impl.MatchImpl;
import fr.ele.model.ref.impl.RefKeyImpl;
import fr.ele.services.dao.BetDao;
import fr.ele.services.dao.BetTypeDao;
import fr.ele.services.dao.BookMakerDao;
import fr.ele.services.dao.DataMappingDao;
import fr.ele.services.dao.MatchDao;
import fr.ele.services.dao.SportDao;

@Service
public class BetclickSynchronizer {

    @Autowired
    private DataMappingDao dataMappingDao;

    @Autowired
    private SportDao sportDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private BetTypeDao betTypeDao;

    @Autowired
    private BookMakerDao bookMakerDao;

    @Autowired
    private BetDao betDao;

    void convert(SportsBcDto sportsBcDto) {
        for (SportBcDto sportBcDto : sportsBcDto.getSport()) {
            convert(sportBcDto);
        }

    }

    private void convert(SportBcDto sportBcDto) {
        String sportBetclickCode = sportBcDto.getName();
        String sportCode = dataMappingDao.getModelCode(RefEntityType.SPORT,
                sportBetclickCode);
        if (sportCode == null) {
            return;
        }
        Sport sport = sportDao.findByCode(sportCode);
        for (EventBcDto eventBcDto : sportBcDto.getEvent()) {
            convert(sport, eventBcDto);
        }
    }

    private void convert(Sport sport, EventBcDto eventBcDto) {
        for (MatchBcDto matchBcDto : eventBcDto.getMatch()) {
            String matchCode = computeMatchCode(sport, eventBcDto, matchBcDto);
            Match match = matchDao.findByCode(matchCode);
            if (match == null) {
                match = new MatchImpl();
                match.setCode(matchCode);
                matchDao.create(match);
            }
            for (BetBcDto betsBcDto : matchBcDto.getBets().getBet()) {
                convert(sport, match, betsBcDto);
            }
        }
    }

    private void convert(Sport sport, Match match, BetBcDto betBcDto) {
        String betName = betBcDto.getName();
        BetType betType = findBetType(betName);
        if (betType == null) {
            return;
        }
        for (Choice choice : betBcDto.getChoice()) {
            convert(sport, match, betType, choice);
        }
    }

    private void convert(Sport sport, Match match, BetType betType,
            Choice choice) {
        RefKey refKey = new RefKeyImpl();
        refKey.setBetType(betType);
        refKey.setMatch(match);
        // TODO : refresh refKey -> create RefKeyDao and findby sport match
        // bettype

        Bet bet = new BetImpl();
        bet.setOdd(choice.getOdd().doubleValue());
        bet.setRefKey(refKey);
        bet.setBookMaker(bookMakerDao.findByCode("betclick"));
        betDao.create(bet);
    }

    private BetType findBetType(String betclickBetType) {
        String modelCode = dataMappingDao.getModelCode(RefEntityType.BET_TYPE,
                betclickBetType);
        if (modelCode == null) {
            return null;
        }
        return betTypeDao.findByCode(modelCode);
    }

    private String computeMatchCode(Sport sport, EventBcDto eventBcDto,
            MatchBcDto matchBcDto) {
        return null;
    }
}
