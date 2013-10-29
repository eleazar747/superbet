package fr.ele.core.search.criteria.string;

import fr.ele.core.search.CriteriaVisitor;
import fr.ele.core.search.criteria.ValueCriteria;

public class StringValueCriteria extends ValueCriteria<String> {

    private StringOperator operator;

    public StringOperator getOperator() {
        return operator;
    }

    public void setOperator(StringOperator operator) {
        this.operator = operator;
    }

    @Override
    public void accept(CriteriaVisitor visitor) {
        visitor.visit(this);
    }

}
