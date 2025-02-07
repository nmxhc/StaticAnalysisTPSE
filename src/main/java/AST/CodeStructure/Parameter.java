package AST.CodeStructure;

import AST.Types.Type;

/**
 * Represents Parameter of {@link Method} of analysed code.
 *
 * @see Method
 * @see Type
 */
public class Parameter {

    protected String name;
    protected Type type;

    /**
     * Constructs Parameter with specified name and type.
     * @param name of parameter.
     * @param type of parameter.
     */
    public Parameter(String name, Type type){
        this.name = name;
        this.type = type;
    }

}
