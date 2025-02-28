package AST.CodeStructure;

import AST.Types.RefType;

import org.w3c.dom.Attr;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Analysed class containing
 * name of class,
 * list of attributes of class,
 * list of methods of class,
 * {@link RefType} of class extended by this class,
 * list of interfaces implemented by this class,
 * bool for if this class isAbstract, isInterface.
 *
 * @see Attribute
 * @see Method
 */
public class JavaClass {

    private final String name;
    protected List<Attribute> attributes;
    protected List<Method> methods;

    @Nullable
    protected RefType extendsClass;
    @Nullable
    protected List<RefType> implementsInterfaces;

    protected boolean isAbstract;
    protected boolean isInterface;

    /**
     * Create new Java Class with given
     * @param name of the Java Class
     */
    public JavaClass(String name) {
        this.name = name;
    }

    /**
     * @return the class name
     */
    public String getName() {
        return name;
    }

    /**
     * @return all (static and non-static) attributes of the class
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @return all methods of the class
     */
    public List<Method> getMethods() {
        return methods;
    }

    /**
     * @return the class that it inherits from
     */
    @Nullable
    public RefType getExtendsClass() {
        return extendsClass;
    }

    /**
     * @return the interfaces the class implements
     */
    @Nullable
    public List<RefType> getImplementsInterfaces() {
        return implementsInterfaces;
    }

    /**
     * @return whether the class is abstract
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * @return whether the class is an interface
     */
    public boolean isInterface() {
        return isInterface;
    }

    /**
     * Try fetching method of this class by
     * @param name of method
     * @return Optional of method with given name
     */
    public Optional<Method> getMethodByName(String name) {
        return methods.stream().filter(m -> m.getName().equals(name)).findAny();
    }

    /**
     * Try fetching method of this class by
     * @param sig signature of method
     * @return Optional of method with given signature
     */
    public Optional<Method> getMethodBySignature(MethodSignature sig) {
        return methods.stream().filter(m -> m.getSignature().equals(sig)).findAny();
    }

    /**
     * @return '<<className>>'
     */
    @Override
    public String toString() {
        if (isInterface()) {
            return "<<" + name + ">>";
        }
        return name;
    }

    /**
     * @param other JavaClass to be compared
     * @return true, if names of classes are identical.
     */
    @Override public boolean equals(Object other) {
        if (other instanceof JavaClass c) {
            return getName().equals(c.getName());
        } else {
            return false;
        }
    }
}
