package fr.ele.core.csv;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.Ostermiller.util.CSVPrinter;

public class CsvMarshaller<T> {

    private final CsvBeanProperties csvBeanProperties;

    private final CsvContext<T> context;

    CsvMarshaller(CsvContext<T> context, CsvBeanProperties csvBeanProperties) {
        super();
        this.context = context;
        this.csvBeanProperties = csvBeanProperties;
    }

    public void marshall(List<T> objects, OutputStream outputStream) {
        CSVPrinter printer = new CSVPrinter(
                new OutputStreamWriter(outputStream), context.getComment(),
                context.getQuote(), context.getSeparator());
        if (context.isWithHeader()) {
            for (CsvProperty property : csvBeanProperties.getProperties()) {
                printer.print(property.getHeader());
            }
            printer.println();
        }

        for (T object : objects) {
            if (!csvBeanProperties.getHandledClass().isAssignableFrom(
                    object.getClass())) {
                throw new RuntimeException(object.toString() + "not handled");
            }
            for (CsvProperty property : csvBeanProperties.getProperties()) {
                printer.print(property.getStringValue(context, object));
            }
            printer.println();
        }
    }
}
