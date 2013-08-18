package fr.ele.services.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ele.core.TimeTracker;
import fr.ele.feeds.expekt.dto.Alternative;
import fr.ele.feeds.expekt.dto.Category;
import fr.ele.feeds.expekt.dto.Description;
import fr.ele.feeds.expekt.dto.Game;
import fr.ele.feeds.expekt.dto.PunterOdds;
import fr.ele.model.Bet;
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
public class ExpektSynchronizer {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ExpektSynchronizer.class);

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
        SynchronizerContext context = new SynchronizerContext("expekt",
                dataMappingRepository, sportRepository, betTypeRepository,
                bookMakerRepository, matchRepository);
        context.setSynchronizationDate(new Date());
        LOGGER.debug("start expekt sync at {}",
                context.getSynchronizationDate());
        TimeTracker tt = new TimeTracker();
        for (Game game : punterodds.getGame()) {
            convert(context, game);
        }
        LOGGER.debug("finish expekt sync at {} in {}ms",
                context.getSynchronizationDate(), tt.getDuration());

    }

    private void convert(SynchronizerContext context, Game game) {
        Description description = game.getDescription();
        Category category = (Category) description.getContent().get(1);
        String[] vDescription = parseExpektDescription(description);
        String sporExpektCode = category.getId().toString().substring(0, 3);
        Sport sport = context.findSport(sporExpektCode);
        if (sport == null) {
            return;
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
            return;
        }
        RefKey refKey = refKeyRepository.findOne(RefKeyRepository.Queries
                .findRefKey(betType, match));

        if (refKey == null) {
            refKey = new RefKey();
            refKey.setBetType(betType);
            refKey.setMatch(match);
            refKeyRepository.save(refKey);
        }

        for (Alternative alternative : game.getAlternatives().getAlternative()) {
            convert(context, alternative, refKey, match);
        }
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
}
