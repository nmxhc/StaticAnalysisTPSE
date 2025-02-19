package AST.Statements;

import AST.CodeStructure.BasicBlock;
import AST.Expressions.Expression;

/**
 * Represents a return statement
 */
public class ReturnStatement extends Statement {
    private final Expression returnValue;

    public ReturnStatement(Expression returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * @return whether it has a return value or none (only the case if method is declared void)
     */
    public boolean hasReturnValue() {
        return returnValue != null;
    }

    /**
     * @return the return value
     * @throws RuntimeException if the method in which it is doesn't return a value, i.e. is void
     */
    public Expression getReturnValue() {
        if (!hasReturnValue()) {
            throw new RuntimeException("Tried to get return value where no value is returned");
        }
        return returnValue;
    }

    public String toString() {
        if (returnValue == null) {
            return "return";
        }

        return "return " + returnValue;
    }
}
