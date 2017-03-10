package cz.muni.fi.webapp.controller;

import cz.muni.fi.dto.SiteDto;
import cz.muni.fi.model.structural.StructuralModel;
import cz.muni.fi.parser.JsonParser;
import cz.muni.fi.parser.JsonParserImpl;
import cz.muni.fi.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/model")
public class ModelController {

    private final ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @RequestMapping(path = "/sites", method = RequestMethod.GET)
    public List<SiteDto> homepage() {
        return this.modelService.getAllSites();
    }
}
