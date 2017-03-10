package cz.muni.fi.dto;


import cz.muni.fi.model.structural.dual.GeographicCoordinates;

public class SiteDto {
    private GeographicCoordinates coordinates;

    public GeographicCoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(GeographicCoordinates coordinates) {
        this.coordinates = coordinates;
    }
}
