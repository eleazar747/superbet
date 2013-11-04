package fr.ele.core.csv;

import fr.ele.core.formatter.DefaultStringConverterRegistry;
import fr.ele.core.formatter.StringConverter;
import fr.ele.core.formatter.StringConverterRegistry;
import fr.ele.csv.CsvRegistry;

public class CsvContext<T> {

    public static final char DEFAULT_SEPARATOR = ',';

    public static final char DEFAULT_QUOTE = '\'';

    public static final char DEFAULT_COMMENT = '#';

    private final CsvBeanProperties csvBeanProperties;

    private final GraphResolver graphResolver;

    private StringConverterRegistry registry = new DefaultStringConverterRegistry();

    private boolean withHeader = true;

    private char separator = DEFAULT_SEPARATOR;

    private char quote = DEFAULT_QUOTE;

    private char comment = DEFAULT_COMMENT;

    private CsvContext(CsvBeanProperties csvBeanProperties,
            GraphResolver graphResolver) {
        this.csvBeanProperties = csvBeanProperties;
        this.graphResolver = graphResolver;
    }

    public static <T> CsvContext<T> create(Class<T> clazz,
            GraphResolver graphResolver) {
        return new CsvContext(CsvRegistry.findCsvDefinition(clazz),
                graphResolver);
    }

    public CsvMarshaller<T> newMarshaller() {
        return new CsvMarshaller<T>(this, csvBeanProperties);
    }

    public CsvUnmarshaller<T> newUnmarshaller() {
        return new CsvUnmarshaller<T>(this, csvBeanProperties);
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

    public <Q> Q unmarshall(Class<Q> clazz, String value) {
        StringConverter<Q> converter = registry.lookup(clazz);
        return converter.unmarshall(clazz, value);
    }

    public StringConverterRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(StringConverterRegistry registry) {
        this.registry = registry;
    }

    public char getSeparator() {
        return separator;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public char getQuote() {
        return quote;
    }

    public void setQuote(char quote) {
        this.quote = quote;
    }

    public char getComment() {
        return comment;
    }

    public void setComment(char comment) {
        this.comment = comment;
    }

    public GraphResolver getGraphResolver() {
        return graphResolver;
    }
}
