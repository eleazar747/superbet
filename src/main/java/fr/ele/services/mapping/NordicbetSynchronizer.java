package fr.ele.services.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import fr.ele.feeds.nordicbet.dto.Game;
import fr.ele.feeds.nordicbet.dto.Odds;
import fr.ele.feeds.nordicbet.dto.OutcomeSet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Sport;


@Service("NordicbetSynchronizer")
public class NordicbetSynchronizer extends AbstractSynchronizer<Odds> {



    @Override
    protected long convert(SynchronizerContext context, Odds dto) {
        for (Game game : dto.getGame()) {
            convert(context, game);
        }
        return 0;
    }

    private long convert(SynchronizerContext context, Game game) {

        Sport sport = context.findSport(game.getSport());
        if (sport == null) {
            return 0;
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
        context.findOrCreateMatch(sport, matchCode, date);
        long nb = 0L;
        for (OutcomeSet outcomeset : game.getOutcomeSet()) {
            nb += convert(context, outcomeset, game);
        }
        return nb;

    }

    private long convert(SynchronizerContext context, OutcomeSet outcomeset,
            Game game) {
        String sBet = outcomeset.getName().toString()
                .replaceAll(game.getName().toString(), "");

        BetType betType = context.findBetType(sBet);
        if (betType != null) {

        }
        return 0L;
    }

    @Override
    protected Class<Odds> getDtoClass() {
        return Odds.class;
    }

}
