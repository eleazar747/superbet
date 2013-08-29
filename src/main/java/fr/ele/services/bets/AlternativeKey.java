package fr.ele.services.bets;

import fr.ele.mapreduce.Key;
import fr.ele.model.Bet;

public class AlternativeKey extends Key {
    private final String alternativeCode;

    public AlternativeKey(Bet bet) {
        alternativeCode = bet.getCode();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (alternativeCode == null ? 0 : alternativeCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AlternativeKey other = (AlternativeKey) obj;
        if (alternativeCode == null) {
            if (other.alternativeCode != null) {
                return false;
            }
        } else if (!alternativeCode.equals(other.alternativeCode)) {
            return false;
        }
        return true;
    }

}
