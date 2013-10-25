package fr.ele.ui.search;

import org.codehaus.jackson.annotate.JsonValue;

public enum FieldType {
    STRING, NUMBER, INTEGER, BOOLEAN, ARRAY, OBJECT;

    @JsonValue
    public String toLowerCase() {
        return name().toLowerCase();
    }
}
