package cz.muni.fi.filter;


import cz.muni.fi.model.GeographicCoordinates;
import cz.muni.fi.model.perday.CoordinatesInTime;
import cz.muni.fi.model.perday.RouteModel;
import cz.muni.fi.model.perday.Trip;
import cz.muni.fi.model.structural.dual.Site;

public class SiteFilterByRoutes implements SiteFilter {

    private double maxLatitude;
    private double minLatitude;
    private double maxLongitude;
    private double minLongitude;

    public SiteFilterByRoutes(RouteModel routeModel) {
        initializeBoundaryValues(routeModel);
    }

    private void initializeBoundaryValues(RouteModel routeModel) {
        Trip firstTrip = routeModel.getTrips().get(0);
        CoordinatesInTime coordinatesInTime = firstTrip.getTimetable().get(0);
        GeographicCoordinates coords = coordinatesInTime.getCoordinates();

        maxLatitude = coords.getLatitude();
        minLatitude = coords.getLatitude();
        maxLongitude = coords.getLongitude();
        minLongitude = coords.getLongitude();

        for (Trip trip : routeModel.getTripsUniqueByRoute()) {
            for (CoordinatesInTime coordsInTime : trip.getTimetable()) {
                coords = coordsInTime.getCoordinates();
                double latitude = coords.getLatitude();
                double longitude = coords.getLongitude();
                maxLatitude = maxLatitude < latitude ? latitude : maxLatitude;
                minLatitude = minLatitude > latitude ? latitude : minLatitude;
                maxLongitude = maxLongitude < longitude ? longitude : maxLongitude;
                minLongitude = minLongitude > longitude ? longitude : minLongitude;
            }
        }
    }

    @Override
    public boolean filter(Site site) {
        GeographicCoordinates coords = site.getCoordinates();
        return coords.getLatitude() < minLatitude ||
                coords.getLatitude() > maxLatitude ||
                coords.getLongitude() < minLongitude ||
                coords.getLongitude() > maxLongitude;
    }
}
