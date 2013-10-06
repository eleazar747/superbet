package fr.ele.core.search.querydsl;

import com.mysema.query.types.ExpressionBase;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.StringPath;

import fr.ele.core.search.StringOperator.Visitor;

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
        expression = path.eq(value);
    }

    @Override
    public void visitLIKE() {
        expression = path.like(value);
    }

    @Override
    public void visitSTART_WITH() {
        expression = path.startsWith(value);
    }

    @Override
    public void visitCONTAINS() {
        expression = path.contains(value);
    }

    @Override
    public void visitNULL() {
        expression = path.isNull();
    }
}
