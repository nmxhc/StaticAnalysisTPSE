package AST.Types;

import AST.CodeStructure.ClassDeclaration;

/**
 *
 */
public class RefType extends Type {

    protected ClassDeclaration classType;

    private final ClassDeclaration classDeclaration;

    public RefType(ClassDeclaration classDeclaration){
        this.classDeclaration = classDeclaration;
    }

    public String getName(){
        return classDeclaration.getName();
    }

    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }
}
