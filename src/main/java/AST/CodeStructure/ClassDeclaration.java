package AST.CodeStructure;

import javax.annotation.Nullable;
import java.util.List;

/**
 *
 */
public class ClassDeclaration {

    private final List<Attribute> attributes;
    private final List<Method> methods;

    @Nullable
    private final JavaClass extendsClass;
    @Nullable
    private final List<JavaClass> implementsInterfaces;

    private final boolean isAbstract;
    private final boolean isInterface;

    /**
     * New AnalysedClass
     * @param attributes of class
     * @param methods of class
     * @param isAbstract is declared as abstract
     * @param isInterface is declared as Interface
     */
    public ClassDeclaration(List<Attribute> attributes, List<Method> methods, JavaClass extendsClass, List<JavaClass> implementsInterfaces, boolean isAbstract, boolean isInterface) {
        this.attributes = attributes;
        this.methods = methods;
        this.extendsClass = extendsClass;
        this.implementsInterfaces = implementsInterfaces;
        this.isAbstract = isAbstract;
        this.isInterface = isInterface;
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
    public JavaClass getExtendsClass() {
        return extendsClass;
    }

    /**
     * @return Interfaces implemented
     */
    @Nullable
    public List<JavaClass> getImplementsInterfaces() {
        return implementsInterfaces;
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
