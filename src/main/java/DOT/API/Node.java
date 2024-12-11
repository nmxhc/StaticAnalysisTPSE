package DOT.API;

import java.util.HashSet;
import java.util.Set;

public class Node<T> {
    private Set<Node<T>> successors = new HashSet<Node<T>>();
    private Set<Node<T>> predecessors = new HashSet<Node<T>>();
    private T value; //Node's name, indicating the method described

    /**
     * Create empty node.
     */
    public Node(){};

    /**
     * Set Node's value to
     * @param value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * @return value of Node.
     */
    public T getValue() {
        return value;
    }

    /**
     * @return Node's parents.
     */
    public Set<Node<T>> getPredecessors(){
        return predecessors;
    }

    /**
     * Add node
     * @param newPredecessor
     * to Predecessors.
     */
    protected void addPredecessor(Node<T> newPredecessor){
        this.predecessors.add(newPredecessor);
    }

    /**
     * Add successor i.e. edge to Node,
     * adding itself to newSuccessor's predecessors.
     * @param newSuccessor to be added
     */
    public void addSuccessor(Node<T> newSuccessor){
        successors.add(newSuccessor);
        newSuccessor.addPredecessor(this);
    }

    public String toString() {
        return value.toString();
    }

    /**
     * Get all Edges going away from Node as
     * @return Node[] array
     */
    public Set<Node<T>> getSuccessors(){
        return successors;
    }
}
