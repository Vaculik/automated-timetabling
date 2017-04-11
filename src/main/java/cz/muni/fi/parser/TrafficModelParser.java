package cz.muni.fi.parser;


import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.StructuralModel;

public interface TrafficModelParser {

    TrafficModel parseTrafficModel();

    void updateTrafficModel(TrafficModel trafficModel);

    void updateStructuralModelByTrafficData(StructuralModel structuralModel);
}
