package fr.ele.mapreduce.superbet;

import fr.ele.mapreduce.Holder;
import fr.ele.model.Bet;

public class LastBet implements Holder<Bet, Bet> {

    private Bet lastBet;

    @Override
    public Bet getResult() {
        return null;
    }

    @Override
    public void add(Bet value) {
        if (lastBet == null) {
            lastBet = value;
        }
        if (value != null && value.getDate().after(lastBet.getDate())) {
            lastBet = value;
        }

    }

}
