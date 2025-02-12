package AST.CodeStructure;

import AST.Types.Type;

/**
 * Class for class attributes
 */
public class Attribute {

    protected Type type;
    protected String name;

    /**
     * New Attribute
     * @param type of attribute
     * @param name of attribute
     */
    public Attribute(Type type, String name){
        this.type = type;
        this.name = name;
    }

    /**
     * @return type of attribute
     */
    public Type getType() {
        return type;
    }

    /**
     * @return name of attribute
     */
    public String getName() {
        return name;
    }
}
