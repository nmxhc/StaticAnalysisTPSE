package AST.Statements;

import AST.CodeStructure.Method;
import AST.Expressions.Expression;
import AST.Expressions.VariableExpression;

import java.util.List;

/**
 * Represents a call statement in a {@link AST.CodeStructure.BasicBlock}
 * of analysed java code.
 * variable is assigned return of method called with arguments.
 */
public class CallStatement extends Statement {

    private final VariableExpression variable;
    private final Method method;
    private final List<Expression> arguments;

    /**
     *
     * @param variable
     * @param method
     * @param arguments
     */
    public CallStatement(VariableExpression variable, Method method, List<Expression> arguments) {
        this.variable = variable;
        this.method = method;
        this.arguments = arguments;
    }

    /**
     * @return variable assigned in call statement.
     */
    public VariableExpression getVariable() {
        return variable;
    }

    /**
     * @return method called in call statement.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * @return arguments passed to method in call statement.
     */
    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        String argString = "";
        for (int i = 0; i < arguments.size(); i++) {
            argString += arguments.get(i); // not efficient, but does the job
            if (i < arguments.size() - 1) {
                argString += ", ";
            }
        }
        return "TODO." + method.getName() + "(" + argString + ")";
    }
}
