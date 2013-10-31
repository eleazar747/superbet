package fr.ele.core.search.querydsl;

import java.util.Date;

import com.mysema.query.types.ExpressionBase;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.DateTimePath;

import fr.ele.core.search.criteria.date.DateOperator.Visitor;

public class DateOperatorVisitor implements Visitor<Date> {

    private final DateTimePath path;

    private BooleanExpression expression;

    private final ExpressionBase value;

    public DateOperatorVisitor(DateTimePath path, ExpressionBase value) {
        super();
        this.value = value;
        this.path = path;
    }

    @Override
    public void visitEQ() {
        expression = value == null ? null : path.eq(value);
    }

    @Override
    public void visitGT() {
        expression = value == null ? null : path.gt(value);

    }

    @Override
    public void visitLT() {
        expression = value == null ? null : path.lt(value);

    }

    @Override
    public void visitEGT() {
        expression = value == null ? null : path.goe(value);

    }

    @Override
    public void visitELT() {
        expression = value == null ? null : path.loe(value);

    }

    @Override
    public void visitNULL() {
        expression = path.isNull();
    }

    public BooleanExpression result() {
        return expression;
    }

}
