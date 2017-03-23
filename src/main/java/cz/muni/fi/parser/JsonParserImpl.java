package cz.muni.fi.parser;

import cz.muni.fi.model.perday.RouteModel;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.StructuralModel;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonParserImpl implements JsonParser {
    private StructuralModelParser structuralModelParser;
    private RouteModelParser routeModelParser;

    public JsonParserImpl(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("Parameter inputStream is null");
        }
        JSONObject json = getJsonObject(inputStream);
        this.structuralModelParser = new StructuralModelParserImpl(json);
        this.routeModelParser = new RouteModelParserImpl(json);
    }

    private JSONObject getJsonObject(InputStream is) throws IOException {
        return new JSONObject(getContentInString(is));
    }

    private String getContentInString(InputStream is) throws IOException {
        StringBuilder result = new StringBuilder();
        String line = "";
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    @Override
    public StructuralModel parseStructuralModel() {
        return structuralModelParser.parseStructuralModel();
    }

    @Override
    public RouteModel parseRouteModel() {
        return routeModelParser.parseRouteModel();
    }

    @Override
    public TrafficModel parseTrafficModel() {
        return null;
    }


}
