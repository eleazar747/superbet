package fr.ele.csv;

public interface CsvProperty<T> {

    String getStringValue(T object);

    String getHeader();

    String getPropertyBean();

    void setValue();
}
