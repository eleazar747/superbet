package fr.ele.core.formatter;

public class DefaultStringConverterRegistry extends StringConverterRegistry {

    public DefaultStringConverterRegistry() {
        register(new BooleanStringConverter());
        register(new NumberStringConverter());
        register(new DateStringConverter());
        register(new EnumStringConverter<>());
        register(new StringStringConverter());
    }
}
