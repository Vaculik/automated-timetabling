package cz.muni.fi;

import cz.muni.fi.client.TimetablingClient;
import cz.muni.fi.client.TimetablingClientImpl;
import cz.muni.fi.model.structural.StructuralModel;
import cz.muni.fi.parser.JsonParser;
import cz.muni.fi.parser.JsonParserImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Hello world!
 *
 */
public class App {
    private static String apiKey = "47bd80008fdbeefeb9efbcb0929cb2e8386439eda76240bd1bb52367";

    public static void main(String[] args) {
        TimetablingClient client = new TimetablingClientImpl(apiKey);

        InputStream staticData;
        StructuralModel model;
        try {
            staticData = new FileInputStream("data/static/static_data.json");
            JsonParser jsonParser = new JsonParserImpl(staticData);
            model = jsonParser.parseStructuralModel();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
