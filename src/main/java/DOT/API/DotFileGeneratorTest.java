package DOT.API;

import org.junit.jupiter.api.Test;

class DotFileGeneratorTest {

    @Test
    void emptyGraph(){
        Graph<String> graph = new Graph<String>();
        String s1 = DotFileGenerator.generateDotString(graph);
        String s0 = "digraph G {\n}\n";
        assert (s1.equals(s0));
    }

    @Test
    void addNode(){
        Graph<String> graph = new Graph<String>();
        Node<String> node1 = new Node<>("foo_n1");
        graph.addNode(node1);
        assert (graph.getNodes().contains(node1));
        String s1 = DotFileGenerator.generateDotString(graph);
        String s0 = "digraph G {\n foo_n1;\n }\n";
        //assert (s1.contains(s0));
    }

    @Test
    void stringOutput() {
        Graph<String> graph = new Graph<String>();

        Node<String> node1 = new Node<>("foo_n1");
        Node<String> node2 = new Node<>("bar_n2");
        Node<String> node3 = new Node<>("foobar_n3");

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);

        String s0 = "  foo_n1;\n";
        graph.addNode(node1);
        String s1 = DotFileGenerator.generateDotString(graph);
        assert (s1.contains(s0));


        graph.addNode(node2);
        graph.addNode(node3);

        String s2 = "digraph G {\n  foobar_n3;\n  bar_n2;\n  foo_n1;\n}\n";
        String s3 = DotFileGenerator.generateDotString(graph);
        assert (s3.contains(s2));
    }
}
