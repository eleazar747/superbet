package fr.ele.core.formatter;

import java.lang.reflect.Field;

import fr.ele.core.registry.GenericRegistry;

public class StringConverterRegistry extends
        GenericRegistry<StringConverter, Class> {

    @Override
    protected boolean areKeysEquals(Class key, Class klass) {

        if (key.isAssignableFrom(klass)) {
            return true;
        }

        if (klass.isPrimitive()) {
            try {
                final Field field = key.getField("TYPE");

                if (field != null) {
                    if (klass.equals(field.get(null))) {
                        return true;
                    }
                }
            } catch (final Exception e) {
            }
        }
        return false;
    }

    @Override
    protected StringConverter getDefaultObject(Class klass) {
        return null;
    }
}
