package DOT.API;

import java.util.LinkedList;
import java.util.List;

public class GraphEquivalency {

    public static <T> List<List<GraphItem<T>>> missingAndRedundantNodes(Graph<T> refGraph, Graph<T> testGraph) {

        /* A List of 4 elements: missing nodes, missing edges, redundant nodes, redundant edges */
        List<List<GraphItem<T>>> missingOrTooMany = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            missingOrTooMany.add(new LinkedList<>());
        }

        for (Node<T> n : refGraph.getNodes()) {
            if (!testGraph.getNodes().contains(n)) {
                missingOrTooMany.get(0).add(n);
            }
        }

        for (Edge<T> e : refGraph.getEdges()) {
            if (!testGraph.getEdges().contains(e)) {
                missingOrTooMany.get(1).add(e);
            }
        }

        for (Node<T> n : testGraph.getNodes()) {
            if (!refGraph.getNodes().contains(n)) {
                missingOrTooMany.get(2).add(n);
            }
        }

        return missingOrTooMany;
    }

    public static <T> boolean isEquivalent(Graph<T> refGraph, Graph<T> testGraph) {
        for (List<GraphItem<T>> l : missingAndRedundantNodes(refGraph, testGraph)) {
            if (!l.isEmpty()) {
                return false;
            }
        }

        return true;
    }

}