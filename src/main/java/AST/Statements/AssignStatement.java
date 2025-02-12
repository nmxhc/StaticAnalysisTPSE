package AST.Statements;

import AST.Expressions.Expression;

/**
 * Represents an assign statement in a {@link AST.CodeStructure.BasicBlock}
 * of analysed java code.
 * lhs is assigned the value of rhs; ´lhs = rhs´
 */
public class AssignStatement extends Statement {

    private Variable lhs;
    private Expression rhs;

    /**
     *
     * @param lhs
     * @param rhs
     */
    public AssignStatement(Variable lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * @return the left-hand-side of the assign statement.
     */
    public Variable getLhs() {
        return lhs;
    }

    /**
     * @return the right-hand-side of the assign statement.
     */
    public Expression getRhs() {
        return rhs;
    }

    @Override
    public String toString() {
        return lhs + " = " + rhs;
    }
}
