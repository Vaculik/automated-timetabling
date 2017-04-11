package cz.muni.fi.dto;

import cz.muni.fi.model.GeographicCoordinates;


public class BusExample {
    private GeographicCoordinates coordinates;
    private static final double shift = 0.0002;

    public BusExample() {
        this.coordinates = new GeographicCoordinates(53.3389, -6.2749);
    }

    public void move() {
        double lat = this.coordinates.getLatitude() + shift;
        double lon = this.coordinates.getLongitude();
        this.coordinates = new GeographicCoordinates(lat, lon);
    }

    public BusExampleDto getDto() {
        BusExampleDto dto = new BusExampleDto();
        dto.setCoordinates(this.coordinates);
        return dto;
    }
}
