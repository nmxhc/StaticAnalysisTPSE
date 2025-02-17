package SootUp;

import DotAPI.Edge;
import DotAPI.Graph;
import DotAPI.Node;
import sootup.core.jimple.common.expr.AbstractInvokeExpr;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootClass;
import sootup.core.model.SootClassMember;
import sootup.core.model.SootMethod;
import sootup.core.types.Type;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CHAAnalysis extends Analysis {
    public CHAAnalysis() {
        super();
    }


    /**
     * Performs Class Hierarchy Analysis (CHA) on the specified method in the given class file.
     * @param file The class file to analyze.
     * @param method The method name to target for analysis.
     * @return An Optional containing the call graph if the method is found, or an empty Optional otherwise.
     */
    public static Optional<Graph<String>> CHA(String file, String method) {

        // Load the class from the provided file
        SootClass c = InternalUtil.loadClass(file);

        // Extract all method names in the class
        Set<String> methodNames = InternalUtil.getMethods(c).stream().map(SootClassMember::getName).collect(Collectors.toSet());
        if (!methodNames.contains(method)) {
            // Return empty Optional if the method is not found
            return Optional.empty();
        }

        // Retrieve the method to be analyzed
        SootMethod methodToAnalyse = InternalUtil.getMethods(c).stream().filter(m -> method.equals(m.getName())).toList().get(0);

        // Initialize a call graph to represent method calls
        Graph<String> callGraph = new Graph<>();
        Node<String> methodInGraph = new Node<>(methodToAnalyse.getName());
        callGraph.addNode(new Node<>(methodToAnalyse.getName()));

        // Analyze each statement in the target method
        for (Stmt s : InternalUtil.getStatements(methodToAnalyse)) {
            if (s.containsInvokeExpr()) { // s is function call
                System.out.println("NEXT STMT");
                AbstractInvokeExpr callSite = s.getInvokeExpr();

                // Create an edge in the call graph for the function call
                Edge<String> e = new Edge<>(methodInGraph, new Node<>(callSite.getMethodSignature().getName()));

                // Ensure the edge is not already present in the graph before adding it
                if (!callGraph.getEdges().stream().map(Edge::toString).collect(Collectors.toSet()).contains(e.toString())) {
                    System.out.println("Adding: " + e);
                    for (Edge<String> ed : callGraph.getEdges()) {
                        System.out.println(ed);
                    }
                    callGraph.addEdge(e);
                }

                // Ensure the target node is added if not already present
                if (!callGraph.getNodes().stream().map(Node::toString).collect(Collectors.toSet()).contains(e.getTargetNode().toString())) {
                    callGraph.addNode(e.getTargetNode());
                }

            }
        }

        // Return the constructed call graph wrapped in an Optional
        return Optional.of(callGraph);
    }

    /**
     * Retrieves the set of constructed types.
     * @return A set of constructed types.
     */
    protected Set<Type> getConstructedTypes() {
        return null;
    }
}
