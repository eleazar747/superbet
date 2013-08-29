package fr.ele.mapreduce.superbet;

import fr.ele.mapreduce.Holder;
import fr.ele.mapreduce.HolderFactory;
import fr.ele.model.Bet;
import fr.ele.services.bets.SureBet;

public class SureBetHolderFactory implements HolderFactory<SureBet, Bet> {

    @Override
    public Holder<SureBet, Bet> createHolder() {
        return new SurebetHolder();
    }

}
