package ReferenceImplementations;

import AST.CodeStructure.BasicBlock;
import AST.CodeStructure.Method;
import AST.Statements.CallStatement;
import DotAPI.Graph;

import java.util.*;

import AST.CodeStructure.Package;
import DotAPI.Node;

public class CHAReference {

    public static Graph<String> run(Package pkg, String className, String method) {

        Graph<String> graph = new Graph<>();
        graph.addNode(new Node<>(className + "." + method));

        List<Method> worklist = new ArrayList<>();
        Set<Method> alreadyVisited = new HashSet<>();
        worklist.add(pkg.getClassByName(className).getMethodByName(method));

        while (!worklist.isEmpty()) {
            Method m = worklist.remove(0);
            alreadyVisited.add(m);
            Node<String> correspondingNode = graph.findNode(methodToString(m));

            System.out.println(graph.getNodes().stream().map(Node::getValue).toList());
            System.out.println(methodToString(m));

            if (!m.isAbstract()) {
                m.getControlFlowGraph().getBasicBlocks().stream().map(BasicBlock::getStatements).flatMap(List::stream)
                        .filter(s -> s instanceof CallStatement)
                        .forEach(s -> {

                            Method m1 = ((CallStatement) s).getCallExpression().getMethod();

                            if (alreadyVisited.contains(m1)) {
                                return;
                            }

                            Node<String> nodeToAdd = new Node<>(methodToString(m1));
                            if (!graph.getNodes().contains(nodeToAdd)) {
                                graph.addNode(nodeToAdd);
                            }

                            graph.addEdge(correspondingNode, nodeToAdd);
                            worklist.add(m1);
                        });
            }
        }

        return graph;
    }

    private static String methodToString(Method m) {
        return m.getJavaClass().getName() + "." + m.getName();
    }

}
