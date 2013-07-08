package fr.ele.model;

import java.util.List;

public interface SureBet {

    List<Bet> getBets();

    void setBets(List<Bet> bets);

    double getComputedValue();

    void setComputedValue(double value);
}
