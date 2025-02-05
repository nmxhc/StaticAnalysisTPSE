package AST.CodeStructure;

import AST.Types.Type;

/**
 *
 */
public class Parameter {

    protected String name;
    protected Type type;

    public Parameter(String name, Type type){
        this.name = name;
        this.type = type;
    }

}
