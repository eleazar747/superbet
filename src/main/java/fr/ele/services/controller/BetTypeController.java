package fr.ele.services.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.model.ref.BetType;

@Controller
@RequestMapping(BetTypeController.URI)
public class BetTypeController extends AbstractRefController {

    public static final String URI = "bettype";

    @Override
    public String getActivityName() {
        return "Bet Type";
    }

    @Override
    public String getActivityUrlBase() {
        return URI;
    }

    @Override
    protected Class<?> handledModelClass() {
        return BetType.class;
    }

    @Override
    protected String resourceUri() {
        return "betTypes";
    }

}
