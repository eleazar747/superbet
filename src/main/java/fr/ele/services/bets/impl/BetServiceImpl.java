package fr.ele.services.bets.impl;

import java.util.Iterator;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

import fr.ele.mapreduce.Closure;
import fr.ele.mapreduce.Closures;
import fr.ele.mapreduce.Holder;
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
        Iterator<Bet> it = betQueryService.iterate(search);
        final KeyMap<LastBetKey, Bet, Bet> keyMap = new KeyMap<LastBetKey, Bet, Bet>(
                new LastBetFactory());
        Closures.forAll(it, new Closure<Bet>() {
            @Override
            public void execute(Bet input) {
                LastBetKey key = new LastBetKey(input);
                keyMap.add(key, input);
            }
        });
        return Iterators.transform(keyMap.iterateHolder(),
                new Function<Holder<Bet, Bet>, Bet>() {

                    @Override
                    @Nullable
                    public Bet apply(@Nullable Holder<Bet, Bet> input) {
                        return input.getResult();
                    }
                });
    }

    @Override
    public Iterator<SureBet> findSureBets(BetSearch search) {
        Iterator<Bet> iterator = findLastValues(search);
        final KeyMap<AggregateByRefKey, SureBet, Bet> keyMap = new KeyMap<AggregateByRefKey, SureBet, Bet>(
                new SureBetHolderFactory());
        Closures.forAll(iterator, new Closure<Bet>() {
            @Override
            public void execute(Bet input) {
                keyMap.add(new AggregateByRefKey(input), input);
            }
        });
        Iterator<SureBet> surebets = Iterators.transform(
                keyMap.iterateHolder(),
                new Function<Holder<SureBet, Bet>, SureBet>() {

                    @Override
                    @Nullable
                    public SureBet apply(@Nullable Holder<SureBet, Bet> input) {
                        return input.getResult();
                    }
                });
        return Iterators.filter(surebets, new Predicate<SureBet>() {

            @Override
            public boolean apply(@Nullable SureBet input) {
                return input.isSureBet();
            }
        });
    }

}
