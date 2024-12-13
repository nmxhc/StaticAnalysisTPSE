package DOT.API;

public class Edge<T> {

    private Node<T> fromNode;
    private Node<T> toNode;

    public Edge(){}

    public Node<T> getFromNode() {
        return fromNode;
    }

    public Node<T> getToNode() {
        return toNode;
    }

    public void setFromNode(Node<T> fromNode) {
        this.fromNode = fromNode;
    }

    public void setToNode(Node<T> toNode) {
        this.toNode = toNode;
    }
}