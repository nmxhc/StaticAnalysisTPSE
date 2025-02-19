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
    public String toString() {
        return getName();
    }

}
