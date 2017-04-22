package cz.muni.fi.model.perday;

import cz.muni.fi.dto.RouteDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RouteModel {
    private List<Route> routes;
    private List<Trip> trips;

    public RouteModel(List<Route> routes, List<Trip> trips) {
        if (routes == null) {
            throw new NullPointerException("Parameter routes is null.");
        }
        if (trips == null) {
            throw new NullPointerException("Parameter trips is null.");
        }
        this.routes = new ArrayList<>(routes);
        this.trips = new ArrayList<>(trips);
    }

    public List<Route> getRoutes() {
        return Collections.unmodifiableList(routes);
    }

    public List<Trip> getTrips() {
        return Collections.unmodifiableList(trips);
    }

    public List<Trip> getTripsUniqueByRoute() {
        List<Trip> resultTrips = new ArrayList<>();
        Set<String> routeIds = new HashSet<>();
        for(Trip trip : trips) {
            String routeId = trip.getRoute().getId();
            if (routeIds.add(routeId)) {
                resultTrips.add(trip);
            }
        }
        return resultTrips;
    }
}
