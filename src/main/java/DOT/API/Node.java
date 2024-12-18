package DOT.API;

public class Node<T> {
    private T value;

    /**
     * Create empty Node.
     */
    public Node(){};

    public Node(T value) {
        this.value = value;
    }

    /**
     * Create Node with
     * @param value
     */
    public Node(T value){
        this.value = value;
    }

    /**
     * Set value of Node to
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
     * @return value of Node as String.
     */
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Node && this.value.equals(((Node<?>) obj).getValue());
    }
}
