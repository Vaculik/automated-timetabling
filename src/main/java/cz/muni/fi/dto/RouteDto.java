package cz.muni.fi.dto;


import cz.muni.fi.model.GeographicCoordinates;

import java.util.List;

public class RouteDto {
    private List<GeographicCoordinates> coordinates;

    public List<GeographicCoordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<GeographicCoordinates> coordinates) {
        this.coordinates = coordinates;
    }
}
