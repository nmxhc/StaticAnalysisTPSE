package ReferenceImplementations;

import AST.CodeStructure.BasicBlock;
import AST.CodeStructure.JavaClass;
import AST.CodeStructure.Method;
import AST.CodeStructure.Package;
import AST.Expressions.CallExpression;
import AST.Expressions.ConstructorExpression;
import AST.Expressions.Expression;
import AST.Statements.AssignStatement;
import AST.Statements.BranchStatement;
import AST.Statements.CallStatement;
import AST.Statements.Statement;
import AST.Types.RefType;
import AST.Types.Type;
import DotAPI.Graph;
import DotAPI.Node;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Reference implementation of Rapid Type Analysis
 * using {@link Graph} and {@link AST.CodeStructure.Util}.
 */
public class RTAReference extends CHAReference {

    private static List<Type> instantiatedTypes;
    private static Graph<RefType> hierarchy;

    /**
     * Runs RTA on the given method saving results in {@link Graph}
     * @param pkg of class, containing method
     * @param className of method, in package
     * @param methodName to be analysed in class of package
     * @return {@link Graph} of CHA analysis on methodName.
     */
    public static Graph<Method> run(Package pkg, String className, String methodName) {

        hierarchy = CHAReference.createTypeHierarchy(pkg);
        instantiatedTypes = getAllInstantiatedTypes(pkg);

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

                            List<Method> possibleCalls = RTA(expr);
                            System.out.println("possibleCalls = " + possibleCalls);

                            for (Method m : possibleCalls) {
                                if (m.isAbstract())
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

    private static List<Method> RTA(CallExpression callExpression) {
        if (!callExpression.isDynamicDispatch())
            return Stream.of(callExpression.getMethod()).toList();

        var sig = callExpression.getMethod().getSignature();
        List<RefType> subclasses = getAllSubclasses(callExpression.getRefType());

        return subclasses.stream().filter(subclass -> instantiatedTypes.contains(subclass))
                .flatMap(s -> s.getMethodBySignature(sig).stream()).toList();
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


    private static List<Type> getAllInstantiatedTypes(Package pkg) {
        List<List<BasicBlock>> allBasicBlocks = pkg.getClasses().stream().flatMap(c -> c.getMethods().stream()).map(m -> !m.isAbstract() ? m.getControlFlowGraph().getBasicBlocks() : new ArrayList<BasicBlock>()).toList();
        List<Statement> allStatements = new ArrayList<>();
        for (List<BasicBlock> basicBlockList : allBasicBlocks) {
            for (BasicBlock basicBlock : basicBlockList) {
                allStatements.addAll(basicBlock.getStatements());
            }
        }

        List<Type> instantiatedTypes = new ArrayList<>();
        for (Statement stmt : allStatements) { // Assumption: Only Assign Statements contain NewExpressions
            if (stmt instanceof AssignStatement) {
                if (((AssignStatement) stmt).getRhs() instanceof ConstructorExpression) {
                    instantiatedTypes.add(((ConstructorExpression) ((AssignStatement) stmt).getRhs()).getRefType());
                }
            }
        }

        return instantiatedTypes;
    }

}