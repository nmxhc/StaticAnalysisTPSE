package DOT.API;

import java.util.HashSet;
import java.util.Set;

public class Node<T> {
    private Set<Node<T>> successors = new HashSet<Node<T>>();
    private Set<Node<T>> predecessors = new HashSet<Node<T>>();
    private T value; //Node's name, indicating the method described

    /**
     * Create root Node without parent.
     */
    public Node(){

    };

    /**
     * Create node with
     * @param newPredecessor node
     */
    public void addPredecessor(Node<T> newPredecessor){
        this.predecessors.add(newPredecessor);
    }

    public void setValue(T val) {
        value = val;
    }

    public T getValue() {
        return value;
    }

    /**
     * @return Node's parent
     */
    public Set<Node<T>> getPredecessors(){
        return predecessors;
    }

    /**
     * Add edge to Node.
     * @param newSuccessor to be added
     */
    public void addSuccessor(Node<T> newSuccessor){
        successors.add(newSuccessor);
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
