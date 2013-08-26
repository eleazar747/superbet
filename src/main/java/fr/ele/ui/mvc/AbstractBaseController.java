package fr.ele.ui.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

public class AbstractBaseController implements BaseController {

    @Autowired
    private ActivitiesRegistry registry;

    protected void mapActivities(Model model) {
        model.addAttribute("groups", registry.getGroupActivities());
    }
}
