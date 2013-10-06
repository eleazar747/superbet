package fr.ele.core.search;

public abstract class ValueCriteria<T> extends SearchCriteria {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
