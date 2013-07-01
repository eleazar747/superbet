package fr.ele.core.formatter;

import fr.ele.core.registry.RegistrableEntity;

public interface StringConverter<T> extends RegistrableEntity<Class<T>> {

    <Q extends T> String getFormatHelp(Class<Q> type);

    /**
     * Transform an object to its String representation
     */
    <Q extends T> String marshall(Q object);

    /**
     * Transform back an object transformed as String to its original object
     * representation
     * 
     * @param type
     *            TODO
     */
    <Q extends T> T unmarshall(Class<Q> type, String string);

    void setOverridenFormat(String format);

    String getOverridenFormat();

    Class<T> getHandledKey();
}
