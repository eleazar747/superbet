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
import fr.ele.services.repositories.RefKeyRepository;

@Service("BetclickSynchronizer")
public class BetclickSynchronizer extends AbstractSynchronizer<SportsBcDto> {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private RefKeyRepository refKeyRepository;

    @Override
    protected long convert(SynchronizerContext context, SportsBcDto dto) {
        long nb = 0L;
        for (SportBcDto sportBcDto : dto.getSport()) {
            nb += convert(context, sportBcDto);
        }
        return nb;
    }

    @Override
    protected BookMakers getBookMaker() {
        return BookMakers.BETCLICK;
    }

    private long convert(SynchronizerContext context, SportBcDto sportBcDto) {
        Sport sport = context.findSport(sportBcDto.getName());
        if (sport == null) {
            return 0L;
        }
        long nb = 0L;
        for (EventBcDto eventBcDto : sportBcDto.getEvent()) {
            nb += convert(context, sport, eventBcDto);
        }
        return nb;
    }

    private long convert(SynchronizerContext context, Sport sport,
            EventBcDto eventBcDto) {
        long nb = 0L;
        for (MatchBcDto matchBcDto : eventBcDto.getMatch()) {
            String matchCode = matchBcDto.getName().replaceAll(" - ", "**");
            Match match = context.findOrCreateMatch(sport, matchCode,
                    matchBcDto.getStartDate().toGregorianCalendar().getTime());
            for (BetBcDto betsBcDto : matchBcDto.getBets().getBet()) {
                nb += convert(context, sport, match, betsBcDto);
            }
        }
        return nb;
    }

    private long convert(SynchronizerContext context, Sport sport, Match match,
            BetBcDto betBcDto) {
        String betName = betBcDto.getName();
        BetType betType = context.findBetType(betName);
        if (betType == null) {
            return 0L;
        }
        long nb = 0L;
        for (Choice choice : betBcDto.getChoice()) {
            nb += convert(context, sport, match, betType, choice);
        }
        return nb;
    }

    private long convert(SynchronizerContext context, Sport sport, Match match,
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
        // TODO Normalize bet code !!!!!
        if ("Match Result".equals(betType.getCode())) {
            if (choice.getName().contains("1")) {
                bet.setCode("1");
            } else if (choice.getName().contains("Draw")) {
                bet.setCode("X");
            } else if (choice.getName().contains("2")) {
                bet.setCode("2");
            } else {
                return 0L;
            }
        } else {
            bet.setCode(choice.getName());
        }
        bet.setBookmakerBetId(String.valueOf(choice.getId()));
        bet.setDate(context.getSynchronizationDate());
        bet.setBookMaker(context.getBookMaker());
        betRepository.save(bet);
        return 1L;
    }
}
