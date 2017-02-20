package cz.muni.fi.model.perday;

import cz.muni.fi.model.structural.dual.GeographicCoordinates;

import java.time.LocalTime;
import java.util.Date;

/**
 * Created by vacullik on 20/02/2017.
 */
public class CoordinatesInTime {
    private GeographicCoordinates coordinates;
    private LocalTime time;

    public CoordinatesInTime(GeographicCoordinates coordinates, LocalTime time) {
        this.coordinates = coordinates;
        this.time = time;
    }

    public GeographicCoordinates getCoordinates() {
        return coordinates;
    }

    public LocalTime getTime() {
        return time;
    }
}
