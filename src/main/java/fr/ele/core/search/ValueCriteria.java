package fr.ele.core.search;


public abstract class ValueCriteria<T> extends SearchCriteria {

    private T criteriaValue;

    public T getCriteriaValue() {
        return criteriaValue;
    }

    public void setCriteriaValue(T value) {
        this.criteriaValue = value;
    }

}
