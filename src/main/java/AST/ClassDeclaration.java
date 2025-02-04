package AST;

import javax.annotation.Nullable;
import java.util.List;

/**
 *
 */
public class ClassDeclaration{

    protected String name;
    protected List<Attribute> attributes;
    protected List<Method> methods;

    @Nullable
    protected ClassDeclaration extendsClass;
    @Nullable
    protected List<ClassDeclaration> implementsInterfaces;

    protected boolean isAbstract;
    protected boolean isInterface;

    /**
     * New AnalysedClass
     * @param name of class
     * @param attributes of class
     * @param methods of class
     * @param extendsClass super class
     * @param implementsInterfaces Interfaces implemented
     * @param isAbstract is declared as abstract
     * @param isInterface is declared as Interface
     */
    public ClassDeclaration(String name, List<Attribute> attributes, List<Method> methods, ClassDeclaration extendsClass, List<ClassDeclaration> implementsInterfaces, boolean isAbstract, boolean isInterface) {
        this.name = name;
        this.attributes = attributes;
        this.methods = methods;
        this.extendsClass = extendsClass;
        this.implementsInterfaces = implementsInterfaces;
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


    protected void setExtendsClass(@Nullable ClassDeclaration extendsClass) {
        this.extendsClass = extendsClass;
    }

    /**
     * @return Interfaces implemented
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
