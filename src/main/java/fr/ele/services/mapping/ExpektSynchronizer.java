package fr.ele.services.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ele.feeds.expekt.dto.Alternative;
import fr.ele.feeds.expekt.dto.Category;
import fr.ele.feeds.expekt.dto.Description;
import fr.ele.feeds.expekt.dto.Game;
import fr.ele.feeds.expekt.dto.PunterOdds;
import fr.ele.model.Bet;
import fr.ele.model.BookMakers;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.RefKeyRepository;

@Service("ExpektSynchronizer")
public class ExpektSynchronizer extends AbstractSynchronizer<PunterOdds> {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private RefKeyRepository refKeyRepository;

    @Override
    protected long convert(SynchronizerContext context, PunterOdds punterodds) {
        long nb = 0L;
        for (Game game : punterodds.getGame()) {
            nb += convert(context, game);
        }
        return nb;
    }

    private long convert(SynchronizerContext context, Game game) {
        Description description = game.getDescription();
        Category category = (Category) description.getContent().get(1);
        String[] vDescription = parseExpektDescription(description);
        String sporExpektCode = category.getId().toString().substring(0, 3);
        Sport sport = context.findSport(sporExpektCode);
        if (sport == null) {
            return 0L;
        }

        String matchCode = vDescription[0];
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {
            date = formatter.parse(game.getDate().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Match match = context.findOrCreateMatch(sport, matchCode, date);
        BetType betType = context.findBetType(vDescription[1]);
        if (betType == null) {
            return 0L;
        }
        RefKey refKey = refKeyRepository.findOne(RefKeyRepository.Queries
                .findRefKey(betType, match));

        if (refKey == null) {
            refKey = new RefKey();
            refKey.setBetType(betType);
            refKey.setMatch(match);
            refKeyRepository.save(refKey);
        }
        long nb = 0L;
        for (Alternative alternative : game.getAlternatives().getAlternative()) {
            convert(context, alternative, refKey, match);
            ++nb;
        }
        return nb;
    }

    private void convert(SynchronizerContext context, Alternative alternative,
            RefKey refKey, Match match) {

        Bet bet = new Bet();
        bet.setOdd(alternative.getOdds());
        bet.setRefKey(refKey);
        bet.setCode(alternative.getValue());
        bet.setDate(context.getSynchronizationDate());
        bet.setBookMaker(context.getBookMaker());
        betRepository.save(bet);
    }

    private String[] parseExpektDescription(Description description) {
        String[] strtmp = null;
        String matchDescription = description.getContent().get(2).toString();
        if (matchDescription.contains(":")) {
            strtmp = matchDescription.split(":");
            strtmp[0] = strtmp[0].replaceAll(" ", "").replaceAll("-", "**")
                    .toLowerCase();
            strtmp[1] = strtmp[1].replaceAll(" ", "").toLowerCase();

        } else {

            strtmp = new String[2];
            strtmp[0] = matchDescription.toLowerCase().replaceAll(" ", "")
                    .replaceAll("-", "**");
            strtmp[1] = "Match Result";
        }
        return strtmp;
    }

    @Override
    protected BookMakers getBookMaker() {
        return BookMakers.EXPEKT;
    }

}
