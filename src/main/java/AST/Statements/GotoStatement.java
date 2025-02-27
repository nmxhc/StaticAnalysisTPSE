package AST.Statements;

import AST.CodeStructure.BasicBlock;

/**
 * Represents a goto-statement in a {@link AST.CodeStructure.BasicBlock}
 * of analysed java code.
 * program jumps to targetBlock
 */
public class GotoStatement extends Statement {
    private final BasicBlock targetBlock;

    /**
     * Create new GotoStatement with
     * @param targetBlock being branched to
     */
    public GotoStatement(BasicBlock targetBlock) {
        this.targetBlock = targetBlock;
    }

    /**
     * @return targetBlock of goto-statement.
     */
    public BasicBlock getTargetBlock() {
        return targetBlock;
    }

    /**
     * @return 'goto [targetBlock]'
     */
    @Override
    public String toString() {
        return "goto " + targetBlock;
    }
}
