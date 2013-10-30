package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.model.ref.Sport;
import fr.ele.model.search.SportSearch;
import fr.ele.services.rest.SportRestService;
import fr.ele.ui.mvc.annotation.Activity;

@Controller
@RequestMapping(SportController.URI)
public class SportController extends AbstractRefController {

    static final String URI = "sport";

    @Override
    @Activity(name = "Sport")
    public String list(Locale locale, Model model) {
        return super.list(locale, model);
    }

    @Override
    protected Class<?> handledModelClass() {
        return Sport.class;
    }

    @Override
    protected String resourceUri() {
        return SportRestService.PATH;
    }

    @Override
    protected Class<SportSearch> getSearchClass() {
        return SportSearch.class;
    }

}
