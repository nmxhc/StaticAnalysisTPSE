package SootUp;

import DOT.API.Edge;
import DOT.API.Graph;
import DOT.API.Node;
import sootup.core.model.SootMethod;
import sootup.core.signatures.MethodSignature;
import sootup.core.views.AbstractView;
import sootup.core.types.Type;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.views.JavaView;

import java.nio.file.Paths;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public abstract class Analysis {
    private Set<String> seen = new HashSet<>();
    private Graph<String> callGraph = new Graph<>();
    protected AbstractView view;

    protected Analysis() {
        view = new JavaView(PathBasedAnalysisInputLocation.create(Paths.get("demo/"), null));
    }

    public Optional<Graph<String>> run(String className, String method) {
       return view.getClass(view.getIdentifierFactory().getClassType(className))
           .flatMap(c -> c.getMethodsByName(method).stream().findFirst())
           .map(methodToAnalyse -> {
               addMethod(methodToAnalyse);
               return callGraph;
           });
    }

    private void addMethod(SootMethod methodToAnalyse) {
        // don't process methods twice
        var name = methodToAnalyse.getName();
        if (!seen.add(name))
            return;

        var localEdges = new HashSet<String>();

        var methodInGraph = new Node<>(name);
        callGraph.addNode(methodInGraph);

        for (var stmt : Util.getStatements(methodToAnalyse)) {
            if (!stmt.containsInvokeExpr())
                continue;
            System.out.println("NEXT STMT");

            getSubMethods(stmt.getInvokeExpr().getMethodSignature())
                .forEach(method -> {
                    var methodName = method.getName();
                    // ignore existing edges
                    if (!localEdges.add(methodName))
                        return;

                    // add edge to graph
                    var edge = new Edge<>(methodInGraph, new Node<>(methodName));
                    System.out.println("Adding: " + edge);
                    callGraph.addEdge(edge);

                    // recurse
                    addMethod(method);
                });
        }
    }

    protected abstract Set<Type> getConstructedTypes();

    private Stream<? extends SootMethod> getSubMethods(MethodSignature signature) {
        var declClass = signature.getDeclClassType();
        var consTypes = getConstructedTypes();
        return Stream.concat(Stream.of(declClass), view.getTypeHierarchy().subtypesOf(declClass))
            .filter(sub -> consTypes == null || consTypes.contains(sub))
            .flatMap(sub -> view.getClass(sub).stream())
            .flatMap(sub -> sub.getMethod(signature.getSubSignature()).stream())
            .filter(method -> !method.isAbstract());
    }
}
