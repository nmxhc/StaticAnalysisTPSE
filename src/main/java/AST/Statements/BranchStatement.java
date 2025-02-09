package AST.Statements;

import AST.CodeStructure.BasicBlock;
import AST.Expressions.Expression;

/**
 * Represents a branch statement in a {@link AST.CodeStructure.BasicBlock}
 * of analysed java code.
 * if(condition) thenBlock;
 * else elseBlock.
 */
public class BranchStatement extends Statement {
    //replace with conditional branch statement
    private final Expression condition;
    private final BasicBlock thenBlock;
    private final BasicBlock elseBlock;

    /**
     *
     * @param condition
     * @param thenBlock
     * @param elseBlock
     */
    public BranchStatement(Expression condition, BasicBlock thenBlock, BasicBlock elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    /**
     * @return condition of branch statement.
     */
    public Expression getCondition() {
        return condition;
    }

    /**
     * @return thenBlock of branch statement.
     */
    public BasicBlock getThenBlock() {
        return thenBlock;
    }

    /**
     * @return elseBlock of branch statement.
     */
    public BasicBlock getElseBlock() {
        return elseBlock;
    }

}