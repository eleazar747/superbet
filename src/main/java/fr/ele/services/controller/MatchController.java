package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.model.ref.Match;
import fr.ele.model.search.MatchSearch;
import fr.ele.services.rest.MatchRestService;
import fr.ele.ui.mvc.annotation.Activity;

@Controller
@RequestMapping(MatchController.URI)
public class MatchController extends AbstractRefController {

    static final String URI = "match";

    @Override
    @Activity(name = "Match")
    public String list(Locale locale, Model model) {
        return super.list(locale, model);
    }

    @Override
    protected Class<?> handledModelClass() {
        return Match.class;
    }

    @Override
    protected String resourceUri() {
        return MatchRestService.PATH;
    }

    @Override
    protected Class<MatchSearch> getSearchClass() {
        return MatchSearch.class;
    }

}
