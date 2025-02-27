package AST.Types;

/**
 * Represents an integer type in the analysed code.
 *
 * @see Type
 */
public class IntType extends Type {

    /**
     * @return "int".
     */
    public String getName() {
        return "int";
    }

    /**
     * @return name of int.
     */
    @Override
    public String toString() {
        return getName();
    }
}
