package DOT.API;

import java.util.LinkedList;
import java.util.List;

public class GraphEquivalency {

    public static <T> GraphItem<T> missingAndRedundantNodes(Graph<T> refGraph, Graph<T> testGraph) {

        /* A List of 4 elements: missing nodes, missing edges, redundant nodes, redundant edges */

        GraphItem<T> graphItem = new GraphItem<>();

        for (Node<T> n : refGraph.getNodes()) {
            if (!testGraph.getNodes().contains(n)) {
                graphItem.missingNodes.add(n);
            }
        }

        for (Edge<T> e : refGraph.getEdges()) {
            if (!testGraph.getEdges().contains(e)) {
                graphItem.missingEdges.add(e);
            }
        }

        for (Node<T> n : testGraph.getNodes()) {
            if (!refGraph.getNodes().contains(n)) {
                graphItem.surplusNodes.add(n);
            }
        }

        for (Edge<T> e : testGraph.getEdges()) {
            if (!refGraph.getEdges().contains(e)) {
                graphItem.surplusEdges.add(e);
            }
        }

        return graphItem;
    }

    public static <T> boolean isEquivalent(Graph<T> refGraph, Graph<T> testGraph) {
        GraphItem<T> graphItem = missingAndRedundantNodes(refGraph, testGraph);

        return (graphItem.missingNodes.isEmpty() &&
                graphItem.missingEdges.isEmpty() &&
                graphItem.surplusNodes.isEmpty() &&
                graphItem.surplusEdges.isEmpty());
    }

}
