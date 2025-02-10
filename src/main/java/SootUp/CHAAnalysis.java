package SootUp;

import DOT.API.Edge;
import DOT.API.Graph;
import DOT.API.Node;
import sootup.core.jimple.common.expr.AbstractInvokeExpr;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootClass;
import sootup.core.model.SootClassMember;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
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
                AbstractInvokeExpr callSite = s.getInvokeExpr();

                System.out.println("NEXT STMT");

                Edge<String> e = GetMethodStringEdge(callSite, methodInGraph,callGraph);

                AddEdge(callGraph, e);

            }
        }

        // Return the constructed call graph wrapped in an Optional
        return Optional.of(callGraph);
    }



    private static Edge<String> GetMethodStringEdge(AbstractInvokeExpr callSite, Node<String> methodInGraph, Graph<String> callGraph) {

        // Create node for the call graph for the function call
        Node<String> n = new Node<>(callSite.getMethodSignature().getDeclClassType().getClassName()
                    + "_" + callSite.getMethodSignature().getName().replace("<init>", ""));

        ClassType c = callSite.getMethodSignature().getDeclClassType();
        Class<?> superClass = c.getClass().getSuperclass(); //does not work as intended

        System.out.println("SuperClass: " + superClass.getName());
        if (!superClass.equals(Object.class)) {
            methodInGraph = new Node<>(c.getClassName());
//            AddSuperClassEdge(c.getClass(), callGraph);
        }

        return new Edge<>(methodInGraph, n);
    }

    private static void AddSuperClassEdge(Class<?> c, Graph<String> callGraph) {
        Class<?> superClass = c.getSuperclass();
        if (!superClass.getSuperclass().equals(Object.class)){
            AddSuperClassEdge(superClass.getSuperclass(), callGraph);
        }

        Node<String> n1 = new Node<>(c.getName());
        Node<String> n2 = new Node<>(superClass.getName());
        Edge<String> e = new Edge<>(n1, n2);
        AddEdge(callGraph, e);

    }

    private static void AddEdge(Graph<String> callGraph, Edge<String> e) {
        // Ensure the edge is not already present in the graph before adding it
        if (!callGraph.getEdges().stream().map(Edge::toString).collect(Collectors.toSet()).contains(e.toString())) {
            System.out.println("\nAdding: " + e);
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


    /**
     * Retrieves the set of constructed types.
     * @return A set of constructed types.
     */
    protected Set<Type> getConstructedTypes() {
        return null;
    }
}
