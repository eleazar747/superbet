package fr.ele.core.search.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ele.core.search.SearchOperator;

public class UiCriteria {

    private List<SearchOperator> operators;

    private String name, title;

    private ValueType type;

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType type) {
        this.type = type;
    }

    public List<SearchOperator> getOperators() {
        return operators;
    }

    public void setOperators(List<SearchOperator> operators) {
        this.operators = operators;
    }

    public void addOperators(SearchOperator... searchOperators) {
        if (operators == null) {
            operators = new ArrayList<SearchOperator>(searchOperators.length);
        }
        operators.addAll(Arrays.asList(searchOperators));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
