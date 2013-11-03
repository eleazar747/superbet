package fr.ele.core.csv;

public interface CsvBeanProperties<T> {
    Class<T> getHandledClass();

    CsvProperty[] getProperties();

    T getNewInstance();
}
