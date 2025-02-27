import DotAPI.DotFileGenerator;
import DotAPI.Graph;
import DotAPI.Node;
import org.junit.jupiter.api.Test;

class BasicGraphToStringTest {
    @Test
    void stringOutput() {
        Graph<String> g = new Graph<String>();


        Node<String> n1 = new Node<>("foo_n1");
        Node<String> n2 = new Node<>("bar_n2");
        Node<String> n3 = new Node<>("foobar_n3");

        g.addNode(n1);


        String s0 = "digraph G {\n  "+ "\"foo_n1\"" + ";\n}";
        String s1 = DotFileGenerator.generateDotString(g);
        System.out.println(s1);
        assert (s1.contains(s0));
        g.addNode(n1);
        assert (s1.contains(s0));


        g.addNode(n2);
        g.addNode(n3);

        String s3 = DotFileGenerator.generateDotString(g);
        System.out.println(s3);
        assert (s3.startsWith("digraph G {\n"));
        assert (s3.contains("\n  \"foo_n1\";\n"));
        assert (s3.contains("\n  \"bar_n2\";\n"));
        assert (s3.contains("\n  \"foobar_n3\";\n"));
        assert (s3.endsWith("\n}\n"));


    }
}
