package cz.muni.fi;


import cz.muni.fi.client.TimetablingClient;
import cz.muni.fi.client.TimetablingClientImpl;
import org.springframework.stereotype.Component;

@Component
public class TimetablingApp {

    private static final String apiKey = "47bd80008fdbeefeb9efbcb0929cb2e8386439eda76240bd1bb52367";
    private TimetablingClient client;

    public TimetablingApp() {
        this.client = new TimetablingClientImpl(apiKey);
    }

    public void startProcessing() {
        System.out.println("START PROCESSING");
    }

    public void stopProcessing() {

    }

    private void processOneRound() {

    }

    public static void main(String[] args) throws InterruptedException {
//        TimetablingClient client = new TimetablingClientImpl(apiKey);

//        InputStream staticData;
//        StructuralModel model = null;
//        try {
//            staticData = new FileInputStream("data/static/static_data.json");
//            JsonParser jsonParser = new JsonParserImpl(staticData);
//            model = jsonParser.parseStructuralModel();
//        } catch (IOException | NullPointerException e) {
//            e.printStackTrace();
//        }

    }
}
