package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.ui.mvc.AbstractActivityController;
import fr.ele.ui.mvc.annotation.Activity;
import fr.ele.ui.mvc.annotation.Group;

@Controller
@Group("bets")
@RequestMapping("bets")
public class BetController extends AbstractActivityController {
    @RequestMapping("sures")
    @Activity(name = "Sure Bets")
    public String synchronize(Locale locale, Model model) {
        mapActivities(model);
        return "betSearchView";
    }
}
