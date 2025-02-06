package AST.CodeStructure;

import java.util.List;
import AST.Statements.Statement;

/**
 * Implements a basic block. Each basic block ends with either a BranchStatement or GotoStatement
 */
public class BasicBlock {
    //CFG auslesen aus Soot

    private final List<Statement> statements;
    private List<BasicBlock> successors;
    private List<BasicBlock> predecessors;

    public BasicBlock(List<Statement> statements) {
        this.statements = statements;
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

    protected void setSuccessors(List<BasicBlock> successors) {
        this.successors = successors;
    }

    protected void setPredecessors(List<BasicBlock> predecessors) {
        this.predecessors = predecessors;
    }
}
