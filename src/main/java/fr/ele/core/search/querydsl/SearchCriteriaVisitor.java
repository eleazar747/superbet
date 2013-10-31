package fr.ele.core.search.querydsl;

import com.mysema.query.types.ConstantImpl;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.EnumPath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import fr.ele.core.search.CriteriaVisitor;
import fr.ele.core.search.criteria.date.DateOperator;
import fr.ele.core.search.criteria.date.DateValueCriteria;
import fr.ele.core.search.criteria.enums.EnumOperator;
import fr.ele.core.search.criteria.enums.EnumValueCriteria;
import fr.ele.core.search.criteria.number.NumberOperator;
import fr.ele.core.search.criteria.number.NumberValueCriteria;
import fr.ele.core.search.criteria.string.StringOperator;
import fr.ele.core.search.criteria.string.StringValueCriteria;

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

    @Override
    public void visit(DateValueCriteria criteria) {
        Object value = criteria.getCriteriaValue();
        ConstantImpl constant = value == null ? null : new ConstantImpl(value);
        DateOperatorVisitor visitor = new DateOperatorVisitor(
                (DateTimePath) path, constant);
        DateOperator operator = criteria.getOperator();
        if (operator != null) {
            operator.accept(visitor);
        }
        result = visitor.result();

    }

    @Override
    public void visit(EnumValueCriteria criteria) {
        Object value = criteria.getCriteriaValue();
        ConstantImpl constant = value == null ? null : new ConstantImpl(value);
        EnumOperatorVisitor visitor = new EnumOperatorVisitor((EnumPath) path,
                constant);
        EnumOperator operator = criteria.getOperator();
        if (operator != null) {
            operator.accept(visitor);
        }
        result = visitor.result();

    }
}
