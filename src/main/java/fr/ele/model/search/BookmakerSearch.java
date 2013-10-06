package fr.ele.model.search;

import fr.ele.core.search.StringValueCriteria;

public class BookmakerSearch {
    private StringValueCriteria code, url;

    public StringValueCriteria getCode() {
        return code;
    }

    public void setCode(StringValueCriteria code) {
        this.code = code;
    }

    public StringValueCriteria getUrl() {
        return url;
    }

    public void setUrl(StringValueCriteria url) {
        this.url = url;
    }
}
