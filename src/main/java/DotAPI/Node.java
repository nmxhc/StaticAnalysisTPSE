package DotAPI;

/**
 * Node in {@link Graph} structure, characterized by value.
 * @param <T> data type of value
 */
public class Node<T> {
    private final T value;

    /**
     * Create new node with given
     * @param value of node
     */
    public Node(T value){
        this.value = value;
    }

    /**
     * @return value of this node.
     */
    public T getValue() {
        return value;
    }

    /**
     * @return value of node as String.
     */
    public String toString() {
        return value.toString();
    }

    /**
     * Check nodes for equal values
     * @param obj node to be compared
     * @return true if node's values are identical
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Node && this.getValue().equals(((Node<?>) obj).getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
