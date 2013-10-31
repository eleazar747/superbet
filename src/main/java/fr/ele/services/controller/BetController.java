package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.core.search.ui.SearchToUi;
import fr.ele.model.search.BetSearch;
import fr.ele.ui.mvc.AbstractActivityController;
import fr.ele.ui.mvc.annotation.Activity;
import fr.ele.ui.mvc.annotation.Group;

@Controller
@Group("bets")
@RequestMapping("bets")
public class BetController extends AbstractActivityController {
    @RequestMapping("sures")
    @Activity(name = "Sure Bets")
    public String sure(Locale locale, Model model) {
        mapActivities(model);
        return "betSureView";
    }

    @RequestMapping("search")
    @Activity(name = "Search Bets")
    public String search(Locale locale, Model model) {
        mapActivities(model);
        model.addAttribute("searchForm", SearchToUi.transform(BetSearch.class));
        return "betSearchView";
    }
}
