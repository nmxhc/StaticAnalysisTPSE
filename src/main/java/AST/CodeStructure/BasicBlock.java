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

    public BasicBlock() {

    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<BasicBlock> getSuccessors() {
        return successors;
    }

    public List<BasicBlock> getPredecessors() {
        return predecessors;
    }
}
