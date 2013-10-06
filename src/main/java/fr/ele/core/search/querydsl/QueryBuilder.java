package fr.ele.core.search.querydsl;

import java.util.ArrayList;
import java.util.List;

import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;

import fr.ele.core.search.ValueCriteria;

public class QueryBuilder {

    private final List<BooleanExpression> criterias = new ArrayList<BooleanExpression>();

    public <T> QueryBuilder and(Path<T> path, ValueCriteria<T> criteria) {
        if (criteria != null) {
            SearchCriteriaVisitor<T> visitor = new SearchCriteriaVisitor<T>(
                    path);
            criteria.accept(visitor);
            criterias.add(visitor.result());
        }
        return this;
    }

    public Predicate build() {
        return BooleanExpression.allOf(criterias
                .toArray(new BooleanExpression[criterias.size()]));
    }
}
