package cz.muni.fi.model.perday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
}
