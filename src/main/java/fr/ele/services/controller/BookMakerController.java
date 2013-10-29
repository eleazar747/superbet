package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.model.ref.BookMaker;
import fr.ele.model.search.BookmakerSearch;
import fr.ele.services.rest.BookMakerRestService;
import fr.ele.ui.mvc.annotation.Activity;

@Controller
@RequestMapping(BookMakerController.URI)
public class BookMakerController extends AbstractRefController {

    static final String URI = "bookmaker";

    @Override
    @Activity(name = "BookMaker")
    public String list(Locale locale, Model model) {
        return super.list(locale, model);
    }

    @Override
    protected Class handledModelClass() {
        return BookMaker.class;
    }

    @Override
    protected String resourceUri() {
        return BookMakerRestService.PATH;
    }

    @Override
    protected Class<BookmakerSearch> getSearchClass() {
        return BookmakerSearch.class;
    }
}
