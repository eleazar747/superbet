package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.model.ref.BetType;
import fr.ele.services.rest.BetTypeRestService;
import fr.ele.ui.mvc.annotation.Activity;

@Controller
@RequestMapping(BetTypeController.URI)
public class BetTypeController extends AbstractRefController {

    public static final String URI = "bettype";

    @Override
    @Activity(name = "Bet Type")
    public String list(Locale locale, Model model) {
        return super.list(locale, model);
    }

    @Override
    protected Class<?> handledModelClass() {
        return BetType.class;
    }

    @Override
    protected String resourceUri() {
        return BetTypeRestService.PATH;
    }

}
