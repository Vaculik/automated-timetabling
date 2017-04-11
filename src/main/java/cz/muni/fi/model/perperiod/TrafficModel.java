package cz.muni.fi.model.perperiod;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TrafficModel {
    private List<Bus> buses;

    public TrafficModel(List<Bus> buses) {
        this.buses = new ArrayList<>(buses);
    }

    public List<Bus> getBuses() {
        return Collections.unmodifiableList(this.buses);
    }
}
