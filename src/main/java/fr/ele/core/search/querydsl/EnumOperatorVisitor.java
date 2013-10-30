package fr.ele.core.search.querydsl;

import com.mysema.query.types.ExpressionBase;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.EnumPath;

import fr.ele.core.search.criteria.enums.EnumOperator.Visitor;

public class EnumOperatorVisitor<E extends Enum<E>> implements Visitor {

    private final EnumPath<E> path;

    private final ExpressionBase<E> value;

    private BooleanExpression expression;

    public EnumOperatorVisitor(EnumPath<E> path, ExpressionBase<E> value) {
        super();
        this.value = value;
        this.path = path;
    }

    public BooleanExpression result() {
        return expression;
    }

    @Override
    public void visitEQ() {
        expression = value == null ? null : path.eq(value);
    }

    @Override
    public void visitNULL() {
        expression = path.isNull();
    }

}
