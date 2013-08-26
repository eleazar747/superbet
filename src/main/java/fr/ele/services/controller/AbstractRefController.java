package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.ui.model.MetaMapping;
import fr.ele.ui.model.MetaRegistry;
import fr.ele.ui.mvc.AbstractBaseController;
import fr.ele.ui.mvc.ActivityController;

public abstract class AbstractRefController extends AbstractBaseController
        implements ActivityController {

    @Autowired
    private MetaRegistry metaRegistry;

    protected abstract Class<?> handledModelClass();

    @Override
    public String getGroup() {
        return "referential";
    }

    @RequestMapping("/")
    public String list(Locale locale, Model model) {
        MetaMapping metaMapping = metaRegistry
                .getMetaMapping(handledModelClass());
        model.addAttribute("model", metaMapping);
        mapActivities(model);
        return "refView";
    }

    public String edit(Locale locale, Model model) {
        return "editRefView";
    }
}
