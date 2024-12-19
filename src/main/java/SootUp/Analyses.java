package SootUp;

import DOT.API.Edge;
import DOT.API.Graph;
import DOT.API.Node;
import com.googlecode.dex2jar.tools.BaseCmd;
import org.eclipse.jdt.internal.core.search.indexing.AbstractIndexer;
import sootup.core.jimple.basic.Immediate;
import sootup.core.jimple.common.expr.AbstractInvokeExpr;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootClass;
import sootup.core.model.SootClassMember;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Analyses {


    public static Optional<Graph<String>> CHA(String file, String method) {


        Optional<SootClass> wrappedClass = Util.loadClass(file);

        if (wrappedClass.isEmpty()) {
            return Optional.empty();
        }

        SootClass c = wrappedClass.get();

        Set<String> methodNames = Util.getMethods(c).stream().map(SootClassMember::getName).collect(Collectors.toSet());
        if (!methodNames.contains(method)) {
            return Optional.empty();
        }

        // get the method that has the name we want
        SootMethod methodToAnalyse = Util.getMethods(c).stream().filter(m -> method.equals(m.getName())).toList().get(0);

        // from here on: method is present
        Graph<String> callGraph = new Graph<>();
        Node<String> methodInGraph = new Node<>(methodToAnalyse.getName());
        callGraph.addNode(new Node<>(methodToAnalyse.getName()));

        for (Stmt s : Util.getStatements(methodToAnalyse)) {
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

    public static Optional<Graph<String>> RTA(String file) {
        return Optional.empty();
    }

}
