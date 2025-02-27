package AST.Expressions;

import AST.Statements.AssignStatement;
import AST.Types.Type;

/**
 * Represents a local variable in analysed java statement.
 *
 * @see AssignStatement
 */
public class Local extends Variable {
    private String name;
    private Type type;

    /**
     * Create new Local with
     * @param name of local
     * @param type of local
     */
    public Local(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    /**
     * @return the type associated with the variable
     * @throws RuntimeException if the type is unknown
     */
    public Type getType() {
        if (!hasTypeInformation()) {
            throw new RuntimeException("Local variable " + name + " doesn't have type info associated with it");
        }
        return type;
    }

    /**
     * @return true if type is known.
     */
    public boolean hasTypeInformation() {
        return type != null;
    }

    /**
     * @return name of local.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Local as String.
     */
    @Override
    public String toString() {
        return name;
    }
}
