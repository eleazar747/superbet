package fr.ele.services.bets;

import fr.ele.mapreduce.Key;
import fr.ele.model.Bet;

public class AggregateByRefKey extends Key {

    private final long refKeyId;

    public AggregateByRefKey(Bet bet) {
        refKeyId = bet.getRefKey().getId();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (refKeyId ^ refKeyId >>> 32);
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
        AggregateByRefKey other = (AggregateByRefKey) obj;
        if (refKeyId != other.refKeyId) {
            return false;
        }
        return true;
    }

}
