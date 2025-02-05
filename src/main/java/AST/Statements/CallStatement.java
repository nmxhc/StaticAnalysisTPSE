package AST.Statements;

import AST.CodeStructure.Method;
import AST.Expressions.Expression;
import AST.Expressions.VariableExpression;

import java.util.List;

public class CallStatement extends Statement {

    private final VariableExpression variable;
    private final Method method;
    private final List<Expression> arguments;

    /* variable, method name, arguments */
    public CallStatement(VariableExpression variable, Method method, List<Expression> arguments) {
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
