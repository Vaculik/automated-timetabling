package cz.muni.fi.model.perday;

import java.util.Collections;
import java.util.List;

/**
 * Created by vacullik on 22/02/2017.
 */
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
    }

    public List<Route> getRoutes() {
        return Collections.unmodifiableList(routes);
    }

    public List<Trip> getTrips() {
        return Collections.unmodifiableList(trips);
    }
}
