package cz.muni.fi.parser;


import cz.muni.fi.model.GeographicCoordinates;
import cz.muni.fi.model.perperiod.Bus;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.StructuralModel;
import cz.muni.fi.model.structural.dual.Arm;
import cz.muni.fi.model.structural.dual.DualGraph;
import cz.muni.fi.model.structural.dual.Site;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class TrafficModelParserImpl implements TrafficModelParser {

    private JSONObject json;

    public TrafficModelParserImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public TrafficModel parseTrafficModel() {
        JSONObject busesJson = json.getJSONObject("B");
        List<Bus> buses = parseBuses(busesJson);
        JSONObject busStatesJson = json.getJSONObject("S");
        updateBusStates(buses, busStatesJson);
        return new TrafficModel(buses);
    }

    private void updateBusStates(List<Bus> buses, JSONObject busStatesJson) {
        for (String busKey : busStatesJson.keySet()) {
            int state = busStatesJson.getInt(busKey);
            buses.stream()
                    .filter((bus) -> bus.getId().equals(busKey))
                    .findFirst()
                    .ifPresent((bus) -> bus.setState(state));
        }
    }

    private List<Bus> parseBuses(JSONObject busesJson) {
        List<Bus> resultBuses = new ArrayList<>();
        for (String busKey : busesJson.keySet()) {
            JSONObject busJson = busesJson.getJSONObject(busKey);
            Bus bus = new Bus(busKey);
            updateBus(bus, busJson);
            resultBuses.add(bus);
        }
        return resultBuses;
    }

    private void updateBus(Bus bus, JSONObject busJson) {
        double latitude = busJson.getDouble("lat");
        double longitude = busJson.getDouble("lon");
        GeographicCoordinates coordinates = new GeographicCoordinates(latitude, longitude);
        bus.setCoordinates(coordinates);

        String siteId = busJson.optString("site_id");
        if (siteId != null && !siteId.equals("")) {
            bus.setSiteId(Long.parseLong(siteId));
            String armNumKey = "arm_num";
            if (busJson.has(armNumKey)) {
                int armNum = busJson.getInt("arm_num");
                bus.setArmNum(armNum);
            }
        }
    }

    @Override
    public void updateTrafficModel(TrafficModel trafficModel) {

    }

    @Override
    public void updateStructuralModelByTrafficData(StructuralModel structuralModel) {
        DualGraph dualGraph = structuralModel.getDualGraph();

        JSONObject arrivalVehiclesJson = json.getJSONObject("A");
        updateSites(dualGraph,
                arrivalVehiclesJson,
                (value) -> (arm) -> arm.setArrivalVehicles(value));

        JSONObject priorityVehiclesRatioJson = json.getJSONObject("s");
        updateSites(dualGraph,
                priorityVehiclesRatioJson,
                (value) -> (arm) -> arm.setPriorityVehiclesRatio(value));

        JSONObject vehiclesRatioJson = json.getJSONObject("r");
        updateSites(dualGraph,
                vehiclesRatioJson,
                (value) -> (arm) -> arm.setArrivalVehicles(value));

        JSONObject priorityVehiclesJson = json.getJSONObject("X");
        updateSites(dualGraph,
                priorityVehiclesJson,
                (value) -> (arm) -> arm.setPriorityVehicles(value));

        JSONObject vehiclesJson = json.getJSONObject("W");
        updateSites(dualGraph,
                vehiclesJson,
                (value) -> (arm) -> arm.setVehicles(value));
    }

    private void updateSites(DualGraph dualGraph,
                                       JSONObject valuesToSetJson,
                                       SetValueFunctionFactory setValueFunctionFactory) {
        for (String siteKey : valuesToSetJson.keySet()) {
            long siteId = Long.parseLong(siteKey);
            Site site = dualGraph.getSiteById(siteId);
            if (site != null) {
                JSONObject siteValuesToSetJson = valuesToSetJson.getJSONObject(siteKey);
                updateSiteArms(site, siteValuesToSetJson, setValueFunctionFactory);
            }
        }
    }

    private void updateSiteArms(Site site,
                                JSONObject siteValuesToSetJson,
                                SetValueFunctionFactory setValueFunctionFactory) {
        List<Arm> arms = site.getArms();
        for (String armKey : siteValuesToSetJson.keySet()) {
            long armId = Long.parseLong(armKey);
            int value = siteValuesToSetJson.getInt(armKey);
            arms.stream()
                    .filter((a) -> a.getArmNum() == armId)
                    .findFirst()
                    .ifPresent(setValueFunctionFactory.getConsumer(value));
        }
    }

    private interface SetValueFunctionFactory {
        Consumer<Arm> getConsumer(int value);
    }
}
