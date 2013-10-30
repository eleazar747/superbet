package fr.ele.model.search;

import fr.ele.core.search.Search;
import fr.ele.core.search.criteria.date.DateValueCriteria;
import fr.ele.core.search.criteria.string.StringValueCriteria;

public class MatchSearch implements Search {
    private StringValueCriteria code, sport;

    private DateValueCriteria date;

    public StringValueCriteria getCode() {
        return code;
    }

    public void setCode(StringValueCriteria code) {
        this.code = code;
    }

    public StringValueCriteria getSport() {
        return sport;
    }

    public void setSport(StringValueCriteria sport) {
        this.sport = sport;
    }

    public DateValueCriteria getDate() {
        return date;
    }

    public void setDate(DateValueCriteria date) {
        this.date = date;
    }
}
