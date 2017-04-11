package cz.muni.fi.model;

/**
 * Created by vacullik on 20/02/2017.
 */
public class GeographicCoordinates {
    private double latitude;
    private double longitude;

    public GeographicCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}
