package fr.ele.ui.model.superbet;

import fr.ele.ui.model.MetaProperty;

public enum BookMakerMapping implements MetaProperty {

    ID("id", "Id"), CODE("code", "Code");

    private final String property;

    private final String propertyName;

    public String getProperty() {
        return property;
    }

    public String getPropertyName() {
        return propertyName;
    }

    private BookMakerMapping(String property, String propertyName) {
        this.property = property;
        this.propertyName = propertyName;
    }

}
