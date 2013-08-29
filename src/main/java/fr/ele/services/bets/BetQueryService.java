package fr.ele.services.bets;

import java.util.Iterator;

import fr.ele.model.Bet;

public interface BetQueryService {
    Iterator<Bet> iterate(BetSearch search);
}
