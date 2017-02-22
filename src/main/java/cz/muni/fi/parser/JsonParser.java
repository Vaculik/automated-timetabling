package cz.muni.fi.parser;

import cz.muni.fi.model.perday.RouteModel;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.StructuralModel;

import java.io.InputStream;

/**
 * Created by vacullik on 22/02/2017.
 */
public interface JsonParser {

    StructuralModel parseStructuralModel();

    RouteModel parseRouteModel();

    TrafficModel parseTrafficModel();
}
