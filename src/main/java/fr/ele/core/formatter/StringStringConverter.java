package fr.ele.core.formatter;

public class StringStringConverter implements StringConverter<String> {

    @Override
    public <Q extends String> String getFormatHelp(Class<Q> type) {
        return "string";
    }

    @Override
    public <Q extends String> String marshall(Q object) {
        return object;
    }

    @Override
    public <Q extends String> String unmarshall(Class<Q> type, String string) {
        return string;
    }

    @Override
    public void setOverridenFormat(String format) {
    }

    @Override
    public String getOverridenFormat() {
        return null;
    }

    @Override
    public Class<String> getHandledKey() {
        return String.class;
    }

}
