package fr.ele.ui.model.superbet;

import fr.ele.ui.model.MetaProperty;

public enum BookMakerSynchronizationMapping implements MetaProperty {

    ID("id", "Id"),

    BOOKMAKER_CODE("bookMaker.code", "BookMaker Code"),

    SYNC_DATE("synchronizationDate", "Sync Date"),

    NB_BETS("nbBets", "Nb bets sync"),

    DURATION("duration", "Duration");

    private final String property;

    private final String propertyName;

    public String getProperty() {
        return property;
    }

    public String getPropertyName() {
        return propertyName;
    }

    private BookMakerSynchronizationMapping(String property, String propertyName) {
        this.property = property;
        this.propertyName = propertyName;
    }
}
