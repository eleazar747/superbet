package fr.ele.core.search.criteria.enums;

import fr.ele.core.search.CriteriaVisitor;
import fr.ele.core.search.criteria.ValueCriteria;

public class EnumValueCriteria<E extends Enum<E>> extends ValueCriteria<E> {

    private EnumOperator operator;

    public EnumOperator getOperator() {
        return operator;
    }

    public void setOperator(EnumOperator operator) {
        this.operator = operator;
    }

    @Override
    public void accept(CriteriaVisitor visitor) {
        visitor.visit(this);

    }

}
