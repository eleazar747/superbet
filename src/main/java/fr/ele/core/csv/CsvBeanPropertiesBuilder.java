package fr.ele.core.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import fr.herman.metatype.model.MetaProperty;
import fr.herman.metatype.model.method.Getter;
import fr.herman.metatype.model.method.Setter;

public class CsvBeanPropertiesBuilder<BEAN> {


    private class SimpleProperty<BEAN, VALUE> implements CsvProperty<BEAN> {

        private final String header;

        private final Class<VALUE> valueType;

        private final Getter<? super BEAN, VALUE> getter;

        private final Setter<? super BEAN, VALUE> setter;

        public SimpleProperty(String header, Class<VALUE> valueType, Getter<? super BEAN, VALUE> getter, Setter<? super BEAN, VALUE> setter) {
            this.header = header;
            this.valueType = valueType;
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        public String getStringValue(CsvContext<BEAN> context, BEAN object) {
            if (getter != null) {
                return context.marshall(getter.getValue(object));
            }
            return null;
        }

        @Override
        public String getHeader() {
            return header;
        }

        @Override
        public void setValue(CsvContext<BEAN> context, BEAN object, String value) {
            if (setter != null) {
                setter.setValue(object, context.unmarshall(valueType, value));
            }
        }

    }

    @FunctionalInterface
    public static interface GraphSetter<BEAN, VALUE> {
        void bind(CsvContext<BEAN> context, BEAN bean, VALUE value);
    }

    private class Property<BEAN, VALUE> implements CsvProperty<BEAN> {

        private final String header;

        private final Class<VALUE> valueType;

        private final Getter<? super BEAN, VALUE> getter;

        private final GraphSetter<BEAN, VALUE> setter;

        public Property(String header, Class<VALUE> valueType, Getter<? super BEAN, VALUE> getter, GraphSetter<BEAN, VALUE> setter) {
            this.header = header;
            this.valueType = valueType;
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        public String getStringValue(CsvContext<BEAN> context, BEAN object) {
            if (getter != null) {
                return context.marshall(getter.getValue(object));
            }
            return null;
        }

        @Override
        public String getHeader() {
            return header;
        }

        @Override
        public void setValue(CsvContext<BEAN> context, BEAN object, String value) {
            if (setter != null) {
                setter.bind(context, object, context.unmarshall(valueType, value));
            }
        }

    }

    private class Properties<BEAN> implements CsvBeanProperties<BEAN> {

        private final Class<BEAN> beanType;

        private final Supplier<BEAN> constructor;

        private final CsvProperty<BEAN>[] properties;

        public Properties(Class<BEAN> beanType, Supplier<BEAN> constructor, CsvProperty<BEAN>[] properties) {
            super();
            this.beanType = beanType;
            this.constructor = constructor;
            this.properties = properties;
        }

        @Override
        public Class<BEAN> getHandledClass() {
            return beanType;
        }

        @Override
        public CsvProperty<BEAN>[] getProperties() {
            return properties;
        }

        @Override
        public BEAN getNewInstance() {
            return constructor.get();
        }

    }

    private final Class<BEAN> beanType;

    private final Supplier<BEAN> constructor;

    private final List<CsvProperty<BEAN>> properties;

    public CsvBeanPropertiesBuilder(Class<BEAN> beanType, Supplier<BEAN> constructor) {
        super();
        this.beanType = beanType;
        this.constructor = constructor;
        this.properties = new ArrayList<>();
    }

    public static <BEAN> CsvBeanPropertiesBuilder<BEAN> create(Class<BEAN> handledClass, Supplier<BEAN> contructor) {
        return new CsvBeanPropertiesBuilder<BEAN>(handledClass, contructor);
    }

    public <VALUE> CsvBeanPropertiesBuilder<BEAN> add(String header, Class<VALUE> type, Getter<? super BEAN, VALUE> getter,
            Setter<? super BEAN, VALUE> setter) {
        properties.add(new SimpleProperty<>(header, type, getter, setter));
        return this;
    }

    public <VALUE> CsvBeanPropertiesBuilder<BEAN> add(String header, Class<VALUE> type, Getter<? super BEAN, VALUE> getter,
            GraphSetter<BEAN, VALUE> setter) {
        properties.add(new Property<>(header, type, getter, setter));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <VALUE, GS extends Getter<? super BEAN, VALUE> & Setter<? super BEAN, VALUE> & MetaProperty<? super BEAN, VALUE>> CsvBeanPropertiesBuilder<BEAN> add(
            String header, GS getterSetter) {
        properties.add(new SimpleProperty<>(header, (Class<VALUE>) getterSetter.type(), getterSetter, getterSetter));
        return this;
    }

    public <VALUE, GS extends Getter<? super BEAN, VALUE> & Setter<? super BEAN, VALUE>> CsvBeanPropertiesBuilder<BEAN> add(String header,
            Class<VALUE> type, GS getterSetter) {
        properties.add(new SimpleProperty<>(header, type, getterSetter, getterSetter));
        return this;
    }

    @SuppressWarnings("unchecked")
    public CsvBeanProperties<BEAN> build() {
        return new Properties<BEAN>(beanType, constructor, properties.toArray(new CsvProperty[properties.size()]));
    }

}
