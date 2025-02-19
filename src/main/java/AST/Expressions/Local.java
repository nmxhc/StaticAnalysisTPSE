package AST.Expressions;

import AST.Statements.AssignStatement;
import AST.Types.Type;

/**
 * Represents a variable in analysed java statement.
 *
 * @see AssignStatement
 */
public class Local extends Variable {
    private String name;
    private Type type;

    public Local(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Type getType() {
        if (!hasTypeInformation()) {
            throw new RuntimeException("Local variable " + name + " doesn't have type info associated with it");
        }
        return type;
    }

    public boolean hasTypeInformation() {
        return type != null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
