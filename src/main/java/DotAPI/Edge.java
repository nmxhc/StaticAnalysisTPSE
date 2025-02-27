package DotAPI;

/**
 * Edge in {@link Graph} structure;
 * Contains origin and target {@link Node} characterizing edge in graph.
 * @param <T> data type of Edge's Node Value
 */
public class Edge<T> {

    private final Node<T> originNode;
    private final Node<T> targetNode;

    /**
     * Creates new Edge, accordingly setting
     * @param originNode of edge
     * @param targetNode of edge
     */
    public Edge(Node<T> originNode, Node<T> targetNode){
        this.originNode = originNode;
        this.targetNode = targetNode;
    }


    /**
     * @return origin node of edge.
     */
    public Node<T> getOriginNode() {
        return originNode;
    }

    /**
     * @return target node of edge.
     */
    public Node<T> getTargetNode() {
        return targetNode;
    }

    /**
     * Check edges for equal content using node's values.
     * @param obj edge to be compared
     * @return true if edge's node's values are identical
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Edge
                && this.originNode.equals(((Edge<?>) obj).getOriginNode())
                && this.targetNode.equals(((Edge<?>) obj).getTargetNode());
    }

    /**
     * @return [originNode] -> [targetNode].
     */
    public String toString(){
        return originNode.toString() + " -> " + targetNode.toString();
    }

}