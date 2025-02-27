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

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof VoidType;
    }
}
