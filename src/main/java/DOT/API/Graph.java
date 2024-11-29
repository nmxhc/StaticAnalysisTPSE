package DOT.API;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents DOT graph structure
 */
public class Graph {
    private Set<Node> nodes = new HashSet<Node>();

    /**
     * Creates new empty Graph
     */
    public Graph(){}

    /**
     * Creates new Graph containing only
     * @param root Node
     */
    public Graph(Node root){
        nodes.add(root);
    }

    public void addNode(Node node){
        nodes.add(node);
    }
}
