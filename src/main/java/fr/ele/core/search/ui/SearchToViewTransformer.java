package fr.ele.core.search.ui;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import fr.ele.core.search.SearchCriteria;

public class SearchToViewTransformer {

    public SearchView transform(Class<?> clazz) {
        List<CriteriaView> views = new ArrayList<CriteriaView>();
        for (Method method : clazz.getMethods()) {
            String name = method.getName();
            if (name.startsWith("get")) {
                if (SearchCriteria.class.isAssignableFrom(method
                        .getReturnType())) {
                    String propertyName = StringUtils.removeStart(name, "get");
                    CriteriaView criteria = new CriteriaView();
                    criteria.setName(WordUtils.uncapitalize(propertyName));
                    criteria.setLabel(propertyName);
                    views.add(criteria);
                }
            }
        }
        SearchView searchView = new SearchView();
        searchView.setCriterias(views);
        return searchView;
    }
}
