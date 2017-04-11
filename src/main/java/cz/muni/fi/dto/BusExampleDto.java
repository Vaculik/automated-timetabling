package cz.muni.fi.dto;

import cz.muni.fi.model.GeographicCoordinates;

/**
 * Created by vacullik on 23/03/2017.
 */
public class BusExampleDto {
    private GeographicCoordinates coordinates;

    public GeographicCoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(GeographicCoordinates coordinates) {
        this.coordinates = coordinates;
    }
}
