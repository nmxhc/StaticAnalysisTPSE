package DOT.API;

public class Main {

    public static void main(String[] args) {

        Graph<String> g = new Graph<String>();

        Node<String> n1 = new Node<>("foo_n1");
        Node<String> n2 = new Node<>("bar_n2");
        Node<String> n3 = new Node<>("foobar_n3");

        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);

        g.addEdge(n1, n2);
        g.addEdge(n3, n1);

        String s = DotFileGenerator.generateDotString(g);
        try {
            DotFileGenerator.writeDotFile("output.dot", s);
        } catch (Exception e) {
            System.out.printf("Error writing file: %s\n", e.getMessage());
        }
    }

}
