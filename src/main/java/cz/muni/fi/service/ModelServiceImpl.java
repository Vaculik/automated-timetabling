package cz.muni.fi.service;


import cz.muni.fi.dto.ArmDto;
import cz.muni.fi.dto.BusDto;
import cz.muni.fi.dto.LinkDto;
import cz.muni.fi.dto.RouteDto;
import cz.muni.fi.dto.SiteDto;
import cz.muni.fi.model.ModelDao;
import cz.muni.fi.model.perday.CoordinatesInTime;
import cz.muni.fi.model.perday.RouteModel;
import cz.muni.fi.model.perday.Trip;
import cz.muni.fi.model.perperiod.Bus;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.dual.Arm;
import cz.muni.fi.model.structural.dual.DualGraph;
import cz.muni.fi.model.GeographicCoordinates;
import cz.muni.fi.model.structural.dual.Site;
import cz.muni.fi.model.structural.primal.Link;
import cz.muni.fi.model.structural.primal.PrimalGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelServiceImpl implements ModelService {

    private ModelDao modelDao;

    @Autowired
    public ModelServiceImpl(ModelDao modelDao) {
        this.modelDao = modelDao;
    }

    public List<SiteDto> getAllSites() {
        DualGraph dualGraph = modelDao.getStructuralModel().getDualGraph();
        List<SiteDto> resultSites = new ArrayList<>();
        for(Site site : dualGraph.getAllSites()) {
            resultSites.add(makeSiteDtoFromSite(site));
        }
        return resultSites;
    }

    private SiteDto makeSiteDtoFromSite(Site site) {
        SiteDto siteDto = new SiteDto();
        siteDto.setId(site.getId());
        siteDto.setCoordinates(site.getCoordinates());
        List<ArmDto> armDtos = new ArrayList<>();
        for (Arm arm : site.getArms()) {
            ArmDto armDto = new ArmDto();
            armDto.setArmAngle(arm.getArmAngle());
            armDto.setStreetName(arm.getStreetName());
            Site nextSite = arm.getNextSite();
            if (nextSite != null) {
                armDto.setNextSiteId(arm.getNextSite().getId());
                armDto.setArrivalVehicles(arm.getArrivalVehicles());
                armDto.setPriorityVehicles(arm.getPriorityVehicles());
                armDto.setVehicles(arm.getVehicles());
                armDto.setPriorityVehiclesRatio(arm.getPriorityVehiclesRatio());
                armDto.setVehiclesRatio(arm.getVehiclesRatio());
            }
            armDtos.add(armDto);
        }
        siteDto.setArms(armDtos);
        return siteDto;
    }

    public List<RouteDto> getAllRoutes() {
        RouteModel routeModel = modelDao.getRouteModel();
        List<RouteDto> resultRoutes = new ArrayList<>();
//        System.out.println("NUMBER OF TRIPS: " + routeModel.getTrips().size());
        for(Trip trip : routeModel.getTripsUniqueByRoute()) {
            resultRoutes.add(makeRouteDtoFromTrip(trip));
        }
        return resultRoutes;
    }

    private RouteDto makeRouteDtoFromTrip(Trip trip) {
        RouteDto routeDto = new RouteDto();
        List<GeographicCoordinates> coordinates = new ArrayList<>();
        for (CoordinatesInTime coordInTime : trip.getTimetable()) {
            coordinates.add(coordInTime.getCoordinates());
        }
        routeDto.setCoordinates(coordinates);
        return routeDto;
    }

    public List<BusDto> getAllBuses() {
        List<BusDto> resultBuses = new ArrayList<>();
        TrafficModel trafficModel = modelDao.getTrafficModel();
        for (Bus bus : trafficModel.getBuses()) {
            BusDto busDto = new BusDto();
            busDto.setCoordinates(bus.getCoordinates());
            resultBuses.add(busDto);
        }
        return resultBuses;
    }

    public List<LinkDto> getAllLinks() {
        List<LinkDto> resultLinks = new ArrayList<>();
        PrimalGraph primalGraph = modelDao.getStructuralModel().getPrimalGraph();
        for (Link link : primalGraph.getAllLinks()) {
            LinkDto linkDto = new LinkDto();
            linkDto.setFirstSiteId(link.getFirstNode().getKey());
            linkDto.setSecondSiteId(link.getSecondNode().getKey());
            resultLinks.add(linkDto);
        }
        return resultLinks;
    }
}
