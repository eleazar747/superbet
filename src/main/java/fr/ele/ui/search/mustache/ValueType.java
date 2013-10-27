package fr.ele.ui.search.mustache;

public enum ValueType {
    STRING, NUMBER;

    public String htmlType() {
        return name().toLowerCase();
    }
}
