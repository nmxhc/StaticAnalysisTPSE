package DotAPI;

import java.util.LinkedList;

/**
 * Helper object for {@link GraphEquivalency}
 * @param <T> data type of graphs compared.
 */
public class GraphItem<T> {

    /**
     * Create new empty GraphItem.
     */
    public GraphItem(){}

    /**
     * List of nodes missing in testGraph relative to refGraph.
     */
    public LinkedList<Node<T>> missingNodes = new LinkedList<>();
    /**
     * List of nodes redundant in testGraph relative to refGraph.
     */
    public LinkedList<Node<T>> redundantNodes = new LinkedList<>();

    /**
     * List of edges missing in testGraph relative to refGraph.
     */
    public LinkedList<Edge<T>> missingEdges = new LinkedList<>();
    /**
     * List of nodes redundant in testGraph relative to refGraph.
     */
    public LinkedList<Edge<T>> redundantEdges = new LinkedList<>();

}
