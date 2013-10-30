package fr.ele.core.search;

import fr.ele.core.search.criteria.date.DateValueCriteria;
import fr.ele.core.search.criteria.enums.EnumValueCriteria;
import fr.ele.core.search.criteria.number.NumberValueCriteria;
import fr.ele.core.search.criteria.string.StringValueCriteria;

public interface CriteriaVisitor {
    void visit(NumberValueCriteria criteria);

    void visit(StringValueCriteria criteria);

    void visit(DateValueCriteria criteria);

    void visit(EnumValueCriteria criteria);
}
