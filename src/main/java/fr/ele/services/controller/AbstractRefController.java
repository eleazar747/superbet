package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "refView";
    }

    public String edit(Locale locale, Model model) {
        return "editRefView";
    }

    protected abstract String resourceUri();
}
