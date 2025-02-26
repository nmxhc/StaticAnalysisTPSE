package AST.Types;

/**
 * Represents data type in analysed java code.
 *
 * @see RefType
 * @see IntType
 * @see BooleanType
 * @see VoidType
 */
public abstract class Type {

    /**
     * @return name of type.
     */
    public abstract String getName();

}
