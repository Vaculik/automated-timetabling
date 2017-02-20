package cz.muni.fi.model.structural.primal;

/**
 * Created by vacullik on 09/02/2017.
 */
public class Link {

    private Node firstNode;
    private Node secondNode;

    public Link(Node firstNode, Node secondNode) {
        if (firstNode == null) {
            throw new NullPointerException("Parameter firstNode is null.");
        }
        if (secondNode == null) {
            throw new NullPointerException("Parameter secondNode is null.");
        }

        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }

    public Node getFirstNode() {
        return this.firstNode;
    }

    public Node getSecondNode() {
        return this.secondNode;
    }

    public Node getNeighbor(Node current) {
        if(!(current.equals(firstNode) || current.equals(secondNode))){
            return null;
        }

        return (current.equals(firstNode)) ? secondNode : firstNode;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Link)) {
            return false;
        }

        Link link = (Link) other;

        return (link.getFirstNode().equals(this.firstNode) && link.getSecondNode().equals(this.secondNode)) ||
                (link.getFirstNode().equals(this.secondNode) && link.getSecondNode().equals(this.firstNode));
    }
}
