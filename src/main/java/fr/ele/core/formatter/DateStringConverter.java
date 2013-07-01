package fr.ele.core.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateStringConverter implements StringConverter<Date> {

    private String format;

    public void setOverridenFormat(String format) {
        this.format = format;
    }

    public String getOverridenFormat() {
        return format;
    }

    @Override
    public <Q extends Date> String getFormatHelp(Class<Q> type) {
        return "yyyymmdd";
    }

    @Override
    public <Q extends Date> String marshall(Q object) {
        return DateFormat.getDateInstance().format(object);
    }

    @Override
    public <Q extends Date> Date unmarshall(Class<Q> type, String string) {
        try {
            return DateFormat.getDateInstance().parse(string);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public Class<Date> getHandledKey() {
        return Date.class;
    }

}
