package DOT.API;

public class Edge<T> {

    private Node<T> originNode;
    private Node<T> targetNode;

    /**
     * Create empty Edge.
     */
    public Edge(){}

    /**
     * @return Node origin of Edge.
     */
    public Node<T> getOriginNode() {
        return originNode;
    }

    /**
     * @return Node target of Edge.
     */
    public Node<T> getTargetNode() {
        return targetNode;
    }

    /**
     * Set origin Node to
     * @param originNode
     */
    public void setOriginNode(Node<T> originNode) {
        this.originNode = originNode;
    }

    /**
     * Set target Node to
     * @param targetNode
     */
    public void setTargetNode(Node<T> targetNode) {
        this.targetNode = targetNode;
    }
}