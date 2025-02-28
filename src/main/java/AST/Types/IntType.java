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

    /**
     * @param other IntType to be compared
     * @return true, if int types are equal
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    /**
     * @return hash of this IntType.
     */
    @Override
    public int hashCode() {
        // random number - all instances the same hash code
        return 937429781;
    }
}
