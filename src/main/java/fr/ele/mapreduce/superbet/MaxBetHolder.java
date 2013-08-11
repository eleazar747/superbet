package fr.ele.mapreduce.superbet;

import fr.ele.mapreduce.Holder;
import fr.ele.model.Bet;

public class MaxBetHolder implements Holder<Bet, Bet> {

    private Bet maxBet;

    @Override
    public Bet getResult() {
        return maxBet;
    }

    @Override
    public void add(Bet value) {
        if (maxBet == null) {
            maxBet = value;
        }
        if (value != null && value.getOdd() > maxBet.getOdd()) {
            maxBet = value;
        }

    }

}
