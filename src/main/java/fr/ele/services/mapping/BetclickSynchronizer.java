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
import fr.ele.model.BookMakers;
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
public class BetclickSynchronizer extends AbstractSynchronizer<SportsBcDto> {

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

    @Override
    protected long convert(SynchronizerContext context, SportsBcDto dto) {
        for (SportBcDto sportBcDto : dto.getSport()) {
            convert(context, sportBcDto);
        }
        return 0;
    }

    @Override
    protected BookMakers getBookMaker() {
        return BookMakers.BETCLICK;
    }

    private void convert(SynchronizerContext context, SportBcDto sportBcDto) {
        Sport sport = context.findSport(sportBcDto.getName());
        if (sport == null) {
            return;
        }
        for (EventBcDto eventBcDto : sportBcDto.getEvent()) {
            convert(context, sport, eventBcDto);
        }
    }

    private void convert(SynchronizerContext context, Sport sport,
            EventBcDto eventBcDto) {
        for (MatchBcDto matchBcDto : eventBcDto.getMatch()) {
            String matchCode = computeMatchCode(sport, eventBcDto, matchBcDto);
            Match match = context.findOrCreateMatch(sport, matchCode,
                    matchBcDto.getStartDate().toGregorianCalendar().getTime());
            for (BetBcDto betsBcDto : matchBcDto.getBets().getBet()) {
                convert(context, sport, match, betsBcDto);
            }
        }
    }

    private void convert(SynchronizerContext context, Sport sport, Match match,
            BetBcDto betBcDto) {
        String betName = betBcDto.getName();
        BetType betType = context.findBetType(betName);
        if (betType == null) {
            return;
        }
        for (Choice choice : betBcDto.getChoice()) {
            convert(context, sport, match, betType, choice);
        }
    }

    private void convert(SynchronizerContext context, Sport sport, Match match,
            BetType betType, Choice choice) {
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
        bet.setDate(context.getSynchronizationDate());
        bet.setBookMaker(context.getBookMaker());
        betRepository.save(bet);
    }

    private String computeMatchCode(Sport sport, EventBcDto eventBcDto,
            MatchBcDto matchBcDto) {
        // TODO fix code to match with player1**player2
        StringBuilder sb = new StringBuilder(sport.getCode());
        sb.append(eventBcDto.getName().replaceAll(" ", ""));
        sb.append('_');
        sb.append(matchBcDto.getName().replaceAll(" ", ""));
        return sb.toString();
    }

}
