package cz.muni.fi.model.structural.dual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vacullik on 20/02/2017.
 */
public class Site {
    private int id;
    private GeographicCoordinates coordinates;
    private List<Arm> arms;

    public Site(int id, GeographicCoordinates coordinates) {
        this(id, coordinates, new ArrayList<>());
    }

    public Site(int id, GeographicCoordinates coordinates, List<Arm> arms) {
        this.id = id;
        this.coordinates = coordinates;
        this.arms = new ArrayList<>(arms);
    }

    public int getId() {
        return this.id;
    }

    public GeographicCoordinates getCoordinates() {
        return coordinates;
    }

    public List<Arm> getArms() {
        return Collections.unmodifiableList(arms);
    }

    public void setArms(List<Arm> arms) {
        this.arms = new ArrayList<>(arms);
    }
}
