package fr.ele.csv;

public interface CsvBeanProperties<T> {
    Class<T> getHandledClass();

    CsvProperty[] getProperties();
}
