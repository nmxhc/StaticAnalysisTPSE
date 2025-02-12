package AST.Types;

import AST.CodeStructure.ClassDeclaration;
import AST.CodeStructure.JavaClass;
import fj.data.Java;

/**
 * Represents the data type of {@link JavaClass} in analysed code.
 *
 * @see Type
 */
public class RefType extends Type {

    private final JavaClass classType;

    public RefType(JavaClass classDeclaration){
        this.classType = classDeclaration;
    }

    /**
     * @return name of the {@link JavaClass} represented in RefType.
     */
    public String getName(){
        return classType.getName();
    }

    /**
     * @return the {@link JavaClass} of this RefType.
     */
    public JavaClass getClassType() {
        return classType;
    }
}
