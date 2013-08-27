package fr.ele.ui.model.superbet;

import fr.ele.ui.model.MetaProperty;

public enum DataMappingMapping implements MetaProperty {
    ID("id", "Id"),

    BOOKMAKER("bookMaker.code", "BookMaker"),

    REF_TYPE("refEntityType", "Ref Type"),

    BOOKMAKER_CODE("bookMakerCode", "BookMaker Code"),

    MODEL_CODE("modelCode", "Model Code");

    private final String property;

    private final String propertyName;

    public String getProperty() {
        return property;
    }

    public String getPropertyName() {
        return propertyName;
    }

    private DataMappingMapping(String property, String propertyName) {
        this.property = property;
        this.propertyName = propertyName;
    }
}
