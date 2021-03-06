package fr.ele.core.search.ui;

public enum ValueType {
    STRING("text"), NUMBER("number"), DATE("date"), ENUM("select");

    private final String htmlType;

    private ValueType(String html) {
        htmlType = html;
    }

    public String getHtmlType() {
        return htmlType;
    }
}
