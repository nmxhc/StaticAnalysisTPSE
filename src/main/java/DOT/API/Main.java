package DOT.API;

import SootUp.Analysis;
import SootUp.CHAAnalysis;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<Graph<String>> og = CHAAnalysis.CHA("ComplexTest", "main");

        if (og.isEmpty()) {
            throw new RuntimeException("oononono");
        }

        Graph<String> g = og.get();

        String s = DotFileGenerator.generateDotString(g);
        try {
            DotFileGenerator.writeDotFile("output.dot", s);
        } catch (Exception e) {
            System.out.printf("Error writing file: %s\n", e.getMessage());
        }
    }

}
