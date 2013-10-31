package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.core.search.Search;
import fr.ele.core.search.ui.SearchToUi;
import fr.ele.ui.model.MetaMapping;
import fr.ele.ui.model.MetaRegistry;
import fr.ele.ui.mvc.AbstractActivityController;
import fr.ele.ui.mvc.annotation.Group;

@Group("referential")
public abstract class AbstractRefController extends AbstractActivityController {

    @Autowired
    private MetaRegistry metaRegistry;

    protected abstract Class<?> handledModelClass();

    @RequestMapping
    public String list(Locale locale, Model model) {
        MetaMapping metaMapping = metaRegistry
                .getMetaMapping(handledModelClass());
        model.addAttribute("model", metaMapping);
        model.addAttribute("resource", resourceUri());
        mapActivities(model);
        addSearch(model);
        return "refView";
    }

    public String edit(Locale locale, Model model) {
        return "editRefView";
    }

    protected abstract String resourceUri();

    protected <T extends Search> Class<T> getSearchClass() {
        return null;
    }

    protected void addSearch(Model model) {
        if (getSearchClass() != null) {
            model.addAttribute("searchForm",
                    SearchToUi.transform(getSearchClass()));
        }
    }
}
