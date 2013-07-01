package fr.ele.csv;

import fr.ele.core.formatter.DefaultStringConverterRegistry;
import fr.ele.core.formatter.StringConverter;
import fr.ele.core.formatter.StringConverterRegistry;
import fr.ele.csv.mapping.CsvRegistry;

public class CsvContext<T> {

    private final CsvBeanProperties csvBeanProperties;

    private StringConverterRegistry registry = new DefaultStringConverterRegistry();

    private boolean withHeader = true;

    private CsvContext(CsvBeanProperties csvBeanProperties) {
        this.csvBeanProperties = csvBeanProperties;
    }

    public static <T> CsvContext<T> create(Class<T> clazz) {
        return new CsvContext(CsvRegistry.findCsvDefinition(clazz));
    }

    public CsvMarshaller<T> newMarshaller() {
        return new CsvMarshaller<T>(this, csvBeanProperties);
    }

    public boolean isWithHeader() {
        return withHeader;
    }

    public void setWithHeader(boolean withHeader) {
        this.withHeader = withHeader;
    }

    public String marshall(Object object) {
        StringConverter converter = registry.lookup(object.getClass());
        return converter.marshall(object);
    }

    public StringConverterRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(StringConverterRegistry registry) {
        this.registry = registry;
    }

}
