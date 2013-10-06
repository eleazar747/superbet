package fr.ele.core.search;

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
