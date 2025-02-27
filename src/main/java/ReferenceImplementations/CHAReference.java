package ReferenceImplementations;

import AST.CodeStructure.BasicBlock;
import AST.CodeStructure.JavaClass;
import AST.CodeStructure.Method;
import AST.Expressions.CallExpression;
import AST.Expressions.Local;
import AST.Statements.CallStatement;
import AST.Statements.AssignStatement;
import AST.Types.RefType;
import DotAPI.Edge;
import DotAPI.Graph;

import java.util.*;
import java.util.stream.Stream;

import AST.CodeStructure.Package;
import DotAPI.Node;

public class CHAReference {

    private static Graph<RefType> hierarchy;

    public static Graph<Method> run(Package pkg, String className, String methodName) {

        hierarchy = createTypeHierarchy(pkg);

        var startMethod = pkg.getClassByName(className).getMethodByName(methodName).get();

        Graph<Method> graph = new Graph<>();
        graph.addNode(new Node<>(startMethod));

        List<Method> worklist = new ArrayList<>();
        Set<Method> alreadyVisited = new HashSet<>();
        worklist.add(startMethod);

        while (!worklist.isEmpty()) {
            System.out.println("worklist = " + worklist);
            Method method = worklist.remove(0);
            alreadyVisited.add(method);
            var correspondingNode = new Node<>(method);

            if (!method.isAbstract() && !method.isUnknown()) {
                method.getControlFlowGraph().getBasicBlocks().stream().map(BasicBlock::getStatements).flatMap(List::stream)
                        .forEach(s -> {
                            System.out.println("statement = " + s);

                            CallExpression expr = null;
                            if (s instanceof CallStatement c)
                                expr = c.getCallExpression();
                            else if (s instanceof AssignStatement a && a.getRhs() instanceof CallExpression e)
                                expr = e;
                            if (expr == null)
                                return;
                            System.out.println("expression = " + expr);

                            List<Method> possibleCalls = CHA(expr);
                            System.out.println("possibleCalls = " + possibleCalls);

                            for (Method m : possibleCalls) {
                                if (method.isAbstract() || (m.getJavaClass() != null && m.getJavaClass().isInterface()) )
                                    continue;

                                graph.addEdge(correspondingNode, new Node<>(m));

                                if (!(alreadyVisited.contains(m) || worklist.contains(m))) {
                                    worklist.add(m);
                                }
                            }
                        });
            }
        }

        return graph;
    }

    private static List<Method> CHA(CallExpression callExpression) {
        if (callExpression.isStaticCall() || ((Local) callExpression.getObject()).hasTypeInformation()) {
            return Stream.of(callExpression.getMethod()).toList();
        }

        var sig = callExpression.getMethod().getSignature();
        List<RefType> subclasses = getAllSubclasses(callExpression.getRefType());
        return subclasses.stream()
                .flatMap(s -> s.getMethodBySignature(sig).stream()).toList();
    }

    public static Graph<RefType> createTypeHierarchy(Package p) {
        Graph<RefType> hierarchy = new Graph<>();

        for (JavaClass javaClass : p.getClasses()) {
            hierarchy.addNode(new Node<>(new RefType(javaClass)));
        }

        for (Node<RefType> node : new ArrayList<>(hierarchy.getNodes())) {
            var klass = node.getValue().getClassType();

            var ext = klass.getExtendsClass();
            if (ext != null) {
                hierarchy.addEdge(new Node<>(ext), node);
            }

            for (RefType impl : klass.getImplementsInterfaces()) {
                hierarchy.addEdge(new Node<>(impl), node);
            }
        }

        return hierarchy;
    }

    /**
     *
     * @param c the class of which to get all subclasses
     * @return all *transitive* subclasses, including c itself
     */
    private static List<RefType> getAllSubclasses(RefType c) {
        return Stream.concat(Stream.of(c), hierarchy.getEdges().stream()
                .filter(e -> e.getOriginNode().equals(hierarchy.findNode(c)))
                .map(e -> e.getTargetNode().getValue())
                .flatMap(s -> getAllSubclasses(s).stream())).toList();
    }
}
