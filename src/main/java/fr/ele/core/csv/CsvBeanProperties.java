package fr.ele.core.csv;

public interface CsvBeanProperties<T> {
    Class<T> getHandledClass();

    CsvProperty<T>[] getProperties();

    T getNewInstance();
}
