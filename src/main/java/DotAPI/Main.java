package DotAPI;

import AST.CodeStructure.Method;
import AST.CodeStructure.Util;
import ReferenceImplementations.CHAReference;
import ReferenceImplementations.RTAReference;
import SootUp.CHAAnalysis;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Graph<Method> g = CHAReference.run(Util.loadPackage("demo"),"ComplexTest", "main");
        GraphItem<Method> g1 = CHAReference.compare(Util.loadPackage("demo"),"ComplexTest", "main",
                RTAReference.run(Util.loadPackage("demo"),"ComplexTest", "main")) ;
        System.out.println("\n\n\nredundant Edges:");
        for (Edge<Method> re : g1.redundantEdges){
            System.out.println(re.toString());
        }
        System.out.println("\nmissing Edges:");
        for (Edge<Method> me : g1.missingEdges){
            System.out.println(me.toString());
        }


//        String s = DotFileGenerator.generateDotString(g);
//
//        try {
//            DotFileGenerator.writeDotFile("output.dot", s);
//        } catch (Exception e) {
//            System.out.printf("Error writing file: %s\n", e.getMessage());
//        }
//
//        DotToHtmlGenerator.embedDotFileInHtml("output.dot", "basicTests/HtmlTest.html","graph");
    }

}
