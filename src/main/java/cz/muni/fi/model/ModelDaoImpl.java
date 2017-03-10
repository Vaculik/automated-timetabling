package cz.muni.fi.model;


import cz.muni.fi.model.perday.RouteModel;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.StructuralModel;
import cz.muni.fi.parser.JsonParser;
import cz.muni.fi.parser.JsonParserImpl;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ModelDaoImpl implements ModelDao {
    private StructuralModel structuralModel;
    private RouteModel routeModel;
    private TrafficModel trafficModel;

    public ModelDaoImpl() {
        InputStream staticData;
        StructuralModel model = null;
        try {
            staticData = new FileInputStream("data/static/static_data.json");
            JsonParser jsonParser = new JsonParserImpl(staticData);
            model = jsonParser.parseStructuralModel();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        this.structuralModel = model;
    }

    @Override
    public StructuralModel getStructuralModel() {
        return structuralModel;
    }

    @Override
    public RouteModel getRouteModel() {
        return routeModel;
    }

    @Override
    public TrafficModel getTrafficModel() {
        return trafficModel;
    }

    @Override
    public void updateStructuralModel(StructuralModel structuralModel) {
        if (structuralModel == null) {
            throw new NullPointerException("Parameter structuralModel is null.");
        }
        this.structuralModel = structuralModel;
    }

    @Override
    public void updateRouteModel(RouteModel routeModel) {
        if (routeModel == null) {
            throw new NullPointerException("Parameter routeModel is null.");
        }
        this.routeModel = routeModel;
    }

    @Override
    public void updateTrafficModel(TrafficModel trafficModel) {
        if (trafficModel == null) {
            throw new NullPointerException("Parameter trafficModel is null.");
        }
        this.trafficModel = trafficModel;
    }

}
