package fr.ele.services.repositories;

import java.util.ArrayList;
import java.util.List;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;

import fr.ele.core.jpa.HandledClass;
import fr.ele.model.ref.BetType;
import fr.ele.model.ref.Match;
import fr.ele.model.ref.QRefKey;
import fr.ele.model.ref.RefKey;

@HandledClass(RefKey.class)
public interface RefKeyRepository extends SuperBetRepository<RefKey> {
    public abstract static class Queries {
        public static final Predicate findRefKey(BetType betType, Match match) {
            if (betType == null && match == null) {
                return null;
            }
            QRefKey refkey = QRefKey.refKey;
            List<BooleanExpression> predicates = new ArrayList<BooleanExpression>(
                    2);
            if (betType != null) {
                predicates.add(refkey.betType.eq(betType));
            }
            if (match != null) {
                predicates.add(refkey.match.eq(match));
            }
            return BooleanExpression.allOf(predicates
                    .toArray(new BooleanExpression[predicates.size()]));
        }
    }
}
