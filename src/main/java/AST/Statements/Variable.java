package AST.Statements;

/**
 * Represents a variable in analysed java statement.
 *
 * @see AssignStatement
 */
public class Variable {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
