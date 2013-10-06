package fr.ele.core.search.querydsl;

import com.mysema.query.types.ExpressionBase;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;

import fr.ele.core.search.NumberOperator.Visitor;

public class NumberOperatorVisitor<N extends Number & Comparable<?>> implements
        Visitor {

    private final NumberPath<N> path;

    private BooleanExpression expression;

    private final ExpressionBase<N> value;

    public NumberOperatorVisitor(NumberPath<N> path, ExpressionBase<N> value) {
        super();
        this.value = value;
        this.path = path;
    }

    @Override
    public void visitEQ() {
        expression = path.eq(value);
    }

    @Override
    public void visitGT() {
        expression = path.gt(value);

    }

    @Override
    public void visitLT() {
        expression = path.lt(value);

    }

    @Override
    public void visitEGT() {
        expression = path.goe(value);

    }

    @Override
    public void visitELT() {
        expression = path.loe(value);

    }

    @Override
    public void visitNULL() {
        expression = path.isNull();
    }

    public BooleanExpression result() {
        return expression;
    }
}
