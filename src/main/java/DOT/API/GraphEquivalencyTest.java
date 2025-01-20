package DOT.API;

import org.junit.jupiter.api.Test;

import static DOT.API.GraphEquivalency.isEquivalent;
import static DOT.API.GraphEquivalency.missingAndRedundantNodes;

class GraphEquivalencyTest {

    @Test
    void emptyGraph(){
        assert(isEquivalent(new Graph<>(), new Graph<>()));
    }

    @Test
    void equivalentGraph(){
        assert(isEquivalent(createBasicGraph(), createBasicGraph()));
    }

    @Test
    void basicGraph(){
        Graph<String> refGraph = new Graph<>();
        Node<String> node1 = new Node<String>("a");
        Node<String> node2 = new Node<String>("b");
        Node<String> node3 = new Node<String>("c");

        refGraph.addNode(node1);
        refGraph.addNode(node2);
        refGraph.addNode(node3);

        refGraph.addEdge(node1, node2);
        refGraph.addEdge(node1, node3);

        assert(isEquivalent(refGraph, createBasicGraph()));
    }

    static Graph<String> createBasicGraph(){
        Graph<String> graph = new Graph<>();
        Node<String> node1 = new Node<String>("a");
        Node<String> node2 = new Node<String>("b");
        Node<String> node3 = new Node<String>("c");

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);

        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);
        return graph;
    }
}
