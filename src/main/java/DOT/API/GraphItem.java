package DOT.API;

import java.util.LinkedList;

public class GraphItem<T> {
    LinkedList<Node<T>> missingNodes = new LinkedList<>();
    LinkedList<Node<T>> redundantNodes = new LinkedList<>();

    LinkedList<Edge<T>> missingEdges = new LinkedList<>();
    LinkedList<Edge<T>> redundantEdges = new LinkedList<>();

    public GraphItem(){}
}
