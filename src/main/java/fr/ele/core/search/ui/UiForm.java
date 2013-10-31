package fr.ele.core.search.ui;

import java.util.List;

public class UiForm {
    private List<UiCriteria> fields;

    private List<UiForm> subForms;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UiCriteria> getFields() {
        return fields;
    }

    public void setFields(List<UiCriteria> fields) {
        this.fields = fields;
    }

    public List<UiForm> getSubForms() {
        return subForms;
    }

    public void setSubForms(List<UiForm> subForms) {
        this.subForms = subForms;
    }
}
