package AST;

/**
 *
 */
public class RefType extends Type{

    public RefType(String name){
        this.name = name;
    }

    public String getTypeName(){
        return name;
    }
}
