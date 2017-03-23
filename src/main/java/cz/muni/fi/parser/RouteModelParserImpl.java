package cz.muni.fi.parser;

import cz.muni.fi.model.perday.CoordinatesInTime;
import cz.muni.fi.model.perday.CoordinatesInTimeCompBySequence;
import cz.muni.fi.model.perday.Route;
import cz.muni.fi.model.perday.RouteModel;
import cz.muni.fi.model.perday.Trip;
import cz.muni.fi.model.structural.dual.GeographicCoordinates;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteModelParserImpl implements RouteModelParser {
    private JSONObject json;

    public RouteModelParserImpl(JSONObject json) {
        this.json = json;
    }

    public RouteModel parseRouteModel() {
        JSONObject routesJson = json.getJSONObject("R");
        List<Route> routes = parseRoutes(routesJson);
        List<Trip> trips = parseTrips(json, routes);
        return new RouteModel(routes, trips);
    }

    private List<Route> parseRoutes(JSONObject routesJson) {
        List<Route> resultRoutes = new ArrayList<>();
        for (String key : routesJson.keySet()) {
            JSONObject routeJson = routesJson.getJSONObject(key);
            List<Long> osmStopIds = parseArrayOfLongs(routeJson.getJSONArray("osm_stop_ids"));
            List<Long> osmWayIds = parseArrayOfLongs(routeJson.getJSONArray("osm_way_ids"));
            Route parsedRoute = new Route(key, osmStopIds, osmWayIds);
            resultRoutes.add(parsedRoute);
        }
        return resultRoutes;
    }

    private List<Long> parseArrayOfLongs(JSONArray jsonArray) {
        List<Long> resultList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            resultList.add(jsonArray.getLong(i));
        }
        return resultList;
    }

    private List<Trip> parseTrips(JSONObject json, List<Route> routes) {
        JSONObject tripsJson = json.getJSONObject("V");
        List<Trip> tripsWithoutTimetables = parseTripsWithoutTimetables(tripsJson, routes);
        JSONObject timetablesJson = json.getJSONObject("C");
        Map<String, List<CoordinatesInTime>> timetables = parseTimetables(timetablesJson);
        List<Trip> resultTrips = addTimetablesToTrips(tripsWithoutTimetables, timetables);
        return resultTrips;
    }

    private List<Trip> parseTripsWithoutTimetables(JSONObject tripsJson, List<Route> routes) {
        List<Trip> resultTrips = new ArrayList<>();
        for (String key : tripsJson.keySet()) {
            JSONObject tripJson = tripsJson.getJSONObject(key);
            Integer directionId = tripJson.getInt("direction_id");
            String routeId = tripJson.getString("route");
            String tripHeadsign = tripJson.getString("trip_headsign");
            Route route = routes.stream()
                    .filter((Route r) -> r.getId().equals(routeId))
                    .findFirst()
                    .get();
            Trip newTrip  = new Trip.Builder()
                    .id(key)
                    .directionId(directionId)
                    .tripHeadsign(tripHeadsign)
                    .route(route)
                    .build();
            resultTrips.add(newTrip);
        }
        return resultTrips;
    }

    private Map<String, List<CoordinatesInTime>> parseTimetables(JSONObject timetablesJson) {
        Map<String, List<CoordinatesInTime>> resultTimetables = new HashMap<>();
        for (String key : timetablesJson.keySet()) {
            JSONArray tripTimetableJson = timetablesJson.getJSONArray(key);
            List<CoordinatesInTime> tripTimetable = parseTripTimetable(tripTimetableJson);
            Comparator<CoordinatesInTime> bySequenceComp = new CoordinatesInTimeCompBySequence();
            tripTimetable.sort(bySequenceComp);
            resultTimetables.put(key, tripTimetable);
        }
        return resultTimetables;
    }

    private List<CoordinatesInTime> parseTripTimetable(JSONArray tripTimetableJson) {
        List<CoordinatesInTime> resultTripTimetable = new ArrayList<>();
        for (int i = 0; i < tripTimetableJson.length(); i++) {
            JSONObject coordinatesInTimeJson = tripTimetableJson.getJSONObject(i);
            resultTripTimetable.add(parseCoordinatesInTime(coordinatesInTimeJson));
        }
        return resultTripTimetable;
    }

    private CoordinatesInTime parseCoordinatesInTime(JSONObject coordinatesInTimeJson) {
        double latitude = coordinatesInTimeJson.getDouble("lat");
        double longitude = coordinatesInTimeJson.getDouble("lon");
        GeographicCoordinates coordinates = new GeographicCoordinates(latitude, longitude);
        int sequence = coordinatesInTimeJson.getInt("seq");
        String timeStr = coordinatesInTimeJson.getString("time");
        LocalTime time = parseLocalTime(timeStr);
        return new CoordinatesInTime(coordinates, time, sequence);
    }

    private LocalTime parseLocalTime(String time) {
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        int seconds = Integer.parseInt(timeParts[2]);
        hours = hours % 24;
        return LocalTime.of(hours, minutes, seconds);
    }

    private List<Trip> addTimetablesToTrips(List<Trip> trips, Map<String, List<CoordinatesInTime>> timetables) {
        for(Trip trip : trips) {
            String tripId = trip.getId();
            if (timetables.containsKey(tripId)) {
                trip.setTimetable(timetables.get(tripId));
            }
        }
        return trips;
    }
}
