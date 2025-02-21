import AST.CodeStructure.Package;
import AST.CodeStructure.Util;
import DotAPI.DotFileGenerator;
import DotAPI.DotToHtmlGenerator;
import DotAPI.Graph;

public class Main {

    public static void main(String[] args) {

        Package p = Util.loadPackage("sources");
        Graph<String> graph = StudentImplementedCHAAnalysis.run(p);

        DotFileGenerator.generateDotString(graph);
    }

}
