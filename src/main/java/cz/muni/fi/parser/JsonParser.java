package cz.muni.fi.parser;

import cz.muni.fi.model.perday.RouteModel;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.StructuralModel;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonParser {

    private static JSONObject getJsonObject(InputStream is) throws IOException {
        if (is == null) {
            throw new NullPointerException("Parameter inputStream is null");
        }
        return new JSONObject(getContentInString(is));
    }

    private static String getContentInString(InputStream is) throws IOException {
        StringBuilder result = new StringBuilder();
        String line = "";
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public static StructuralModelParser createStructuralModelParser(InputStream is) throws IOException {
        return new StructuralModelParserImpl(getJsonObject(is));
    }

    public static RouteModelParser createRouteModelParser(InputStream is) throws IOException {
        return new RouteModelParserImpl(getJsonObject(is));
    }

    public static TrafficModelParser createTrafficModelParser(InputStream is) throws IOException {
        return new TrafficModelParserImpl(getJsonObject(is));
    }
}
