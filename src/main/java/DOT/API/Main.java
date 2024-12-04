package DOT.API;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Graph<String> g = new Graph<String>();

        Node<String> n1 = new Node<>();
        Node<String> n2 = new Node<>();
        Node<String> n3 = new Node<>();

        n1.setValue("foo_n1");
        n2.setValue("bar_n2");
        n3.setValue("foobar_n3");

        n1.addSuccessor(n2);
        n1.addSuccessor(n1);

        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);

        String s = DotFileGenerator.generateDotString(g);
        try {
            DotFileGenerator.writeDotFile("output.dot", s);
        } catch (Exception e) {
            System.out.println("oh no");
        }
    }

}
