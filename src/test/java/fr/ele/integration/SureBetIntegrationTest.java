package fr.ele.integration;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.model.Bet;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.BookMaker;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.RefKey;
import fr.ele.model.ref.Sport;
import fr.ele.services.bets.BetSearch;
import fr.ele.services.bets.BetService;
import fr.ele.services.bets.SureBet;
import fr.ele.services.repositories.BetRepository;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.RefKeyRepository;
import fr.ele.services.repositories.SportRepository;

public class SureBetIntegrationTest extends AbstractSuperbetIntegrationTest {
    @Autowired
    private RefKeyRepository refKeyRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private BetTypeRepository betTypeRepository;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private BookMakerRepository bookMakerRepository;

    @Autowired
    private BetService betService;

    @Override
    @Before
    public void initializeDatas() {
        super.initializeDatas();
    }

    @Test
    public void test() {
        Sport sport = sportRepository.findByCode("Football");
        Match match = new Match();
        match.setCode("dummy");
        match.setDate(new Date());
        match.setSport(sport);
        matchRepository.save(match);
        BetType betType = betTypeRepository.findByCode("Winner");
        RefKey refKey = new RefKey();
        refKey.setBetType(betType);
        refKey.setMatch(match);
        refKeyRepository.save(refKey);
        BookMaker bookMaker = bookMakerRepository.findByCode("betclic");
        Bet bet1 = createBet(refKey, bookMaker, "1", 1.2);
        Bet bet2 = createBet(refKey, bookMaker, "2", 3.5);
        bookMaker = bookMakerRepository.findByCode("expekt");
        Bet bet3 = createBet(refKey, bookMaker, "1", 1.5);
        Bet bet4 = createBet(refKey, bookMaker, "2", 1.1);
        BetSearch search = new BetSearch();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        search.setFrom(calendar.getTime());
        calendar.add(Calendar.DATE, 2);
        search.setTo(calendar.getTime());
        Iterator<SureBet> sureBets = betService.findSureBets(search);
        Assert.assertTrue(sureBets.hasNext());
        SureBet sureBet = sureBets.next();
        Assert.assertFalse(sureBets.hasNext());
        Assert.assertTrue(sureBet.isSureBet());
        Assert.assertTrue(sureBet.getValue() < 1d);
        Assert.assertTrue(sureBet.getBets().contains(bet2));
        Assert.assertTrue(sureBet.getBets().contains(bet3));
        Assert.assertFalse(sureBet.getBets().contains(bet1));
        Assert.assertFalse(sureBet.getBets().contains(bet4));
    }

    private Bet createBet(RefKey refKey, BookMaker bookMaker, String code,
            double odd) {
        Bet bet = new Bet();
        bet.setBookMaker(bookMaker);
        bet.setRefKey(refKey);
        bet.setDate(new Date());
        bet.setCode(code);
        bet.setOdd(odd);
        bet.setBookmakerBetId("dummy");
        betRepository.save(bet);
        return bet;
    }
}
