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

    @Override
    public int hashCode() {
        // random number - all instances have the same hash code
        return 379377795;
    }
}
