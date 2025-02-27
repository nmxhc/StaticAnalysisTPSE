import DotAPI.Edge;
import DotAPI.Graph;
import DotAPI.Node;
import org.junit.jupiter.api.Test;

import static DotAPI.GraphEquivalency.isEquivalent;
import static DotAPI.GraphEquivalency.missingAndRedundantNodes;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    void addSameNode(){
        Graph<String> graph1 = createBasicGraph();
        Graph<String> graph2 = createBasicGraph();

        graph1.addNode(new Node<>("abc"));
        graph2.addNode(new Node<>("abc"));

        assert(isEquivalent(graph1, graph2));
    }

    @Test
    void basicGraph(){
        Graph<String> refGraph = new Graph<>();
        Node<String> node1 = new Node<String>("a");
        Node<String> node2 = new Node<String>("b");
        Node<String> node3 = new Node<String>("c");

        refGraph.addNode(node2);
        refGraph.addNode(node3);
        refGraph.addNode(node1);

        refGraph.addEdge(node1, node3);
        refGraph.addEdge(node1, node2);

        assert(isEquivalent(refGraph, createBasicGraph()));
    }

    @Test
    void redundantEdge(){
        Graph<String> graph = new Graph<>();
        Node<String> node1 = new Node<String>("a");
        Node<String> node2 = new Node<String>("b");
        Node<String> node3 = new Node<String>("c");

        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node1);

        graph.addEdge(node1, node3);
        graph.addEdge(node1, node2);

        Edge<String> edge = new Edge<>(node2, node1);
        graph.addEdge(edge);

        assertFalse(isEquivalent(createBasicGraph(), graph));
        assert(missingAndRedundantNodes(createBasicGraph(), graph)
                .redundantEdges.contains(edge));
    }

    @Test
    void missingEdge(){
        Graph<String> graph = new Graph<>();
        Node<String> node1 = new Node<String>("a");
        Node<String> node2 = new Node<String>("b");
        Node<String> node3 = new Node<String>("c");

        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node1);

        graph.addEdge(node1, node3);

        assertFalse(isEquivalent(createBasicGraph(), graph));
    }

    @Test
    void redundantNode(){
        Graph<String> graph = createBasicGraph();
        Node<String> node = new Node<>("red");
        graph.addNode(node);
        assertFalse(isEquivalent(createBasicGraph(), graph));
        assert(missingAndRedundantNodes(createBasicGraph(), graph)
                .redundantNodes.contains(node));
    }

    @Test
    void missingNode(){
        Graph<String> graph = new Graph<>();
        Node<String> node1 = new Node<String>("a");
        Node<String> node2 = new Node<String>("b");

        graph.addNode(node1);
        graph.addNode(node2);

        graph.addEdge(node1, node2);
        graph.addEdge(node2, node1);
        assertFalse(isEquivalent(createBasicGraph(), graph));
    }





    /**
     * helper method
     */
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
