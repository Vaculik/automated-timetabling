package cz.muni.fi.model;

import cz.muni.fi.model.perday.RouteModel;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.StructuralModel;


public interface ModelDao {

    StructuralModel getStructuralModel();

    RouteModel getRouteModel();

    TrafficModel getTrafficModel();

    void updateStructuralModel(StructuralModel structuralModel);

    void updateRouteModel(RouteModel routeModel);

    void updateTrafficModel(TrafficModel trafficModel);
}
