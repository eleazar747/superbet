package fr.ele.services.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.model.ref.Match;

@Controller
@RequestMapping(MatchController.URI)
public class MatchController extends AbstractRefController {

    static final String URI = "match";

    @Override
    public String getActivityName() {
        return "Match";
    }

    @Override
    public String getActivityUrlBase() {
        return URI;
    }

    @Override
    protected Class<?> handledModelClass() {
        return Match.class;
    }

}
