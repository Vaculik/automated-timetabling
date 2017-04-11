package cz.muni.fi.model.perday;

import cz.muni.fi.model.GeographicCoordinates;

import java.time.LocalTime;

public class CoordinatesInTime {
    private GeographicCoordinates coordinates;
    private LocalTime time;
    private int sequence;

    public CoordinatesInTime(GeographicCoordinates coordinates, LocalTime time, int sequence) {
        this.coordinates = coordinates;
        this.time = time;
    }

    public GeographicCoordinates getCoordinates() {
        return coordinates;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getSequence() {
        return sequence;
    }
}
