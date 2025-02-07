package AST.CodeStructure;

import org.checkerframework.checker.units.qual.N;
import org.w3c.dom.Attr;

import javax.annotation.Nullable;
import javax.smartcardio.ATR;
import java.util.List;

/**
 * Represents a Java class of the analysed code.
 * <p>
 * This class encapsulates the essential characteristics of a Java class, such as its name,
 * whether it's abstract or an interface, its attributes, methods, superclass, and implemented interfaces.
 * It allows for further examination and manipulation of the class structure as part of a larger code analysis system.
 * </p>
 *
 * @see Attribute
 * @see Method
 */
public class ClassDeclaration {

    private final String name;
    private final boolean isAbstract;
    private final boolean isInterface;

    private List<Attribute> attributes;
    private List<Method> methods;

    @Nullable
    private ClassDeclaration extendsClass;
    @Nullable
    private List<ClassDeclaration> implementsInterfaces;

    /**
     * Constructs a ClassDeclaration with the specified name, abstract status, and interface status.
     *
     * @param name        The name of the class.
     * @param isAbstract  True if the class is abstract, false otherwise.
     * @param isInterface True if the class is an interface, false otherwise.
     */
    public ClassDeclaration(String name, boolean isAbstract, boolean isInterface) {
        this.name = name;
        this.isAbstract = isAbstract;
        this.isInterface = isInterface;
    }

    /**
     * @return name of class
     */
    public String getName() {
        return name;
    }

    /**
     * @return attributes of class {@link Attribute}
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    protected void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return methods of class {@link Method}
     */
    public List<Method> getMethods() {
        return methods;
    }

    protected void setMethods(List<Method> methods) {
        this.methods = methods;
    }


    /**
     * @return super class
     */
    @Nullable
    public ClassDeclaration getExtendsClass() {
        return extendsClass;
    }

    protected void setExtendsClass(@Nullable ClassDeclaration classDeclaration) {
        this.extendsClass = classDeclaration;
    }

    /**
     * @return Interfaces implemented {@link ClassDeclaration}
     */
    @Nullable
    public List<ClassDeclaration> getImplementsInterfaces() {
        return implementsInterfaces;
    }

    protected void setImplementsInterfaces(@Nullable List<ClassDeclaration> implementsInterfaces) {
        this.implementsInterfaces = implementsInterfaces;
    }

    /**
     * @return is declared as abstract
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * @return is declared as Interface
     */
    public boolean isInterface() {
        return isInterface;
    }
}
