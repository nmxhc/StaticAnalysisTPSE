package DOT.API;

public class Node<T> implements GraphItem<T> {
    private T value;

    public Node(T value){
        this.value = value;
    }
    
    public T getValue() {
        return value;
    }


    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Node && this.toString().equals(((Node<?>) obj).toString());
    }
}
