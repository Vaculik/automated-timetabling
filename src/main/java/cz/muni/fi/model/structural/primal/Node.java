package cz.muni.fi.model.structural.primal;


import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by vacullik on 09/02/2017.
 */
public class Node {

    private long key;
    private Set<Link> adjacentLinks;

    public Node(long key) {
        this.key = key;
        this.adjacentLinks = new TreeSet<>();
    }

    public Node(long key, Set<Link> adjacentLinks) {
        if (adjacentLinks == null) {
            throw new NullPointerException("Parameter adjacentLinks is null.");
        }
        this.key = key;
        this.adjacentLinks = new TreeSet<>(adjacentLinks);
    }

    public long getKey() {
        return key;
    }

    public Collection<Link> getAdjacentLinks() {
        return Collections.unmodifiableSet(adjacentLinks);
    }

    public boolean addLink(Link link) {
        if (link == null) {
            throw new NullPointerException("Parameter link is null.");
        }
        return adjacentLinks.add(link);
    }
}
