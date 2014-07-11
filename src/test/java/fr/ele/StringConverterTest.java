package fr.ele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.ele.core.formatter.DefaultStringConverterRegistry;
import fr.ele.core.formatter.StringConverter;

public class StringConverterTest {

    private enum EnumForTest {
        ONE, TWO
    }

    @Test
    public void testEnum() {
        DefaultStringConverterRegistry registry = new DefaultStringConverterRegistry();
        StringConverter converter = registry.lookup(EnumForTest.class);
        assertNotNull(converter);
        for (EnumForTest value : EnumForTest.values()) {
            String string = converter.marshall(value);
            assertEquals(value, converter.unmarshall(EnumForTest.class, string));
        }
    }
}
