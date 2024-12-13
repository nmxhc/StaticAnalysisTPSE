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

    public void addNode(Node<T> node){
        nodes.add(node);
    }

    public Set<Node<T>> getNodes() {
        return nodes;
    }

    public Set<Edge<T>> getEdges(){
        return edges;
    }
}
