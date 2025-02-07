package AST.Expressions;

import AST.Types.Type;
import com.google.errorprone.annotations.Var;

/**
 * Representing a variable expression, i.e. declaration, in the analysed java code.
 */
public class VariableExpression extends Expression {

    private final Type type;
    private final String name;

    /**
     *
     * @param type
     * @param name
     */
    public VariableExpression(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * @return type of the variable.
     */
    public Type getType() {
        return type;
    }

    /**
     * @return name of the variable.
     */
    public String getName() {
        return name;
    }
}