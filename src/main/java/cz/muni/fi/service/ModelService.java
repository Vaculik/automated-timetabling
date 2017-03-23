package cz.muni.fi.service;


import cz.muni.fi.dto.RouteDto;
import cz.muni.fi.dto.SiteDto;

import java.util.List;

public interface ModelService {

    List<SiteDto> getAllSites();

    List<RouteDto> getAllRoutes();
}

