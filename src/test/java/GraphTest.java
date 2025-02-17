import DotAPI.Edge;
import DotAPI.Graph;
import DotAPI.Node;
import org.junit.jupiter.api.Test;

class GraphTest {

    @Test
    void emptyGraph(){
        //create empty graph
        Graph<String> graph = new Graph<String>();
        assert(graph.getNodes().isEmpty());
        assert(graph.getEdges().isEmpty());
    }

    @Test
    void node(){
        Node<String> node1 = new Node<String>("a");
        assert(node1.getValue().equals("a"));
        assert(node1.toString().equals("a"));
        assert(node1.equals(new Node<String>("a")));

        Node<String> node2 = new Node<String>("a");
        assert(node1.equals(node2));

        Node<String> node3 = new Node<String>("aa");
        assert(!node1.equals(node3));
    }

    //duplicate addition i.e. different nodes with same value, is possible right now
    /*
    @Test
    void duplicateAddition(){
        Graph<String> graph = new Graph<String>();

        Node<String> node1 = new Node<String>("a");
        graph.addNode(node1);

        Node<String> node2 = new Node<String>("a");
        assert(node1.equals(node2));

        assert (graph.getNodes().size() == 1);
        graph.addNode(node2);
        assert(graph.getNodes().size() == 1);

        Node<String> node3 = new Node<String>("aa");
        assert(!node1.equals(node3));
        graph.addNode(node3);
        assert(graph.getNodes().size() == 2);
    }
     */

    @Test
    void addNode() {
        Graph<String> graph = new Graph<String>();

        Node<String> node1 = new Node<String>("a");
        graph.addNode(node1);
        assert (graph.getNodes().contains(node1));
        assert (graph.getNodes().size() == 1);
        assert (graph.getEdges().isEmpty());
    }

    @Test
    void addEdge() {
        Graph<String> graph = new Graph<String>();
        Node<String> node1 = new Node<String>("a");
        graph.addNode(node1);
        assert (graph.getNodes().contains(node1));

        //create successor
        Node<String> node2 = new Node<String>("b");
        graph.addNode(node2);
        assert (graph.getNodes().contains(node2));
        assert (graph.getNodes().size() == 2);
        assert (graph.getEdges().isEmpty());

        //add Edge
        Edge<String> edge = new Edge<String>(node1, node2);
        graph.addEdge(edge);
        assert(graph.getEdges().contains(edge));
        assert(graph.getEdges().size() == 1);
    }

}
