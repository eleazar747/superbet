package fr.ele.core.search;

public interface CriteriaVisitor {
    void visit(NumberValueCriteria criteria);

    void visit(StringValueCriteria criteria);
}
