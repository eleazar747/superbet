package fr.ele.core.search.querydsl;

import com.mysema.query.types.ConstantImpl;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import fr.ele.core.search.CriteriaVisitor;
import fr.ele.core.search.NumberOperator;
import fr.ele.core.search.NumberValueCriteria;
import fr.ele.core.search.StringOperator;
import fr.ele.core.search.StringValueCriteria;

public class SearchCriteriaVisitor<T> implements CriteriaVisitor {

    private final Path<T> path;

    private BooleanExpression result;

    public SearchCriteriaVisitor(Path<T> path) {
        super();
        this.path = path;
    }

    public BooleanExpression result() {
        return result;
    }

    @Override
    public void visit(NumberValueCriteria criteria) {
        Object value = criteria.getCriteriaValue();
        ConstantImpl constant = value == null ? null : new ConstantImpl(value);
        NumberOperatorVisitor visitor = new NumberOperatorVisitor(
                (NumberPath) path, constant);
        NumberOperator operator = criteria.getOperator();
        if (operator != null) {
            operator.accept(visitor);
        }
        result = visitor.result();

    }

    @Override
    public void visit(StringValueCriteria criteria) {
        Object value = criteria.getCriteriaValue();
        ConstantImpl constant = value == null ? null : new ConstantImpl(value);
        StringOperatorVisitor visitor = new StringOperatorVisitor(
                (StringPath) path, constant);
        StringOperator operator = criteria.getOperator();
        if (operator != null) {
            operator.accept(visitor);
        }
        result = visitor.result();
    }
}
