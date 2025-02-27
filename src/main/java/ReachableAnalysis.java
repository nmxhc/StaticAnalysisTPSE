import AST.CodeStructure.*;
import AST.CodeStructure.Package;
import AST.Statements.Statement;
import DotAPI.Graph;
import DotAPI.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Descriptive class depicting example run of an analysis on this structure.
 *
 * @see Util
 * @see ReferenceImplementations
 */
public class ReachableAnalysis {

    /**
     * Executing analysis with
     * @param pkg of class, containing method
     * @param className of method, in package
     * @param methodName to be analysed in class of package
     * @return {@link Graph} of analysis on methodName.
     */
    public static Graph<String> run(Package pkg, String className, String methodName) {
        // we want to analyze a specific method
        JavaClass javaClass = pkg.getClassByName(className);
        Method method = javaClass.getMethodByName(methodName).get();

        ControlFlowGraph cfg = method.getControlFlowGraph();
        BasicBlock entry = cfg.getEntryBlock();

        // the cfg has been loaded, now do something with it
        List<BasicBlock> worklist = new ArrayList<>();
        Set<BasicBlock> reachable = new HashSet<>();

        worklist.add(entry);
        while (!worklist.isEmpty()) {
            // retrieve next block to work on
            BasicBlock currentBlock = worklist.remove(0);

            // do some work with the block
            reachable.add(currentBlock);

            // add all not yet visited successors
            for (BasicBlock successor : currentBlock.getSuccessors())
                if (!reachable.contains(successor))
                    worklist.add(successor);
        }

        // do something with the result of the analysis
        Graph<String> graph = new Graph<String>();
        for (BasicBlock basicBlock : reachable) {
            Node<String> nodeToAdd = new Node<>(bbToString(basicBlock));
            graph.addNode(nodeToAdd);
            graph.addEdge(graph.findNode(bbToString(basicBlock)), nodeToAdd);
        }

        return graph;
    }

    private static String bbToString(BasicBlock b) {
        StringBuilder res = new StringBuilder();
        for (Statement s : b.getStatements()) {
            res.append(s.toString() + "\\n");
        }
        return res.toString();
    }
}
