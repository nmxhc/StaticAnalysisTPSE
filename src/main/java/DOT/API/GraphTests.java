package DOT.API;

import org.junit.jupiter.api.Test;

class GraphTests {

    @Test
    void createBasic(){
        //create empty graph
        Graph<String> graph = new Graph<String>();
        assert(graph.getNodes().isEmpty());

        //add node
        Node<String> node1 = new Node<String>();
        node1.setValue("a");
        assert(node1.getValue().equals("a"));
        graph.addNode(node1);

        assert(graph.getNodes().contains(node1));
        assert(node1.getPredecessors().isEmpty());

        //create successor
        Node<String> node2 = new Node<String>();
        node2.setValue("b");
        graph.addNode(node2);
        assert(graph.getNodes().contains(node1));
        assert(graph.getNodes().contains(node2));
        //set as successor
        assert(node1.getSuccessors().isEmpty());
        node1.addSuccessor(node2);
        assert(node1.getSuccessors().contains(node2));
    }

}
