package AST.Types;

/**
 * Represents a boolean type in the analysed code.
 *
 * @see Type
 */
public class BooleanType extends Type {

    /**
     * @return "boolean".
     */
    public String getName() {
        return "boolean";
    }
    public String toString() {
        return getName();
    }

}
