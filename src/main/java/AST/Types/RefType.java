package AST.Types;

import AST.CodeStructure.JavaClass;

import java.util.Optional;
import AST.CodeStructure.Method;
import AST.CodeStructure.MethodSignature;

/**
 * Represents the data type of {@link JavaClass} in analysed code.
 *
 * @see Type
 */
public class RefType extends Type {

    private final JavaClass classType;
    private String fallbackName;

    public RefType(JavaClass classDeclaration) {
        this.classType = classDeclaration;
        this.fallbackName = null;
    }

    public RefType(String name) {
        this.classType = null;
        this.fallbackName = name;
    }

    /**
     * @return name of the {@link JavaClass} represented in RefType.
     */
    public String getName() {
        return classType != null ? classType.getName() : fallbackName;
    }

    /**
     * @return the {@link JavaClass} of this RefType.
     */
    public JavaClass getClassType() {
        return classType;
    }

    public boolean isUnknown() {
        return classType == null;
    }

    public Optional<Method> getMethodByName(String name) {
        if (isUnknown()) return Optional.of(new Method(this, name));
        return getClassType().getMethodByName(name);
    }

    public Optional<Method> getMethodBySignature(MethodSignature sig) {
        if (isUnknown()) return Optional.of(new Method(this, sig));
        return getClassType().getMethodBySignature(sig);
    }

    /**
     * @return name of RefType.
     */
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RefType r) {
            return getName().equals(r.getName());
        } else {
            return false;
        }
    }
}
