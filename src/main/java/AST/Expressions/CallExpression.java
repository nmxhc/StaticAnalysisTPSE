package AST.Expressions;

import AST.CodeStructure.Method;
import AST.CodeStructure.Parameter;
import java.util.List;

/**
 * Representing a method call in the analysed java code.
 * {@link VariableExpression} = {@link Method}(list of {@link Expression})
 */
public class CallExpression extends Expression {

    private final VariableExpression variable;
    private final Method method;
    private final List<Expression> arguments;

    /**
     *
     * @param variable
     * @param method
     * @param arguments
     */
    public CallExpression(VariableExpression variable, Method method, List<Expression> arguments) {
        this.variable = variable;
        this.method = method;
        this.arguments = arguments;
    }

    /**
     * @return VariableExpression being assigned by CallExpression
     */
    public VariableExpression getVariable() {
        return variable;
    }

    /**
     * @return Method called by Expression.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * @return Arguments given to the method.
     */
    public List<Expression> getArguments() {
        return arguments;
    }
}
