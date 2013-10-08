package fr.ele.core.search.querydsl;

import com.mysema.query.types.ConstantImpl;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import fr.ele.core.search.CriteriaVisitor;
import fr.ele.core.search.NumberValueCriteria;
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
        NumberOperatorVisitor visitor = new NumberOperatorVisitor(
                (NumberPath) path, new ConstantImpl(criteria.getValue()));
        criteria.getOperator().accept(visitor);
        result = visitor.result();

    }

    @Override
    public void visit(StringValueCriteria criteria) {
        StringOperatorVisitor visitor = new StringOperatorVisitor(
                (StringPath) path,
                new ConstantImpl<String>(criteria.getValue()));
        criteria.getOperator().accept(visitor);
        result = visitor.result();
    }
}
