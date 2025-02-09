package AST.CodeStructure;

import java.util.List;
import AST.Statements.Statement;

/**
 * Represents a basic block in Java Code.
 * Each basic block contains a list of statements,
 * links to succeeding and preceding Basic Block,
 * and ends with either a BranchStatement or GotoStatement.
 *
 * @see Method
 * @see Statement
 */
public class BasicBlock {

    private final List<Statement> statements;
    private List<BasicBlock> successors;
    private List<BasicBlock> predecessors;

    /**
     * Constructs a Basic Block with specified list of statements.
     *
     * @param statements    List of statements of basic block.
     */
    public BasicBlock(List<Statement> statements) {
        this.statements = statements;
    }

    /**
     * @return list of statements.
     */
    public List<Statement> getStatements() {
        return statements;
    }

    /**
     * @return list of successors.
     */
    public List<BasicBlock> getSuccessors() {
        return successors;
    }

    /**
     * @return list of predecessors.
     */
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
