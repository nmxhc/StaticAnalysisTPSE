package AST.Statements;

import AST.Expressions.Expression;

/**
 * Represents a throw-statement in a {@link AST.CodeStructure.BasicBlock}
 * of analysed java code.
 * throw operand;
 */
public class ThrowStatement extends Statement {
    private final Expression op;

    public ThrowStatement(Expression op) {
        this.op = op;
    }

    /**
     * @return operand of throw statement.
     */
    public Expression getOperand() {
        return op;
    }

    @Override
    public String toString() {
        return "throw " + getOperand().toString();
    }
}
