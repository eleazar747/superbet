package fr.ele.mapreduce.superbet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.ele.mapreduce.Holder;
import fr.ele.mapreduce.KeyMap;
import fr.ele.model.Bet;
import fr.ele.services.bets.AlternativeKey;
import fr.ele.services.bets.SureBet;

public class SurebetHolder implements Holder<SureBet, Bet> {
    // TODO : Need optimization by constructor (set size and put just 1 factory)
    private final KeyMap<AlternativeKey, Bet, Bet> map = new KeyMap<AlternativeKey, Bet, Bet>(
            new MaxBetHolderFactory());

    @Override
    public SureBet getResult() {
        Collection<Holder<Bet, Bet>> holders = map.getHolders();
        List<Bet> bets = new ArrayList<Bet>(holders.size());
        for (Holder<Bet, Bet> holder : holders) {
            bets.add(holder.getResult());
        }
        return new SureBet(bets);
    }

    @Override
    public void add(Bet value) {
        map.add(new AlternativeKey(value), value);
    }

}
