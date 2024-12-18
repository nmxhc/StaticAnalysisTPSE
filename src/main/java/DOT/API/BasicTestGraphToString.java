package DOT.API;

import org.junit.jupiter.api.Test;

class BasicTestGraphToString {
    @Test
    void stringOutput() {
        Graph<String> g = new Graph<String>();

        Node<String> n1 = new Node<>();
        Node<String> n2 = new Node<>();
        Node<String> n3 = new Node<>();

        String nn1 = "foo_n1";
        String nn2 = "bar_n2";
        String nn3 = "foobar_n3";

        n2.setValue(nn2);
        n3.setValue(nn3);

        n1.setValue(nn1);
        g.addNode(n1);

        String s0 = "digraph G {\n  "+ nn1+ ";\n}";
        String s1 = DotFileGenerator.generateDotString(g);
        System.out.println(s1);
        assert (s1.contains(s0));
        g.addNode(n1);
        assert (s1.contains(s0));


        g.addNode(n2);
        g.addNode(n3);

        String s2 = "digraph G {\n  "+ nn1 + ";\n  "+nn2+";\n  "+nn3+";\n}";
        String s3 = DotFileGenerator.generateDotString(g);
        System.out.println(s3);
        assert (s3.contains(s2));


    }
}
