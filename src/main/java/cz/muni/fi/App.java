package cz.muni.fi;

import cz.muni.fi.client.TimetablingClient;
import cz.muni.fi.client.TimetablingClientImpl;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    private static String apiKey = "47bd80008fdbeefeb9efbcb0929cb2e8386439eda76240bd1bb52367";

    public static void main(String[] args) {
        TimetablingClient client = new TimetablingClientImpl(apiKey);

        try {
            client.getEvaluation();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject("{}");
        System.out.println(json.getJSONArray("V"));
    }
}
