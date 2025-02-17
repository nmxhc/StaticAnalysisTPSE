package DotAPI;

import java.util.LinkedList;

public class GraphItem<T> {
    public LinkedList<Node<T>> missingNodes = new LinkedList<>();
    public LinkedList<Node<T>> redundantNodes = new LinkedList<>();

    public LinkedList<Edge<T>> missingEdges = new LinkedList<>();
    public LinkedList<Edge<T>> redundantEdges = new LinkedList<>();

    public GraphItem(){}
}
