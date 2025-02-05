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

    public boolean hasElse() {
        return this.elseBlock != null;
    }

    public Expression getCondition() {
        return condition;
    }

    public BasicBlock getThenBlock() {
        return thenBlock;
    }

    public BasicBlock getElseBlock() {
        if (!hasElse()) {
            throw new RuntimeException("Tried to get an elseBlock where none exists.");
        }
        return elseBlock;
    }

}