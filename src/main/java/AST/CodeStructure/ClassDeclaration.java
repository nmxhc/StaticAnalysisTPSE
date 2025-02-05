package AST.CodeStructure;

import javax.annotation.Nullable;
import java.util.List;

/**
 *
 */
public class ClassDeclaration {

    private final String name;
    private final List<Attribute> attributes;
    private final List<Method> methods;

    @Nullable
    private ClassDeclaration extendsClass;
    @Nullable
    private List<ClassDeclaration> implementsInterfaces;

    private final boolean isAbstract;
    private final boolean isInterface;

    /**
     * New AnalysedClass
     * @param name of class
     * @param attributes of class
     * @param methods of class
     * @param isAbstract is declared as abstract
     * @param isInterface is declared as Interface
     */
    public ClassDeclaration(String name, List<Attribute> attributes, List<Method> methods, boolean isAbstract, boolean isInterface) {
        this.name = name;
        this.attributes = attributes;
        this.methods = methods;
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
     * @return attributes of class
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @return methods of class
     */
    public List<Method> getMethods() {
        return methods;
    }

    /**
     * @return super class
     */
    @Nullable
    public ClassDeclaration getExtendsClass() {
        return extendsClass;
    }

    protected void setExtendsClass(ClassDeclaration classDeclaration) {
        this.extendsClass = classDeclaration;
    }

    /**
     * @return Interfaces implemented
     */
    @Nullable
    public List<ClassDeclaration> getImplementsInterfaces() {
        return implementsInterfaces;
    }

    public void setImplementsInterfaces(@Nullable List<ClassDeclaration> implementsInterfaces) {
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
