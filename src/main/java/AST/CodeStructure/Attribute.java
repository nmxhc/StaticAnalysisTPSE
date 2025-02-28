package AST.CodeStructure;

import AST.Types.Type;

/**
 * Class for class attributes
 */
public class Attribute {

    private final Type type;
    private final String name;
    private final boolean isStatic;

    /**
     * New Attribute
     * @param type of attribute
     * @param name of attribute
     */
    public Attribute(Type type, String name, boolean isStatic){
        this.type = type;
        this.name = name;
        this.isStatic = isStatic;
    }

    /**
     * @return true, if this attribute is static.
     */
    public boolean isStatic() {
        return isStatic;
    }

    /**
     * @return type of attribute.
     */
    public Type getType() {
        return type;
    }

    /**
     * @return name of attribute.
     */
    public String getName() {
        return name;
    }

    /**
     * @return this attribute's name.
     */
    @Override
    public String toString() {
        return name;
    }
}
