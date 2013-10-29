package fr.ele.core.search;


public abstract class SearchCriteria {
    public abstract void accept(CriteriaVisitor visitor);
}
