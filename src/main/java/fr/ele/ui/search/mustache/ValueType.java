package fr.ele.ui.search.mustache;

public enum ValueType {
    STRING("text"), NUMBER("number");

    private final String htmlType;

    private ValueType(String html) {
        htmlType = html;
    }

    public String getHtmlType() {
        return htmlType;
    }
}
