package AST.Expressions;

import AST.Types.Type;
import com.google.errorprone.annotations.Var;

public class VariableExpression extends Expression {

    private final Type type;
    private final String name;

    public VariableExpression(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}