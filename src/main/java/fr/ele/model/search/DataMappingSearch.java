package fr.ele.model.search;

import fr.ele.core.search.Search;
import fr.ele.core.search.criteria.enums.EnumValueCriteria;
import fr.ele.core.search.criteria.string.StringValueCriteria;
import fr.ele.model.RefEntityType;

public class DataMappingSearch implements Search {
    private StringValueCriteria bookmakerValue, modelValue;

    private BookmakerSearch bookmaker;

    private EnumValueCriteria<RefEntityType> refEntityType;

    public BookmakerSearch getBookmaker() {
        return bookmaker;
    }

    public void setBookmaker(BookmakerSearch bookmaker) {
        this.bookmaker = bookmaker;
    }

    public EnumValueCriteria<RefEntityType> getRefEntityType() {
        return refEntityType;
    }

    public void setRefEntityType(EnumValueCriteria<RefEntityType> refEntityType) {
        this.refEntityType = refEntityType;
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
