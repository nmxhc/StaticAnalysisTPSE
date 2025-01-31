package SootUp;

import DOT.API.Edge;
import DOT.API.Graph;
import DOT.API.Node;
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

    public static Optional<Graph<String>> CHA(String file, String method) {


        SootClass c = InternalUtil.loadClass(file);

        Set<String> methodNames = InternalUtil.getMethods(c).stream().map(SootClassMember::getName).collect(Collectors.toSet());
        if (!methodNames.contains(method)) {
            return Optional.empty();
        }

        // get the method that has the name we want
        SootMethod methodToAnalyse = InternalUtil.getMethods(c).stream().filter(m -> method.equals(m.getName())).toList().get(0);

        // from here on: method is present
        Graph<String> callGraph = new Graph<>();
        Node<String> methodInGraph = new Node<>(methodToAnalyse.getName());
        callGraph.addNode(new Node<>(methodToAnalyse.getName()));

        for (Stmt s : InternalUtil.getStatements(methodToAnalyse)) {
            if (s.containsInvokeExpr()) { // s is function call
                System.out.println("NEXT STMT");
                AbstractInvokeExpr callSite = s.getInvokeExpr();

                Edge<String> e = new Edge<>(methodInGraph, new Node<>(callSite.getMethodSignature().getName()));

                if (!callGraph.getEdges().stream().map(Edge::toString).collect(Collectors.toSet()).contains(e.toString())) {
                    System.out.println("Adding: " + e);
                    for (Edge<String> ed : callGraph.getEdges()) {
                        System.out.println(ed);
                    }
                    callGraph.addEdge(e);
                }
                if (!callGraph.getNodes().stream().map(Node::toString).collect(Collectors.toSet()).contains(e.getTargetNode().toString())) {
                    callGraph.addNode(e.getTargetNode());
                }

            }
        }

        return Optional.of(callGraph);
    }


    protected Set<Type> getConstructedTypes() {
        return null;
    }
}
