package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.ui.mvc.AbstractBaseController;

@Controller
public class IndexController extends AbstractBaseController {

    @RequestMapping("/")
    public String index(Locale locale, Model model) {
        mapActivities(model);
        return "index";
    }
}
