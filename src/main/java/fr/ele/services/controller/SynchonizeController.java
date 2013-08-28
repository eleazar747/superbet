package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.ui.mvc.AbstractActivityController;

@Controller
public class SynchonizeController extends AbstractActivityController {

    private static final String URI = "synchronize";

    @Override
    public String getActivityName() {
        return "Synchronize Bookmakers";
    }

    @Override
    public String getActivityUrlBase() {
        return URI;
    }

    @Override
    public String getGroup() {
        return "admin";
    }

    @RequestMapping(URI)
    public String synchronize(Locale locale, Model model) {
        mapActivities(model);
        return "synchronizeView";
    }
}
