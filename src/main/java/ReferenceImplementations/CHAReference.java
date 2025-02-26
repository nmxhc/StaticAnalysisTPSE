package ReferenceImplementations;

import AST.CodeStructure.BasicBlock;
import AST.CodeStructure.JavaClass;
import AST.CodeStructure.Method;
import AST.Expressions.CallExpression;
import AST.Expressions.Local;
import AST.Statements.CallStatement;
import DotAPI.Edge;
import DotAPI.Graph;

import java.util.*;
import java.util.stream.Stream;

import AST.CodeStructure.Package;
import DotAPI.Node;

public class CHAReference {

    private static Graph<JavaClass> hierarchy;

    public static Graph<Method> run(Package pkg, String className, String methodName) {

        hierarchy = createTypeHierarchy(pkg);

        Graph<Method> graph = new Graph<>();
        graph.addNode(new Node<>(pkg.getClassByName(className).getMethodByName(methodName)));

        List<Method> worklist = new ArrayList<>();
        Set<Method> alreadyVisited = new HashSet<>();
        worklist.add(pkg.getClassByName(className).getMethodByName(methodName));

        while (!worklist.isEmpty()) {
            System.out.println("WL: " + worklist);
            Method method = worklist.remove(0);
            alreadyVisited.add(method);
            Node<Method> correspondingNode = graph.findNode(method);

            if (!method.isAbstract()) {
                method.getControlFlowGraph().getBasicBlocks().stream().map(BasicBlock::getStatements).flatMap(List::stream)
                        .filter(s -> s instanceof CallStatement)
                        .forEach(s -> {
                            CallExpression callExpression = ((CallStatement) s).getCallExpression();

                            System.out.println(s);
                            List<Method> possibleCalls = CHA(callExpression);
                            System.out.println(possibleCalls);

                            for (Method m : possibleCalls) {
                                if (graph.getNodes().stream().map(Node::getValue).toList().contains(m)) {
                                    graph.addEdge(correspondingNode, graph.findNode(m));
                                    return;
                                }

                                Node<Method> nodeToAdd = new Node<>(m);
                                graph.addNode(nodeToAdd);
                                graph.addEdge(correspondingNode, nodeToAdd);

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

        String methodName = callExpression.getMethod().getName();
        List<JavaClass> subclasses = getAllSubclasses(callExpression.getJavaClass());
        return subclasses.stream()
                .filter(s -> s.getMethods().stream().map(Method::getName).toList().contains(methodName))
                .map(s -> s.getMethodByName(methodName)).toList();
    }


    private static String methodToString(Method m) {
        return m.getJavaClass().getName() + "." + m.getName();
    }

    public static Graph<JavaClass> createTypeHierarchy(Package p) {
        Graph<JavaClass> hierarchy = new Graph<>();

        for (JavaClass javaClass : p.getClasses()) {
            hierarchy.addNode(new Node<>(javaClass));
        }

        for (Node<JavaClass> node : hierarchy.getNodes()) {
            if (node.getValue().getExtendsClass() != null) {
                hierarchy.addEdge(hierarchy.findNode(node.getValue().getExtendsClass()), node);
            }
            for (JavaClass impl : node.getValue().getImplementsInterfaces()) {
                hierarchy.addEdge(hierarchy.findNode(impl), node);
            }
        }

        return hierarchy;
    }

    /**
     *
     * @param c the class of which to get all subclasses
     * @return all *transitive* subclasses, including c itself
     */
    private static List<JavaClass> getAllSubclasses(JavaClass c) {
        return Stream.concat(Stream.of(c), hierarchy.getEdges().stream()
                .filter(e -> e.getOriginNode().equals(hierarchy.findNode(c)))
                .map(e -> e.getTargetNode().getValue())
                .flatMap(s -> getAllSubclasses(s).stream())).toList();
    }
}
