package cz.muni.fi.service;


import cz.muni.fi.dto.ArmDto;
import cz.muni.fi.dto.RouteDto;
import cz.muni.fi.dto.SiteDto;
import cz.muni.fi.model.ModelDao;
import cz.muni.fi.model.perday.CoordinatesInTime;
import cz.muni.fi.model.perday.RouteModel;
import cz.muni.fi.model.perday.Trip;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.dual.Arm;
import cz.muni.fi.model.structural.dual.DualGraph;
import cz.muni.fi.model.GeographicCoordinates;
import cz.muni.fi.model.structural.dual.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            }
            armDtos.add(armDto);
        }
        siteDto.setArms(armDtos);
        return siteDto;
    }

    public List<RouteDto> getAllRoutes() {
        RouteModel routeModel = modelDao.getRouteModel();
        List<RouteDto> resultRoutes = new ArrayList<>();
        Set<String> routeIds = new HashSet<>();
        for(Trip trip : routeModel.getTrips()) {
            String routeId = trip.getRoute().getId();
            if (routeIds.add(routeId)) {
                resultRoutes.add(makeRouteDtoFromTrip(trip));
            }
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
}
