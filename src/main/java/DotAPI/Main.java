package DotAPI;

import SootUp.SootUpCHAnalysis;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Optional<Graph<String>> g = new SootUpCHAnalysis("simple").run("Test", "test");
        if (g.isEmpty()) {System.out.print("Error finding file" ); return;}
        String s = DotFileGenerator.generateDotString(g.get());

        try {
            DotFileGenerator.writeDotFile("output.dot", s);
        } catch (Exception e) {
            System.out.printf("Error writing file: %s\n", e.getMessage());
        }

        //DotToHtmlGenerator.embedDotInHtml("basicTests/basicGraph.dot", "basicTests/HtmlTest.html");
    }

}
