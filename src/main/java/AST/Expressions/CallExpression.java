package AST.Expressions;

import AST.CodeStructure.Method;
import AST.CodeStructure.Parameter;
import java.util.List;

public class CallExpression extends Expression {

    private final VariableExpression variable;
    private final Method method;
    private final List<Expression> arguments;

    /* variable, method name, arguments */
    public CallExpression(VariableExpression variable, Method method, List<Expression> arguments) {
        this.variable = variable;
        this.method = method;
        this.arguments = arguments;
    }

    public VariableExpression getVariable() {
        return variable;
    }

    public Method getMethod() {
        return method;
    }

    public List<Expression> getArguments() {
        return arguments;
    }
}
