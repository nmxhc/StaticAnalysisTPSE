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

    /**
     * @return bool value.
     */
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BooleanType;
    }
}
