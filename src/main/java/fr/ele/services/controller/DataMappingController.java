package fr.ele.services.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ele.model.DataMapping;
import fr.ele.services.rest.DataMappingRestService;

@Controller
@RequestMapping(DataMappingController.URI)
public class DataMappingController extends AbstractRefController {

    static final String URI = "datamapping";

    @Override
    public String getActivityName() {
        return "Data Mapping";
    }

    @Override
    public String getActivityUrlBase() {
        return URI;
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
