package fr.ele.csv;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.Ostermiller.util.CSVParser;

public class CsvUnmarshaller<T> {

    private final CsvBeanProperties<T> csvBeanProperties;

    private final CsvContext<T> context;

    public CsvBeanProperties getCsvBeanProperties() {
        return csvBeanProperties;
    }

    public CsvContext<T> getContext() {
        return context;
    }

    CsvUnmarshaller(CsvContext<T> context,
            CsvBeanProperties<T> csvBeanProperties) {
        this.context = context;
        this.csvBeanProperties = csvBeanProperties;
    }

    public Iterator<T> unmarshall(InputStream inputStream) {
        final CSVParser parser = new CSVParser(inputStream,
                context.getSeparator());
        parser.setCommentStart(String.valueOf(context.getComment()));
        parser.changeQuote(context.getQuote());
        return new Iterator<T>() {
            private String[] line;

            private boolean next = true;

            @Override
            public boolean hasNext() {
                return step();
            }

            @Override
            public T next() {
                String[] current = line;
                next = true;
                step();
                T object = csvBeanProperties.getNewInstance();
                for (int i = 0; i < csvBeanProperties.getProperties().length; i++) {
                    CsvProperty<T> property = csvBeanProperties.getProperties()[i];
                    property.setValue(context, object, current[i]);
                }
                return object;
            }

            @Override
            public void remove() {
            }

            private boolean step() {
                if (next) {
                    try {
                        line = parser.getLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    next = false;
                }
                return line != null;
            }
        };
    }
}
