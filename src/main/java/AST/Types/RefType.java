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

    /**
     * Create new RefType with
     * @param classDeclaration class type of this refType.
     */
    public RefType(JavaClass classDeclaration) {
        this.classType = classDeclaration;
        this.fallbackName = null;
    }

    /**
     * Create new RefType with
     * @param name placeholder name for class type of this refType.
     */
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

    /**
     * @return true, if this refType's classType is unknown.
     */
    public boolean isUnknown() {
        return classType == null;
    }

    /**
     * Try fetching method of this refType's class type by
     * @param name of method
     * @return Optional of method with given name
     */
    public Optional<Method> getMethodByName(String name) {
        if (isUnknown()) return Optional.of(new Method(this, name));
        return getClassType().getMethodByName(name);
    }

    /**
     * Try fetching method of this refType's class type by
     * @param sig signature of method
     * @return Optional of method with given signature
     *
     * @see MethodSignature
     */
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

    /**
     * @param other RefType to be compared
     * @return true, if refType's contents are identical
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof RefType r) {
            return getName().equals(r.getName());
        } else {
            return false;
        }
    }

    /**
     * @return hash of this refType.
     */
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
