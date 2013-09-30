package fr.ele.services.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import fr.ele.feeds.nordicbet.dto.Game;
import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.feeds.nordicbet.dto.Outcome;
import fr.ele.feeds.nordicbet.dto.OutcomeSet;
import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;


@Service("NordicbetSynchronizer")
public class NordicbetSynchronizer extends AbstractSynchronizer<Odds> {



    @Override
    protected long convert(SynchronizerContext context, Odds dto) {
        long nb=0L;
    	for (Game game : dto.getGame()) {
            nb+=convert(context, game);
        }
        return nb;
    }

    private long convert(SynchronizerContext context, Game game) {
    	long nb = 0L;
        Sport sport = context.findSport(game.getSport());
        if (sport == null) {
            return 0L;
        }

        String matchCode = game.getName().toString().toLowerCase()
                .replaceAll(" ", "").replaceAll("-", "**");
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        try {
            date = formatter.parse(game.getGameStartTime().replaceAll("CEST",
                    ""));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Match match=context.findOrCreateMatch(sport, matchCode, date);
        
        for (OutcomeSet outcomeset : game.getOutcomeSet()) {
            nb += convert(context, outcomeset, game,match);
        }
        return nb;

    }

    private long convert(SynchronizerContext context, OutcomeSet outcomeset,
            Game game,Match match) {
    	String sBet=null;
    	if(outcomeset.getName().replaceAll(game.getName(), "")==null){
    		sBet=outcomeset.getType();
    	}
    	else
    	{
    		sBet=outcomeset.getName().replaceAll(game.getName(), "").replaceAll(" " , "");
    	}
       
        long nb=0L;
        BetType betType = context.findBetType(sBet);
        if (betType != null) {
        	RefKey refKey = context.findOrCreateRefKey(match, betType);
        	 for (Outcome outcome : outcomeset.getOutcome()) {
                 nb += convert(context,outcomeset, outcome, game,match,refKey);
             }
        	 
       
        }
        return nb;
    }

    private long convert(SynchronizerContext context, OutcomeSet outcomeset, Outcome outcome,
            Game game,Match match,RefKey refKey ) {
        
       

        Bet bet = new Bet();
		bet.setOdd(outcome.getOdds());
		bet.setRefKey(refKey);
		bet.setCode(outcome.getName());
		bet.setDate(context.getSynchronizationDate());
		bet.setBookmakerBetId(String.valueOf(outcomeset.getId()));
		bet.setBookMaker(context.getBookMaker());
		saveBet(bet);
		return 1L;
        
    }

    
    @Override
    protected Class<Odds> getDtoClass() {
        return Odds.class;
    }

}
