package SootAPI;

import javax.annotation.Nullable;
import java.util.List;

/**
 *
 */
public class AnalysedClass {

    protected String name;
    protected List<AnalysedAttribute> attributes;
    protected List<AnalysedMethod> methods;

    @Nullable
    protected AnalysedClass extendsClass;
    @Nullable
    protected List<AnalysedClass> implementsInterfaces;

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
    public AnalysedClass(String name, List<AnalysedAttribute> attributes, List<AnalysedMethod> methods, AnalysedClass extendsClass, List<AnalysedClass> implementsInterfaces, boolean isAbstract, boolean isInterface) {
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
    public List<AnalysedAttribute> getAttributes() {
        return attributes;
    }

    /**
     * @return methods of class
     */
    public List<AnalysedMethod> getMethods() {
        return methods;
    }

    /**
     * @return super class
     */
    @Nullable
    public AnalysedClass getExtendsClass() {
        return extendsClass;
    }


    protected void setExtendsClass(@Nullable AnalysedClass extendsClass) {
        this.extendsClass = extendsClass;
    }

    /**
     * @return Interfaces implemented
     */
    @Nullable
    public List<AnalysedClass> getImplementsInterfaces() {
        return implementsInterfaces;
    }


    protected void setImplementsInterfaces(@Nullable List<AnalysedClass> implementsInterfaces) {
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
