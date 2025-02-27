package AST.Types;

import AST.CodeStructure.JavaClass;

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

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RefType r) {
            return getClassType().equals(r.getClassType());
        } else {
            return false;
        }
    }
}
