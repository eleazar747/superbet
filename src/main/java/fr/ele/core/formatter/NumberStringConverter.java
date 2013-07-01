package fr.ele.core.formatter;

public class NumberStringConverter implements StringConverter<Number> {

    private String format;

    public void setOverridenFormat(String format) {
        this.format = format;
    }

    public String getOverridenFormat() {
        return format;
    }

    @Override
    public <Q extends Number> String getFormatHelp(Class<Q> type) {
        return "number";
    }

    @Override
    public <Q extends Number> String marshall(Q object) {
        return ((Number) object).toString();
    }

    @Override
    public <Q extends Number> Number unmarshall(Class<Q> type, String string) {
        if (Integer.class.equals(type)) {
            return Integer.valueOf(string);
        } else if (Long.class.equals(type)) {
            return new Long(string);
        } else if (Double.class.equals(type)) {
            return new Double(string);
        } else if (Float.class.equals(type)) {
            return new Float(string);
        }

        return null;
    }

    @Override
    public Class<Number> getHandledKey() {
        return Number.class;
    }
}
