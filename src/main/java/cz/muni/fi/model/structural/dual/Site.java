package cz.muni.fi.model.structural.dual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vacullik on 20/02/2017.
 */
public class Site {
    private GeographicCoordinates coordinates;
    private List<Arm> arms;

    public Site(long id, GeographicCoordinates coordinates, List<Arm> arms) {
        this.coordinates = coordinates;
        this.arms = new ArrayList<>(arms);
    }

    public GeographicCoordinates getCoordinates() {
        return coordinates;
    }

    public List<Arm> getArms() {
        return Collections.unmodifiableList(arms);
    }
}
