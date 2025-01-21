package DOT.API;

public class GraphEquivalency {

    public static <T> GraphItem<T> missingAndRedundantNodes(Graph<T> refGraph, Graph<T> testGraph) {

        GraphItem<T> graphItem = new GraphItem<>();

        for (Node<T> n : refGraph.getNodes()) {
            if (!testGraph.nodesToString().contains(n.toString())) {
                graphItem.missingNodes.add(n);
            }
        }

        for (Edge<T> e : refGraph.getEdges()) {
            if (!testGraph.edgesToString().contains(e.toString())) {
                graphItem.missingEdges.add(e);
            }
        }

        for (Node<T> n : testGraph.getNodes()) {
            if (!refGraph.nodesToString().contains(n.toString())) {
                graphItem.redundantNodes.add(n);
            }
        }

        for (Edge<T> e : testGraph.getEdges()) {
            if (!refGraph.edgesToString().contains(e.toString())) {
                graphItem.redundantEdges.add(e);
            }
        }

        return graphItem;
    }

    public static <T> boolean isEquivalent(Graph<T> refGraph, Graph<T> testGraph) {
        GraphItem<T> graphItem = missingAndRedundantNodes(refGraph, testGraph);

        return (graphItem.missingNodes.isEmpty() &&
                graphItem.missingEdges.isEmpty() &&
                graphItem.redundantNodes.isEmpty() &&
                graphItem.redundantEdges.isEmpty());
    }

}
