package AST.Statements;

import AST.CodeStructure.BasicBlock;

/**
 * Represents a goto-statement in a {@link AST.CodeStructure.BasicBlock}
 * of analysed java code.
 * program jumps to targetBlock
 */
public class GotoStatement {
    private final BasicBlock targetBlock;

    public GotoStatement(BasicBlock targetBlock) {
        this.targetBlock = targetBlock;
    }

    /**
     * @return targetBlock of goto-statement.
     */
    public BasicBlock getTargetBlock() {
        return targetBlock;
    }
}
