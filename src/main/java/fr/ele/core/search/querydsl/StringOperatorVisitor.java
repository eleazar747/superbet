package fr.ele.core.search.querydsl;

import com.mysema.query.types.ExpressionBase;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.StringPath;

import fr.ele.core.search.criteria.string.StringOperator.Visitor;

public class StringOperatorVisitor implements Visitor {
    private final StringPath path;

    private final ExpressionBase<String> value;

    private BooleanExpression expression;

    public StringOperatorVisitor(StringPath path, ExpressionBase<String> value) {
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
    public void visitLIKE() {
        expression = value == null ? null : path.like(value);
    }

    @Override
    public void visitSTART_WITH() {
        expression = value == null ? null : path.startsWith(value);
    }

    @Override
    public void visitCONTAINS() {
        expression = value == null ? null : path.contains(value);
    }

    @Override
    public void visitNULL() {
        expression = path.isNull();
    }
}
