package AST.CodeStructure;

import java.util.List;
import AST.Statements.Statement;

/**
 * Implements a basic block. Each basic block ends with either a BranchStatement or GotoStatement
 */
public class BasicBlock {
    //CFG auslesen aus Soot

    protected List<Statement> statements = null;
    protected List<BasicBlock> successors;
    protected List<BasicBlock> predecessors;

    /**
     * Create new empty Basic Block.
     */
    public BasicBlock() {

    }

    /**
     * @return the statements of the basic block, in order
     */
    public List<Statement> getStatements() {
        return statements;
    }

    /**
     * @return the basic block's successors
     */
    public List<BasicBlock> getSuccessors() {
        return successors;
    }

    /**
     * @return the basic block's predecessors
     */
    public List<BasicBlock> getPredecessors() {
        return predecessors;
    }
}
