package fr.ele.mapreduce.superbet;

import fr.ele.mapreduce.Holder;
import fr.ele.mapreduce.HolderFactory;
import fr.ele.model.Bet;

public class LastBetFactory implements HolderFactory<Bet, Bet> {

    @Override
    public Holder<Bet, Bet> createHolder() {
        return new LastBet();
    }

}
