package fr.ele.services.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.model.DataMapping;
import fr.ele.services.rest.DataMappingRestService;
import fr.ele.ui.mvc.annotation.Activity;

@Controller
@RequestMapping(DataMappingController.URI)
public class DataMappingController extends AbstractRefController {

    static final String URI = "datamapping";

    @Override
    @Activity(name = "Data Mapping")
    public String list(Locale locale, Model model) {
        return super.list(locale, model);
    }

    @Override
    protected Class<?> handledModelClass() {
        return DataMapping.class;
    }

    @Override
    protected String resourceUri() {
        return DataMappingRestService.PATH;
    }

}
