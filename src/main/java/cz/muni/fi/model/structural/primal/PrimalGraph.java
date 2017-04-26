package cz.muni.fi.model.structural.primal;

import java.util.*;


public class PrimalGraph {

    private Map<Long, Node> nodes;
    private Set<Link> links;

    public PrimalGraph() {
        this.nodes = new HashMap<>();
        this.links = new TreeSet<>();
    }

    public PrimalGraph(Map<Long, Node> nodes, Set<Link> links) {
        if (nodes == null) {
            throw new NullPointerException("Parameter nodes is null.");
        }
        if (links == null) {
            throw new NullPointerException("Parameter links is null.");
        }
        this.nodes = new HashMap<>(nodes);
        this.links = new HashSet<>(links);
    }

    public void addNode(Node node) {
        if (node == null) {
            throw new NullPointerException("Parameter node is null.");
        }

        nodes.put(node.getKey(), node);
    }

    public Node getNode(long key) {
        return nodes.get(key);
    }

    public boolean containsNode(Node node) {
        return this.nodes.keySet().contains(node.getKey());
    }

    public boolean addLink(Link link) {
        if (link == null) {
            throw new NullPointerException("Parameter link is null.");
        }

        if (links.contains(link)) {
            return false;
        }

        link.getFirstNode().addLink(link);
        link.getSecondNode().addLink(link);

        return links.add(link);
    }

    public Link addLink(Node firstNode, Node secondNode) {
        if (firstNode == null) {
            throw new NullPointerException("Parameter firstNode is null.");
        }
        if (secondNode == null) {
            throw new NullPointerException("Parameter secondNode is null.");
        }

        Link newLink = new Link(firstNode, secondNode);
        if (links.contains(newLink)) {
            return null;
        }
        addLink(newLink);

        return newLink;
    }

    public boolean containsLink(Link link) {
        return links.contains(link);
    }

    public Collection<Node> getAllNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    public Collection<Link> getAllLinks() {
        return Collections.unmodifiableCollection(links);
    }
}
