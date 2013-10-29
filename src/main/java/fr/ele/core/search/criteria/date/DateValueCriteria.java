package fr.ele.core.search.criteria.date;

import java.util.Date;

import fr.ele.core.search.CriteriaVisitor;
import fr.ele.core.search.criteria.ValueCriteria;

public class DateValueCriteria extends ValueCriteria<Date> {

    private DateOperator operator;

    public DateOperator getOperator() {
        return operator;
    }

    public void setOperator(DateOperator operator) {
        this.operator = operator;
    }

    @Override
    public void accept(CriteriaVisitor visitor) {
        visitor.visit(this);
    }

}
