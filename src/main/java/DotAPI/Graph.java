package DotAPI;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Graph structure for recording static analysis results to be rendered into DOT graphs,
 * encompassing {@link Node} and {@link Edge}.
 */
public class Graph<T> {
    private final Set<Node<T>> nodes = new HashSet<Node<T>>();
    private final Set<Edge<T>> edges = new HashSet<Edge<T>>();

    /**
     * Creates new empty Graph.
     */
    public Graph(){}


    /**
     * Add node to graph
      * @param node to be added.
     */
    public void addNode(Node<T> node){
        nodes.add(node);
    }

    /**
     * Adds Edge to graph with
     * @param origin node of edge
     * @param target node of edge
     */
    public void addEdge(Node<T> origin, Node<T> target){
        addEdge(new Edge<T>(origin, target));
    }

    /**
     * Adds given edge to edges:
     * @param edge to be added
     */
    public void addEdge(Edge<T> edge){
        edges.add(edge);
        addNode(edge.getOriginNode());
        addNode(edge.getTargetNode());
    }

    /**
     * @return nodes of Graph.
     */
    public Set<Node<T>> getNodes() {
        return nodes;
    }

    /**
     * @return edges of Graph.
     */
    public Set<Edge<T>> getEdges(){
        return edges;
    }

    /**
     * Find the first Node in this graph with the given value.
     * @param value of node to be found
     * @return node with given value
     */
    public Node<T> findNode(T value){
        return nodes.stream()
                .filter(tNode -> tNode.getValue().equals(value)).findFirst().orElse(null);
    }

    /**
     * Find the first Edge in this graph with the given origin and target value.
     * @param originNodeValue value of the origin node of the edge to be found
     * @param targetNodeValue value of the target node of the edge to be found
     * @return edge with the given origin and target values
     */
    public Edge<T> findEdge(T originNodeValue, T targetNodeValue){
        return edges.stream().filter(edge -> edge.getOriginNode().getValue() == originNodeValue
        && edge.getTargetNode().getValue() == targetNodeValue).findFirst().orElse(null);
    }

    /**
     * @return set of all graph's nodes toString().
     */
    public Set<String> nodesToString(){
        Set<String> strings = new HashSet<>();
        for (Node<T> n : nodes){
            strings.add(n.toString());
        }
        return strings;
    }

    /**
     * @return set of all graph's edges toString().
     */
    public Set<String> edgesToString(){
        Set<String> strings = new HashSet<>();
        for (Edge<T> e : edges){
            strings.add(e.toString());
        }
        return strings;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Graph g) return getNodes().equals(g.getNodes()) && getEdges().equals(g.getEdges());
        return false;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(getNodes(), getEdges());
    }
}
