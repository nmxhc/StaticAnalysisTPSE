package DotAPI;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents DOT graph structure
 */
public class Graph<T> {
    private Set<Node<T>> nodes = new HashSet<Node<T>>();
    private Set<Edge<T>> edges = new HashSet<Edge<T>>();

    /**
     * Creates new empty Graph
     */
    public Graph(){}


    /**
     * Add node
      * @param node to be added
     */
    public void addNode(Node<T> node){
        nodes.add(node);
    }

    /**
     * Adds Edge to edges with
     * @param origin node of edge
     * @param target node of edge
     */
    public void addEdge(Node<T> origin, Node<T> target){
        addEdge(new Edge<T>(origin, target));
    }

    /**
     * Adds Edge to edges only if equal edge is not already present, with
     * @param origin node
     * @param target node
     */
    public void addEdgeS(Node<T> origin, Node<T> target){
        addEdge(origin, target);
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
     * @return edges of Graph.
     */
    public Set<Edge<T>> getEdges(){
        return edges;
    }

    /**
     * @return set of all graph' nodes toString()
     */
    public Set<String> nodesToString(){
        Set<String> strings = new HashSet<>();
        for (Node<T> n : nodes){
            strings.add(n.toString());
        }
        return strings;
    }

    /**
     * @return set of all graph' edges toString()
     */
    public Set<String> edgesToString(){
        Set<String> strings = new HashSet<>();
        for (Edge<T> e : edges){
            strings.add(e.toString());
        }
        return strings;
    }

}
