package cz.muni.fi.model.perday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trip {
    private String id;
    private int directionId;
    private Route route;
    private String tripHeadsign;
    private List<CoordinatesInTime> timetable;

    public static class Builder {
        private String id;
        private int directionId;
        private Route route;
        private String tripHeadsign;
        private List<CoordinatesInTime> timetable;

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder directionId(int val) {
            directionId = val;
            return this;
        }

        public Builder route(Route val) {
            route = val;
            return this;
        }

        public Builder tripHeadsign(String val) {
            tripHeadsign = val;
            return this;
        }

        public Builder timetable(List<CoordinatesInTime> val) {
            timetable = new ArrayList<>(val);
            return this;
        }

        public Trip build() {
            return new Trip(this);
        }
    }

    private Trip(Builder builder) {
        this.id = builder.id;
        this.directionId = builder.directionId;
        this.route = builder.route;
        this.tripHeadsign = builder.tripHeadsign;
        this.timetable = builder.timetable;
    }

    public String getId() {
        return id;
    }

    public int getDirectionId() {
        return directionId;
    }

    public Route getRoute() {
        return route;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public List<CoordinatesInTime> getTimetable() {
        return Collections.unmodifiableList(timetable);
    }

    public void setTimetable(List<CoordinatesInTime> timetable) {
        this.timetable = timetable;
    }
}
