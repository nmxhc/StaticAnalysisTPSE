package DOT.API;

import java.util.HashSet;
import java.util.Set;

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
     * Add
     * @param node to Graph.
     */
    public void addNode(Node<T> node){
        nodes.add(node);
    }

    /**
     * Add node with
      * @param value
     */
    public void addNode(T value){
        nodes.add(new Node<T>(value));
    }

    /**
     * Adds Edge to edges with
     * @param origin
     * @param target
     */
    public void addEdge(Node<T> origin, Node<T> target){
        Edge<T> edge = new Edge<T>();
        edge.setOriginNode(origin);
        edge.setTargetNode(target);
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
}
