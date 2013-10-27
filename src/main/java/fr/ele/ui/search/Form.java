package fr.ele.ui.search;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Form {
    private FormNode schema;

    private Object form;

    public FormNode getSchema() {
        return schema;
    }

    public void setSchema(FormNode schema) {
        this.schema = schema;
    }

    public Object getForm() {
        return form;
    }

    public void setForm(Object form) {
        this.form = form;
    }

}
