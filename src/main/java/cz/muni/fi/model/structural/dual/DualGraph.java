package cz.muni.fi.model.structural.dual;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vacullik on 22/02/2017.
 */
public class DualGraph {
    private Map<Long, Site> sites;

    public DualGraph(Map<Long, Site> sites) {
        if (sites == null) {
            throw new NullPointerException("Parameter sites is null.");
        }
        this.sites = new HashMap<>(sites);
    }

    public Collection<Site> getAllSites() {
        return Collections.unmodifiableCollection(sites.values());
    }
}
