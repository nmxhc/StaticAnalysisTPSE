package DOT.API;

import java.util.HashSet;
import java.util.Set;

public class Node<T> {
    private T value;

    /**
     * Create root Node without parent.
     */
    public Node(){

    };


    public void setValue(T val) {
        value = val;
    }

    public T getValue() {
        return value;
    }


    /**
     *
     * @return
     */
    public String toString() {
        return value.toString();
    }
}
