package cz.muni.fi.webapp.controller;

import cz.muni.fi.TimetablingApp;
import cz.muni.fi.dto.BusDto;
import cz.muni.fi.dto.BusExampleDto;
import cz.muni.fi.dto.LinkDto;
import cz.muni.fi.dto.RouteDto;
import cz.muni.fi.dto.SiteDto;
import cz.muni.fi.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/model")
public class ModelController {

    private final ModelService modelService;
    private final TimetablingApp timetablingApp;

    @Autowired
    public ModelController(ModelService modelService, TimetablingApp timetablingApp) {
        this.modelService = modelService;
        this.timetablingApp = timetablingApp;
    }

    @RequestMapping(path = "/sites", method = RequestMethod.GET)
    public List<SiteDto> getAllSites() {
        return this.modelService.getAllSites();
    }

    @RequestMapping(path = "/routes", method = RequestMethod.GET)
    public List<RouteDto> getAllRoutes() {
        return this.modelService.getAllRoutes();
    }

    @RequestMapping(path = "/bus-example", method = RequestMethod.GET)
    public BusExampleDto getBusExample() {
        return this.timetablingApp.getBusExample().getDto();
    }

    @RequestMapping(path = "/buses", method = RequestMethod.GET)
    public List<BusDto> getAllBuses() {
        return this.modelService.getAllBuses();
    }

    @RequestMapping(path = "/links", method = RequestMethod.GET)
    public List<LinkDto> getAllLinks() {
        return this.modelService.getAllLinks();
    }
}
