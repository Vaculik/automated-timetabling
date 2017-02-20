package cz.muni.fi.model.perday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vacullik on 20/02/2017.
 */
public class Route {
    private String id;
    private List<Long> osmStopIds;
    private List<Long> osmWayIds;

    public Route(String id) {
        this.id = id;
    }

    public Route(String id, List<Long> osmStopIds, List<Long> osmWayIds) {
        this.id = id;
        this.osmStopIds = new ArrayList<>(osmStopIds);
        this.osmWayIds = new ArrayList<>(osmWayIds);
    }

    public String getId() {
        return id;
    }

    public List<Long> getOsmStopIds() {
        return Collections.unmodifiableList(osmStopIds);
    }

    public List<Long> getOsmWayIds() {
        return Collections.unmodifiableList(osmWayIds);
    }
}
