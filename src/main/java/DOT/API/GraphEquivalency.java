package DOT.API;

public class GraphEquivalency {

    public static void main(String[] args){

    }

    public static <T> String nodeCount(Graph<T> refGraph, Graph<T> testGraph){
        if(refGraph.getNodes().size() < testGraph.getNodes().size()){
            return "too many nodes";
        }else if (refGraph.getNodes().size() > testGraph.getNodes().size()){
            return "too few nodes";
        }
        return null;
    }

    public static <T> String edges(Graph<T> refGraph, Graph<T> testGraph){
        StringBuilder result = new StringBuilder();
        for(Edge<T> edge : refGraph.getEdges()){
            if(!testGraph.getEdges().contains(edge)){
                result.append("Missing Edge: " + edge.toString() + "\n");
            }
        }
        return result.toString();
    }

}