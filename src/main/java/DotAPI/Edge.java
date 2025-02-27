package DotAPI;

public class Edge<T> {

    private Node<T> originNode;
    private Node<T> targetNode;

    /**
     * Creates new Edge, accordingly setting
     * @param originNode
     * @param targetNode
     */
    public Edge(Node<T> originNode, Node<T> targetNode){
        this.originNode = originNode;
        this.targetNode = targetNode;
    }


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

    @Override
    public String toString() {
        return originNode.toString() + " -> " + targetNode.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Edge
                && this.originNode.equals(((Edge<?>) obj).getOriginNode())
                && this.targetNode.equals(((Edge<?>) obj).getTargetNode());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
