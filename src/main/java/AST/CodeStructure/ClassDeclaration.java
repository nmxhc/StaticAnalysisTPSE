package AST.CodeStructure;

import org.w3c.dom.Attr;

import javax.annotation.Nullable;
import javax.smartcardio.ATR;
import java.util.List;

/**
 *
 */
public class ClassDeclaration {

    private final String name;
    private final boolean isAbstract;
    private final boolean isInterface;

    private List<Attribute> attributes;
    private List<Method> methods;

    @Nullable
    private ClassDeclaration extendsClass;
    private List<ClassDeclaration> implementsInterfaces;

    /**
     * New AnalysedClass. attributes, methods, extends and implements are set seperately.
     * @param name of class
     * @param isAbstract is declared as abstract
     * @param isInterface is declared as Interface
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
     * @return attributes of class
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    protected void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return methods of class
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
