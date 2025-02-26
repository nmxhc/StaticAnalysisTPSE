import AST.CodeStructure.JavaClass;
import AST.CodeStructure.Method;
import AST.CodeStructure.Package;
import AST.CodeStructure.Util;
import DotAPI.DotFileGenerator;
import DotAPI.DotToHtmlGenerator;
import DotAPI.Graph;
import DotAPI.GraphEquivalency;
import ReferenceImplementations.CHAReference;
import SootUp.CHAAnalysis;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Package p = Util.loadPackage("src/test/sources");
        Graph<Method> graph = CHAReference.run(p, "Node", "expressionTester");

        //Graph<String> graph = new CHAAnalysis("foo").run("B", "<init>").get();

        // GraphEquivalency.missingAndRedundantNodes(graph, graph)

        String dotString = DotFileGenerator.generateDotString(graph);
        try {
            DotFileGenerator.writeDotFile("output.dot", dotString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DotToHtmlGenerator.embedDotInHtml("output.dot", "output.html", "name");
    }



}
