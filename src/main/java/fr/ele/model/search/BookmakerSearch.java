package fr.ele.model.search;

import fr.ele.core.search.Search;
import fr.ele.core.search.criteria.number.NumberValueCriteria;
import fr.ele.core.search.criteria.string.StringValueCriteria;

public class BookmakerSearch implements Search {
    private StringValueCriteria code, url;

    private NumberValueCriteria<Long> id;

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

    public NumberValueCriteria<Long> getId() {
        return id;
    }

    public void setId(NumberValueCriteria<Long> id) {
        this.id = id;
    }

}
