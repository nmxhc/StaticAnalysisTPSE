package DOT.API;

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

    public String toString(){
        return originNode.toString() + " -> " + targetNode.toString();
    }

}