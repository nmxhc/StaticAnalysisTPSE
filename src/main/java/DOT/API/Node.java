package DOT.API;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private Set<Node> edgesTo = new HashSet<Node>();
    private final Node parent;

    /**
     * Create root Node without parent.
     */
    public Node(){
        parent = null;
    };

    /**
     * Create node with
     * @param parent node
     */
    public Node(Node parent){
        this.parent = parent;
    }

    /**
     * @return Node's parent
     */
    public Node getParent(){
        return parent;
    }

    /**
     * Add edge to Node.
     * @param edge to be added
     */
    public void addEdge(Node edge){
        edgesTo.add(edge);
    }

    /**
     * Get all Edges going away from Node as
     * @return Node[] array
     */
    public Node[] getEdgesTo(){
        return edgesTo.toArray(new Node[0]);
    }
}
