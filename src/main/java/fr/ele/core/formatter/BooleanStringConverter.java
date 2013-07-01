package fr.ele.core.formatter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BooleanStringConverter implements StringConverter<Boolean> {
    private static final String[] TRUE_VALUES = {"Y", "YES", "TRUE", "1"};

    private static final String[] FALSE_VALUES = {"N", "NO", "FALSE", "0"};

    private static final Set<String> TRUE_VALUES_SET = stringSet(TRUE_VALUES);

    private static final Set<String> FALSE_VALUES_SET = stringSet(FALSE_VALUES);

    private static final Set<String> ALL_VALUES_SET = new HashSet<String>();

    static {
        ALL_VALUES_SET.addAll(TRUE_VALUES_SET);
        ALL_VALUES_SET.addAll(FALSE_VALUES_SET);
    }

    private static Set<String> stringSet(String[] values) {
        return new HashSet<String>(Arrays.asList(values));
    }

    private String format;

    public void setOverridenFormat(String format) {
        this.format = format;
    }

    public String getOverridenFormat() {
        return format;
    }

    @Override
    public <Q extends Boolean> String getFormatHelp(Class<Q> type) {
        return "[Y/N]";
    }

    @Override
    public <Q extends Boolean> String marshall(Q object) {
        Boolean value = object;
        return value.booleanValue() ? "Y" : "N";
    }

    @Override
    public <Q extends Boolean> Boolean unmarshall(Class<Q> type, String string) {
        Boolean result;
        String toUpperCase = string.toUpperCase();
        if (TRUE_VALUES_SET.contains(toUpperCase)) {
            result = Boolean.TRUE;
        } else if (FALSE_VALUES_SET.contains(toUpperCase)) {
            result = Boolean.FALSE;
        } else {
            throw new RuntimeException(string + " expected to be one of "
                    + ALL_VALUES_SET);
        }
        return result;
    }

    @Override
    public Class<Boolean> getHandledKey() {
        return Boolean.class;
    }
}
