package fr.ele.csv;

import java.io.IOException;
import java.io.OutputStream;
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

    public void marshall(List<T> objects, OutputStream outputStream) throws IOException {
        CSVPrinter printer = new CSVPrinter(outputStream);
        if (context.isWithHeader()) {
            for (CsvProperty property : csvBeanProperties.getProperties()) {
                printer.write(property.getHeader());
            }
        }
        for (T object : objects) {
            if (!csvBeanProperties.getHandledClass().isAssignableFrom(
                    object.getClass())) {
                throw new RuntimeException(object.toString() + "not handled");
            }
            for (CsvProperty property : csvBeanProperties.getProperties()) {
                printer.write(property.getStringValue(context, object));
            }
            printer.println();
        }
    }
}
