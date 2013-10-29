package fr.ele.model.search;

import fr.ele.core.search.Search;
import fr.ele.core.search.criteria.string.StringValueCriteria;

public class DataMappingSearch implements Search {
    private StringValueCriteria bookmakerCode, bookmakerValue, modelValue;

    public StringValueCriteria getBookmakerCode() {
        return bookmakerCode;
    }

    public void setBookmakerCode(StringValueCriteria bookmakerCode) {
        this.bookmakerCode = bookmakerCode;
    }

    public StringValueCriteria getBookmakerValue() {
        return bookmakerValue;
    }

    public void setBookmakerValue(StringValueCriteria bookmakerValue) {
        this.bookmakerValue = bookmakerValue;
    }

    public StringValueCriteria getModelValue() {
        return modelValue;
    }

    public void setModelValue(StringValueCriteria modelValue) {
        this.modelValue = modelValue;
    }

}
