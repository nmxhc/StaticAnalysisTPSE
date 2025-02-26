package SootUp;

import DotAPI.Edge;
import DotAPI.Graph;
import DotAPI.Node;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.common.expr.JVirtualInvokeExpr;
import sootup.core.jimple.common.expr.JInterfaceInvokeExpr;
import sootup.core.model.SootMethod;
import sootup.core.signatures.MethodSignature;
import sootup.core.views.AbstractView;
import sootup.core.types.Type;
import sootup.java.bytecode.inputlocation.JrtFileSystemAnalysisInputLocation;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.views.JavaView;

import java.nio.file.Paths;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Super class for all analysis algorithms
 */
public abstract class Analysis {
    private Set<String> seen = new HashSet<>();
    private Graph<String> callGraph = new Graph<>();
    protected AbstractView view;

    /**
     * constructor
     */
    protected Analysis(String path) {
        var locations = new ArrayList<AnalysisInputLocation>();
        locations.add(PathBasedAnalysisInputLocation.create(Paths.get(path), null));
        // locations.add(new JrtFileSystemAnalysisInputLocation());
        view = new JavaView(locations);
    }

    /**
     * Executes the analysis
     * @param className class to analyse
     * @param method to analyse
     * @return
     */
    public Optional<Graph<String>> run(String className, String method) {
       return view.getClass(view.getIdentifierFactory().getClassType(className))
           .flatMap(c -> c.getMethodsByName(method).stream().findFirst())
           .map(methodToAnalyse -> {
               addMethod(methodToAnalyse);
               return callGraph;
           });
    }

    class MethodOrSignature {
        SootMethod method = null;
        MethodSignature sig = null;

        MethodOrSignature(SootMethod method) {
            this.method = method;
        }

        MethodOrSignature(MethodSignature sig) {
            this.sig = sig;
        }

        @Override
        public String toString() {
            return method != null ? method.toString() : sig.toString();
        }

        void addTo(Analysis analysis) {
            if (method != null)
                analysis.addMethod(method);
        }
    }

    private void addMethod(SootMethod methodToAnalyse) {
        // don't process methods twice
        var name = methodToAnalyse.toString();
        if (!seen.add(name))
            return;

        var localEdges = new HashSet<String>();

        var methodInGraph = new Node<>(name);
        callGraph.addNode(methodInGraph);

        if (!methodToAnalyse.hasBody())
            return;

        for (var stmt : methodToAnalyse.getBody().getStmts()) {
            if (!stmt.containsInvokeExpr())
                continue;

            var expr = stmt.getInvokeExpr();
            var sig = expr.getMethodSignature();

            Stream<MethodOrSignature> methods = null;

            // FIXME: consider JDynamicInvokeExpr ?
            if (expr instanceof JInterfaceInvokeExpr || expr instanceof JVirtualInvokeExpr) {
                var subs = getSubMethods(sig);
                if (subs != null)
                    methods = subs.map(method -> new MethodOrSignature(method));
            } else {
                var method = view.getMethod(sig);
                if (method.isPresent())
                    methods = Stream.of(new MethodOrSignature(method.get()));
            }

            if (methods == null)
                methods = Stream.of(new MethodOrSignature(sig));

            System.out.println("NEXT STMT");

            methods.forEach(method -> {
                var methodName = method.toString();
                // ignore existing edges
                if (!localEdges.add(methodName))
                    return;

                // add edge to graph
                var edge = new Edge<>(methodInGraph, new Node<>(methodName));
                System.out.println("Adding: " + edge);
                callGraph.addEdge(edge);

                // recurse
                method.addTo(this);
            });
        }
    }

    protected abstract Set<Type> getConstructedTypes();

    private Stream<? extends SootMethod> getSubMethods(MethodSignature signature) {
        var declClass = signature.getDeclClassType();
        var consTypes = getConstructedTypes();
        var hierarchy = view.getTypeHierarchy();

        if (!hierarchy.contains(declClass))
            return null;

        return Stream.concat(Stream.of(declClass), hierarchy.subtypesOf(declClass))
            .filter(sub -> consTypes == null || consTypes.contains(sub))
            .flatMap(sub -> view.getClass(sub).stream())
            .flatMap(sub -> sub.getMethod(signature.getSubSignature()).stream())
            .filter(method -> !method.isAbstract());
    }
}
