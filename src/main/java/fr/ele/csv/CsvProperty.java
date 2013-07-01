package fr.ele.csv;

public interface CsvProperty<T> {

    String getStringValue(CsvContext<T> context, T object);

    String getHeader();

    void setValue();
}
