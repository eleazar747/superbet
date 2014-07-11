package fr.ele.core.csv;

public interface CsvProperty<T> {

    String getStringValue(CsvContext<T> context, T object);

    String getHeader();

    void setValue(CsvContext<T> context, T object, String value);
}
