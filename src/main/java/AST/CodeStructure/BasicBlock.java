package AST.CodeStructure;

import java.util.List;
import AST.Statements.Statement;

/**
 * Implements a basic block. Currently, following basic blocks are available via the last statement (branch)
 */
public class BasicBlock {
    //CFG auslesen aus Soot

    private final List<Statement> statements;
    private final List<BasicBlock> successors;
    // private List<BasicBlock> predecessors;

    public BasicBlock(List<Statement> statements, List<BasicBlock> successors) {
        this.statements = statements;
        this.successors = successors;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<BasicBlock> getSuccessors() {
        return successors;
    }
}
