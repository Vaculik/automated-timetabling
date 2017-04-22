package cz.muni.fi.model.structural.dual;

import cz.muni.fi.filter.SiteFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DualGraph {
    private Map<Long, Site> sites;

    public DualGraph(Map<Long, Site> sites) {
        if (sites == null) {
            throw new NullPointerException("Parameter sites is null.");
        }
        this.sites = new HashMap<>(sites);
    }

    public Site getSiteById(long siteId) {
        return sites.get(siteId);
    }

    public Collection<Site> getAllSites() {
        return Collections.unmodifiableCollection(sites.values());
    }

    public void filterSites(SiteFilter siteFilter) {
        List<Long> filteredIds = new ArrayList<>();
        for (Long key : sites.keySet()) {
            if (siteFilter.filter(sites.get(key))) {
                filteredIds.add(key);
            }
        }
        for (Long key : filteredIds) {
            sites.remove(key);
        }
        printIdsAsJsonArray(filteredIds);
    }

    private void printIdsAsJsonArray(List<Long> ids) {
        StringBuilder strToPrint = new StringBuilder("[");
        for (int i = 0; i < ids.size() - 2; i++) {
            strToPrint.append(ids.get(i)).append(", ");
        }
        strToPrint.append(ids.get(ids.size()-1)).append("]");
        System.out.println(strToPrint.toString());
    }
}
