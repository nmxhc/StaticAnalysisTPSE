package AST.Statements;

import AST.Expressions.CallExpression;

/**
 * Represents a call statement in a {@link AST.CodeStructure.BasicBlock}
 * of analysed java code.
 * variable is assigned return of method called with arguments.
 */
public class CallStatement extends Statement {

    private final CallExpression callExpression;

    /**
     * Create new CallStatement with
     * @param callExpression being called
     */
    public CallStatement(CallExpression callExpression) {
        this.callExpression = callExpression;
    }

    /**
     * @return the callExpression associated with the statement
     */
    public CallExpression getCallExpression() {
        return callExpression;
    }

    /**
     * @return callExpression being called
     */
    @Override
    public String toString() {
        return callExpression.toString();
    }
}
