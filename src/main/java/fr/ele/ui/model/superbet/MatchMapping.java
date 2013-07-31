package fr.ele.ui.model.superbet;

import fr.ele.ui.model.MetaProperty;

public enum MatchMapping implements MetaProperty {
    ID("id", "Id"), CODE("code", "Code"), SPORT("sport.code", "Sport");

    private final String property;

    private final String propertyName;

    public String getProperty() {
        return property;
    }

    public String getPropertyName() {
        return propertyName;
    }

    private MatchMapping(String property, String propertyName) {
        this.property = property;
        this.propertyName = propertyName;
    }
}
