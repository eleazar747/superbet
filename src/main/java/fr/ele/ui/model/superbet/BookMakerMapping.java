package fr.ele.ui.model.superbet;

import fr.ele.ui.model.MetaProperty;

public enum BookMakerMapping implements MetaProperty {

    ID("id", "Id"),

    CODE("code", "Code"),

    URL("url", "Main URL"),

    URL_SYNC("urlSync", "URL for sync"),

    SYNC_SERVICE("synchronizerService", "Sync Service");

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
