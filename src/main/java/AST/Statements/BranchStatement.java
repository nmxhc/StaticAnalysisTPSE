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
     * Create new BranchStatement with
     * @param condition for branching
     * @param trueBlock block to jump to if condition is true
     * @param falseBlock block to jump to if condition is false
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

    /**
     * @return 'if([condition]) [trueBlock] [falseBlock]
     */
    @Override
    public String toString() {
        return "if (" + condition + ") " + trueBlock.toString() + " " + falseBlock.toString();
    }
}