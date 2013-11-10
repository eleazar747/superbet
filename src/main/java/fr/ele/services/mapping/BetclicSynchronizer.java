package fr.ele.services.mapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import fr.ele.feeds.betclick.dto.BetBcDto;
import fr.ele.feeds.betclick.dto.BetBcDto.Choice;
import fr.ele.feeds.betclick.dto.EventBcDto;
import fr.ele.feeds.betclick.dto.MatchBcDto;
import fr.ele.feeds.betclick.dto.SportBcDto;
import fr.ele.feeds.betclick.dto.SportsBcDto;
import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;

@Service("BetclicSynchronizer")
public class BetclicSynchronizer extends AbstractSynchronizer<SportsBcDto> {

    private BufferedWriter w;

    @Override
    protected long convert(SynchronizerContext context, SportsBcDto dto) {
        long nb = 0L;
        try {
            new File("/fr/ele/feeds/bwin/teamNameBetclick.txt");
            w = new BufferedWriter(new FileWriter("teamNameBetclick.txt"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        for (SportBcDto sportBcDto : dto.getSport()) {
            nb += convert(context, sportBcDto);
        }
        try {
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nb;
    }

    @Override
    protected Class<SportsBcDto> getDtoClass() {
        return SportsBcDto.class;
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
            playerprint(matchBcDto.getName());
            if (matchBcDto.getName().toString().contains(" - ")) {
                String team[] = matchBcDto.getName().split(" - ");
                String player1 = null;
                String player2 = null;
                player1 = context.findTeam(sport, team[0]);
                player2 = context.findTeam(sport, team[1]);

                if (player1 != null && player2 != null) {
                    Match match = context.findOrCreateMatch(sport, matchBcDto
                            .getStartDate().toGregorianCalendar().getTime(),
                            player1, player2);
                    for (BetBcDto betsBcDto : matchBcDto.getBets().getBet()) {
                        nb += convert(context, sport, match, betsBcDto,
                                player1, player2);
                    }
                }
            }
        }
        return nb;
    }

    private long convert(SynchronizerContext context, Sport sport, Match match,
            BetBcDto betBcDto, String... players) {
        String betName = betBcDto.getName();
        BetType betType = context.findBetType(betName);
        if (betType == null) {
            return 0L;
        }
        long nb = 0L;
        for (Choice choice : betBcDto.getChoice()) {

            // add number to Over/Under
            if (betBcDto.getName().equals("Over/Under")) {
                String underBet = betBcDto.getName()
                        + " "
                        + choice.getName().substring(
                                choice.getName().length() - 3,
                                choice.getName().length());

                betType = context.findBetType(underBet);
            }
            nb += convert(context, sport, match, betType, choice, players);

        }
        return nb;
    }

    private long convert(SynchronizerContext context, Sport sport, Match match,
            BetType betType, Choice choice, String... players) {
        RefKey refKey = context.findOrCreateRefKey(match, betType);

        Bet bet = new Bet();
        bet.setOdd(choice.getOdd().doubleValue());
        bet.setRefKey(refKey);
        // TODO Normalize bet code !!!!!
        if ("Match Result".equals(betType.getCode())) {
            if (choice.getName().contains("1")) {
                bet.setCode(players[0]);
            } else if (choice.getName().contains("Draw")) {
                bet.setCode("Draw");
            } else if (choice.getName().contains("2")) {
                bet.setCode(players[1]);
            } else {
                return 0L;
            }
        } else {
            bet.setCode(choice.getName());
        }
        bet.setBookmakerBetId(String.valueOf(choice.getId()));
        bet.setDate(context.getSynchronizationDate());
        bet.setBookMaker(context.getBookMaker());
        saveBet(bet);
        return 1L;
    }

    private void playerprint(String match) {
        HashMap<String, String> hMap = new HashMap<String, String>();
        String[] team = match.split(" - ");
        for (String str : team) {
            if (hMap.containsKey(str) == false) {
                try {
                    w.write(str);
                    w.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
