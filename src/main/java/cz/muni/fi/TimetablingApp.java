package cz.muni.fi;


import cz.muni.fi.client.TimetablingClient;
import cz.muni.fi.client.TimetablingClientImpl;
import cz.muni.fi.dto.BusExample;
import cz.muni.fi.model.ModelDao;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.parser.JsonParser;
import cz.muni.fi.webapp.websocket.RefreshWebSocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class TimetablingApp {

    private static final String apiKey = "47bd80008fdbeefeb9efbcb0929cb2e8386439eda76240bd1bb52367";
    private TimetablingClient client;
    private BusExample busExample = new BusExample();

    @Autowired
    private RefreshWebSocketController refreshWebSocketController;

    @Autowired
    private ModelDao modelDao;

    public TimetablingApp() {
        this.client = new TimetablingClientImpl(apiKey);
    }

    public void startProcessing() {
        System.out.println("START PROCESSING");
//        try {
//            this.client.getUpdate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Thread t2 = new Thread(this);
                    t2.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    InputStream is = client.getUpdate();
                    TrafficModel trafficModel = JsonParser.createTrafficModelParser(is).parseTrafficModel();
                    modelDao.updateTrafficModel(trafficModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refreshWebSocketController.sendRefreshMessage();
            }
        });
        thread.start();
    }

    public BusExample getBusExample() {
        return this.busExample;
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
//            model = jsonParser.createStructuralModelParser();
//        } catch (IOException | NullPointerException e) {
//            e.printStackTrace();
//        }

    }
}
