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
import fr.ele.model.DataMapping;
import fr.ele.model.RefEntityType;
import fr.ele.model.ref.BetType;
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
public class BetclickSynchronizer {

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

    void convert(SportsBcDto sportsBcDto) {
        for (SportBcDto sportBcDto : sportsBcDto.getSport()) {
            convert(sportBcDto);
        }

    }

    private void convert(SportBcDto sportBcDto) {
        String sportBetclickCode = sportBcDto.getName();
        DataMapping sportMapping = dataMappingRepository
                .findOne(DataMappingRepository.Queries.findModelByBookMaker(
                        RefEntityType.SPORT, sportBetclickCode));
        if (sportMapping == null) {
            return;
        }
        Sport sport = sportRepository.findByCode(sportMapping.getModelCode());
        for (EventBcDto eventBcDto : sportBcDto.getEvent()) {
            convert(sport, eventBcDto);
        }
    }

    private void convert(Sport sport, EventBcDto eventBcDto) {
        for (MatchBcDto matchBcDto : eventBcDto.getMatch()) {
            String matchCode = computeMatchCode(sport, eventBcDto, matchBcDto);
            Match match = matchRepository.findByCode(matchCode);
            if (match == null) {
                match = new Match();
                match.setSport(sport);
                match.setDate(matchBcDto.getStartDate().toGregorianCalendar()
                        .getTime());
                match.setCode(matchCode);
                matchRepository.save(match);
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
        RefKey refKey = refKeyRepository.findOne(RefKeyRepository.Queries
                .findRefKey(betType, match));
        if (refKey == null) {
            refKey = new RefKey();
            refKey.setBetType(betType);
            refKey.setMatch(match);
            refKeyRepository.save(refKey);
        }

        Bet bet = new Bet();
        bet.setOdd(choice.getOdd().doubleValue());
        bet.setRefKey(refKey);
        bet.setBookMaker(bookMakerRepository.findByCode("betclick"));
        betRepository.save(bet);
    }

    private BetType findBetType(String betclickBetType) {
        DataMapping modelMapping = dataMappingRepository
                .findOne(DataMappingRepository.Queries.findModelByBookMaker(
                        RefEntityType.SPORT, betclickBetType));
        if (modelMapping == null) {
            return null;
        }
        return betTypeRepository.findByCode(modelMapping.getModelCode());
    }

    private String computeMatchCode(Sport sport, EventBcDto eventBcDto,
            MatchBcDto matchBcDto) {
        StringBuilder sb = new StringBuilder(sport.getCode());
        sb.append(eventBcDto.getName().replaceAll(" ", ""));
        sb.append('_');
        sb.append(matchBcDto.getName().replaceAll(" ", ""));
        return sb.toString();
    }
}
