package fr.ele.core.search.criteria.number;

import fr.ele.core.search.CriteriaVisitor;
import fr.ele.core.search.criteria.ValueCriteria;

public class NumberValueCriteria<N extends Number> extends ValueCriteria<N> {

    private NumberOperator operator;

    public NumberOperator getOperator() {
        return operator;
    }

    public void setOperator(NumberOperator operator) {
        this.operator = operator;
    }

    @Override
    public void accept(CriteriaVisitor visitor) {
        visitor.visit(this);
    }

}
