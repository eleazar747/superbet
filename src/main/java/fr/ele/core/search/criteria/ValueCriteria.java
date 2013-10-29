package fr.ele.core.search.criteria;

import fr.ele.core.search.SearchCriteria;



public abstract class ValueCriteria<T> extends SearchCriteria {

    private T criteriaValue;

    public T getCriteriaValue() {
        return criteriaValue;
    }

    public void setCriteriaValue(T value) {
        this.criteriaValue = value;
    }

}
