package AST.Statements;

import AST.CodeStructure.BasicBlock;
import AST.Expressions.Expression;

public class BranchStatement extends Statement {
    //replace with conditional branch statement
    private final Expression condition;
    private final BasicBlock thenBlock;
    private final BasicBlock elseBlock;

    public BranchStatement(Expression condition, BasicBlock thenBlock, BasicBlock elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public Expression getCondition() {
        return condition;
    }

    public BasicBlock getThenBlock() {
        return thenBlock;
    }

    public BasicBlock getElseBlock() {
        return elseBlock;
    }

}