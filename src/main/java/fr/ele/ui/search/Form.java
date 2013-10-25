package fr.ele.ui.search;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Form {
    private FormNode schema;

    private FormNode form;

    public FormNode getSchema() {
        return schema;
    }

    public void setSchema(FormNode schema) {
        this.schema = schema;
    }

    public FormNode getForm() {
        return form;
    }

    public void setForm(FormNode form) {
        this.form = form;
    }

}
