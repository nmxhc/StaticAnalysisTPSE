package AST.Statements;

import AST.CodeStructure.BasicBlock;

public class GotoStatement {
    private final BasicBlock targetBlock;

    public GotoStatement(BasicBlock targetBlock) {
        this.targetBlock = targetBlock;
    }

    public BasicBlock getTargetBlock() {
        return targetBlock;
    }
}
