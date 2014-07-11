package fr.ele.core.formatter;

import org.apache.commons.lang3.StringUtils;

public class EnumStringConverter<E extends Enum<E>> implements StringConverter<E> {

    @Override
    public <Q extends E> String getFormatHelp(Class<Q> type) {
        return StringUtils.join(type.getEnumConstants(), ',');
    }

    @Override
    public <Q extends E> String marshall(Q object) {
        return object.name();
    }

    @Override
    public <Q extends E> E unmarshall(Class<Q> type, String string) {
        for (E value : type.getEnumConstants()) {
            if (value.name().equals(string)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public void setOverridenFormat(String format) {
    }

    @Override
    public String getOverridenFormat() {
        return null;
    }

    @Override
    public Class<E> getHandledKey() {
        return (Class<E>) Enum.class;
    }

}
