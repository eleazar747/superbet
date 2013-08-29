package fr.ele.services.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.model.ref.Sport;
import fr.ele.services.rest.SportRestService;

@Controller
@RequestMapping(SportController.URI)
public class SportController extends AbstractRefController {

    static final String URI = "sport";

    @Override
    public String getActivityName() {
        return "Sport";
    }

    @Override
    public String getActivityUrlBase() {
        return URI;
    }

    @Override
    protected Class<?> handledModelClass() {
        return Sport.class;
    }

    @Override
    protected String resourceUri() {
        return SportRestService.PATH;
    }

}