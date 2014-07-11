package fr.ele.services.bets.impl;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ele.mapreduce.KeyMap;
import fr.ele.mapreduce.superbet.LastBetFactory;
import fr.ele.mapreduce.superbet.SureBetHolderFactory;
import fr.ele.model.Bet;
import fr.ele.services.bets.AggregateByRefKey;
import fr.ele.services.bets.BetQueryService;
import fr.ele.services.bets.BetSearch;
import fr.ele.services.bets.BetService;
import fr.ele.services.bets.LastBetKey;
import fr.ele.services.bets.SureBet;

@Service
public class BetServiceImpl implements BetService {

    @Autowired
    private BetQueryService betQueryService;

    @Override
    public Iterator<Bet> findLastValues(BetSearch search) {
        final KeyMap<LastBetKey, Bet, Bet> keyMap = new KeyMap<>(
                new LastBetFactory());
        betQueryService.iterate(search).forEachRemaining(bet -> keyMap.add(new LastBetKey(bet), bet));
        return keyMap.getHolders().stream().map(holder -> holder.getResult()).iterator();
    }

    @Override
    public Iterator<SureBet> findSureBets(BetSearch search) {
        final KeyMap<AggregateByRefKey, SureBet, Bet> keyMap = new KeyMap<>(
                new SureBetHolderFactory());
        findLastValues(search).forEachRemaining(bet -> keyMap.add(new AggregateByRefKey(bet), bet));
        return keyMap.getHolders().stream().map(holder -> holder.getResult()).filter(surebet -> surebet.isSureBet()).iterator();
    }

}
