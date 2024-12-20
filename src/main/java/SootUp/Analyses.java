package SootUp;

import DOT.API.Edge;
import DOT.API.Graph;
import DOT.API.Node;
import sootup.core.model.SootMethod;
import sootup.core.signatures.MethodSignature;
import sootup.core.views.AbstractView;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.views.JavaView;

import java.nio.file.Paths;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class Analyses {

    private static class Context {
        private HashSet<String> seen = new HashSet<>();
        private Graph<String> callGraph = new Graph<>();
        private AbstractView view;
        private boolean rta;

        private Context(AbstractView argView, boolean argRta) {
            view = argView;
            rta = argRta;
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

        private Stream<SootMethod> getSubMethods(MethodSignature signature) {
            return Stream.concat(
                view.getMethod(signature).stream(),
                view.getTypeHierarchy()
                    .subtypesOf(signature.getDeclClassType())
                    .flatMap(sub -> view.getClass(sub).stream())
                    .flatMap(sub -> sub.getMethod(signature.getSubSignature()).stream())
            );
        }
    }

    private static Optional<Graph<String>> run(String className, String method, boolean rta) {
        if (rta)
            return Optional.empty(); // TODO: implement RTA

        var view = new JavaView(PathBasedAnalysisInputLocation.create(Paths.get("demo/"), null));

        return view.getClass(view.getIdentifierFactory().getClassType(className))
            .flatMap(c -> c.getMethodsByName(method).stream().findFirst())
            .map(methodToAnalyse -> {
                var ctx = new Context(view, rta);
                ctx.addMethod(methodToAnalyse);
                return ctx.callGraph;
            });
    }

    public static Optional<Graph<String>> CHA(String file, String method) {
        return run(file, method, false);
    }

    public static Optional<Graph<String>> RTA(String file, String method) {
        return run(file, method, true);
    }

}
