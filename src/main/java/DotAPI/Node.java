package DotAPI;

public class Node<T> {
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
