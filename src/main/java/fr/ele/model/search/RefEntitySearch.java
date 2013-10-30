package fr.ele.model.search;

import fr.ele.core.search.Search;
import fr.ele.core.search.criteria.number.NumberValueCriteria;
import fr.ele.core.search.criteria.string.StringValueCriteria;

public abstract class RefEntitySearch implements Search {

    private NumberValueCriteria<Long> id;

    private StringValueCriteria code;

    public StringValueCriteria getCode() {
        return code;
    }

    public void setCode(StringValueCriteria code) {
        this.code = code;
    }

    public NumberValueCriteria<Long> getId() {
        return id;
    }

    public void setId(NumberValueCriteria<Long> id) {
        this.id = id;
    }
}
