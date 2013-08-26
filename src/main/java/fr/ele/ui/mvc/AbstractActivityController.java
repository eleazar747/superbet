package fr.ele.ui.mvc;

import org.springframework.ui.Model;

public abstract class AbstractActivityController extends AbstractBaseController
        implements ActivityController {

    @Override
    protected void mapActivities(Model model) {
        super.mapActivities(model);
        model.addAttribute("currentActivity", getActivityUrlBase());
    }

}
