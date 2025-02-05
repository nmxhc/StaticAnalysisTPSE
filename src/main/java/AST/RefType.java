package AST;

/**
 *
 */
public class RefType extends Type{

    protected ClassDeclaration classType;

    public RefType(String name){
        this.name = name;
    }

    public String getTypeName(){
        return name;
    }
}
