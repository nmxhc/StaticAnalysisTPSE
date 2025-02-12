package AST.Statements;

import AST.CodeStructure.BasicBlock;
import AST.Expressions.Expression;

/**
 * Represents a branch statement in a {@link AST.CodeStructure.BasicBlock}
 * of analysed java code.
 * if(condition) trueBlock;
 * else falseBlock.
 */
public class BranchStatement extends Statement {
    //replace with conditional branch statement
    private final Expression condition;
    private final BasicBlock trueBlock;
    private final BasicBlock falseBlock;

    /**
     *
     * @param condition
     * @param trueBlock
     * @param falseBlock
     */
    public BranchStatement(Expression condition, BasicBlock trueBlock, BasicBlock falseBlock) {
        this.condition = condition;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    /**
     * @return condition of branch statement.
     */
    public Expression getCondition() {
        return condition;
    }

    /**
     * @return trueBlock of branch statement.
     */
    public BasicBlock getTrueBlock() {
        return trueBlock;
    }

    /**
     * @return falseBlock of branch statement.
     */
    public BasicBlock getFalseBlock() {
        return falseBlock;
    }

    @Override
    public String toString() {
        return "if (" + condition + ") " + trueBlock + " " + falseBlock;
    }
}