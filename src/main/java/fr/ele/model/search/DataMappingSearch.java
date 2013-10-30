package fr.ele.model.search;

import fr.ele.core.search.Search;
import fr.ele.core.search.criteria.enums.EnumValueCriteria;
import fr.ele.core.search.criteria.string.StringValueCriteria;
import fr.ele.model.RefEntityType;

public class DataMappingSearch implements Search {
    private StringValueCriteria bookmakerCode, bookmakerValue, modelValue;

    private EnumValueCriteria<RefEntityType> refEntityType;

    public EnumValueCriteria<RefEntityType> getRefEntityType() {
        return refEntityType;
    }

    public void setRefEntityType(EnumValueCriteria<RefEntityType> refEntityType) {
        this.refEntityType = refEntityType;
    }

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
