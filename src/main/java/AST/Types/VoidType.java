package AST.Types;

/**
 * Represents void type in java code
 */
public class VoidType extends Type {

    /**
     * @return "void"
     */
    public String getName() {
        return "void";
    }

    /**
      * @return name of Type.
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * @param other VoidType to be compared
     * @return true, if types are equal
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof VoidType;
    }

    /**
     * @return hash of this VoidType.
     */
    @Override
    public int hashCode() {
        // random number - all instances the same hash code
        return 1081695995;
    }
}
