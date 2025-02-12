package AST.Statements;

import AST.CodeStructure.BasicBlock;
import AST.Expressions.Expression;

public class ReturnStatement extends Statement {
    private final Expression returnValue;

    public ReturnStatement(Expression returnValue) {
        this.returnValue = returnValue;
    }

    public boolean hasReturnValue() {
        return returnValue != null;
    }

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
