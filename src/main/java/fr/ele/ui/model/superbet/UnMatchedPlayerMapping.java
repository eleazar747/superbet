package fr.ele.ui.model.superbet;

import fr.ele.ui.model.MetaProperty;

public enum UnMatchedPlayerMapping implements MetaProperty {
    ID("id", "Id"),

    BOOKMAKER("bookMaker.code", "BookMaker"),

    BOOKMAKER_CODE("code", "BookMaker Code");

    private final String property;

    private final String propertyName;

    public String getProperty() {
        return property;
    }

    public String getPropertyName() {
        return propertyName;
    }

    private UnMatchedPlayerMapping(String property, String propertyName) {
        this.property = property;
        this.propertyName = propertyName;
    }
}
