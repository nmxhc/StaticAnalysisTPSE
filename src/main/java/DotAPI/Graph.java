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
      * @param node
     */
    public void addNode(Node<T> node){
        nodes.add(node);
    }

    /**
     * Adds Edge to edges with
     * @param origin
     * @param target
     */
    public void addEdge(Node<T> origin, Node<T> target){
        Edge<T> edge = new Edge<T>(origin, target);
        edges.add(edge);
    }

    /**
     * Adds Edge to edges with
     * @param origin
     * @param target
     */
    public void addEdgeS(Node<T> origin, Node<T> target){
        Edge<T> edge = new Edge<T>(origin, target);
        if (!getEdges().stream().map(Edge::toString).collect(Collectors.toSet()).contains(edge.toString()))
            edges.add(edge);
        if (!getNodes().stream().map(Node::toString).collect(Collectors.toSet()).contains(edge.getTargetNode().toString())) {
            addNode(edge.getTargetNode());
        }
    }

    /**
     * Adds to Edges:
     * @param edge
     */
    public void addEdge(Edge<T> edge){
        edges.add(edge);
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
