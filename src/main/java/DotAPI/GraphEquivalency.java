package DotAPI;

/**
 * Helper class for checking two {@link Graph} are identical.
 */
public class GraphEquivalency {

    /**
     * Compares two {@link Graph},
     * returning helper object {@link GraphItem} containing list of each missing and redundant nodes and edges
     * of testGraph relative to refGraph.
     * @param refGraph reference graph for comparison
     * @param testGraph graph to be checked using refGraph
     * @return {@link GraphItem} containing lists indicating testGraph's correctness
     * @param <T> data type of graphs
     */
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

    /**
     * Directly comparing two {@link Graph}
     * @param refGraph reference graph for comparison
     * @param testGraph graph to be checked using refGraph
     * @return true if graphs contain nodes and edges of identical content
     * @param <T> data type of graphs
     */
    public static <T> boolean isEquivalent(Graph<T> refGraph, Graph<T> testGraph) {
        GraphItem<T> graphItem = missingAndRedundantNodes(refGraph, testGraph);

        return (graphItem.missingNodes.isEmpty() &&
                graphItem.missingEdges.isEmpty() &&
                graphItem.redundantNodes.isEmpty() &&
                graphItem.redundantEdges.isEmpty());
    }

    /**
     * Compares two {@link Graph},
     * returning String describing differences in content of graphs
     * @param refGraph reference graph for comparison
     * @param testGraph graph to be checked using refGraph
     * @return String elaborating on content equivalency of the graphs
     * @param <T> data type of graphs
     */
    public static <T> String differenceToString(Graph<T> refGraph, Graph<T> testGraph){
        GraphItem<T> graphItem = missingAndRedundantNodes(refGraph, testGraph);
        StringBuilder resString = new StringBuilder();

        if(isEquivalent(refGraph, testGraph)){
            return refGraph + " and " + testGraph + " are equivalent.";
        }

        resString.append(refGraph).append(" and ").append(testGraph).append(" are not equivalent;\n");

        if(!graphItem.missingNodes.isEmpty()){
            resString.append("The missing nodes are:\n");
            for(Node<T> n : graphItem.missingNodes){
                resString.append("  ").append(n.toString()).append(";\n");
            }
        } else if (!graphItem.redundantNodes.isEmpty()){
            resString.append("The redundant nodes are:\n");
            for(Node<T> n : graphItem.redundantNodes){
                resString.append("  ").append(n.toString()).append(";\n");
            }
        }

        if(!graphItem.missingEdges.isEmpty()){
            resString.append("The missing edges are:\n");
            for(Edge<T> e : graphItem.missingEdges){
                resString.append("  ").append(e.toString()).append(";\n");
            }
        } else if (!graphItem.redundantEdges.isEmpty()){
            resString.append("The redundant edges are:\n");
            for(Edge<T> e : graphItem.redundantEdges){
                resString.append("  ").append(e.toString()).append(";\n");
            }
        }

        return resString.toString();
    }

}
