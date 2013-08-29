package fr.ele.services.bets;

import java.util.Iterator;

import fr.ele.model.Bet;

public interface BetService {
    Iterator<Bet> findLastValues(BetSearch search);

    Iterator<SureBet> findSureBets(BetSearch search);
}
