package fr.ele.services.bets;

import fr.ele.mapreduce.Key;
import fr.ele.model.Bet;

public class LastBetKey extends Key {
    private final long refKeyId, bookMakerId;

    private final String code;

    public LastBetKey(Bet bet) {
        refKeyId = bet.getRefKey().getId();
        bookMakerId = bet.getBookMaker().getId();
        code = bet.getCode();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (bookMakerId ^ bookMakerId >>> 32);
        result = prime * result + (code == null ? 0 : code.hashCode());
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
        LastBetKey other = (LastBetKey) obj;
        if (bookMakerId != other.bookMakerId) {
            return false;
        }
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        if (refKeyId != other.refKeyId) {
            return false;
        }
        return true;
    }

}
