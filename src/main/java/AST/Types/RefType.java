package AST.Types;

import AST.CodeStructure.ClassDeclaration;

/**
 * Represents the data type of {@link ClassDeclaration} in analysed code.
 *
 * @see Type
 */
public class RefType extends Type {

    protected ClassDeclaration classType;

    private final ClassDeclaration classDeclaration;

    public RefType(ClassDeclaration classDeclaration){
        this.classDeclaration = classDeclaration;
    }

    /**
     * @return name of the {@link ClassDeclaration} represented in RefType.
     */
    public String getName(){
        return classDeclaration.getName();
    }

    /**
     * @return the {@link ClassDeclaration} of this RefType.
     */
    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }
}
