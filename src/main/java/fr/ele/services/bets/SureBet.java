package fr.ele.services.bets;

import java.util.Collection;

import fr.ele.model.Bet;

public class SureBet {
    private final Collection<Bet> bets;

    private Double value;

    public SureBet(Collection<Bet> bets) {
        this.bets = bets;
    }

    public Collection<Bet> getBets() {
        return bets;
    }

    public double getValue() {
        if (value == null) {
            // TODO use BigDecimal (or not?)
            // BigDecimal tmp = BigDecimal.ZERO;
            double tmp = 0D;
            for (Bet bet : bets) {
                // tmp.add(BigDecimal.ONE.divide(BigDecimal.valueOf(bet.getOdd())));
                tmp += 1.0 / bet.getOdd();
            }
            // value = tmp.doubleValue();
            value = tmp;
        }
        return value.doubleValue();
    }

    public boolean isSureBet() {
        return getValue() < 1d;
    }
}
