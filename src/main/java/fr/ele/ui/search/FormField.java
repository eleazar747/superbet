package fr.ele.ui.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class FormField {
    private FieldType type = FieldType.STRING;

    private String title;

    private String description;

    @JsonProperty("default")
    private String defaultValue;

    private Boolean disabled;

    @JsonProperty("read-only")
    private Boolean readOnly;

    @JsonProperty("enum")
    private List<String> values;

    private Boolean requiered;

    private Map<String, FormField> properties;

    public void addValue(String value) {
        if (values == null) {
            values = new LinkedList<String>();
        }
        values.add(value);
    }

    public void addValues(String... values) {
        if (this.values == null) {
            this.values = new ArrayList<String>(values.length);
        }
        for (String value : values) {
            addValue(value);
        }
    }

    public Map<String, FormField> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, FormField> properties) {
        this.properties = properties;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Boolean getRequiered() {
        return requiered;
    }

    public void setRequiered(Boolean requiered) {
        this.requiered = requiered;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

}
