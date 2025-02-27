package AST.CodeStructure;

import org.w3c.dom.Attr;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JavaClass {

    private final String name;
    protected List<Attribute> attributes;
    protected List<Method> methods;

    @Nullable
    protected JavaClass extendsClass;
    @Nullable
    protected List<JavaClass> implementsInterfaces;

    protected boolean isAbstract;
    protected boolean isInterface;

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
    public JavaClass getExtendsClass() {
        return extendsClass;
    }

    /**
     * @return the interfaces the class implements
     */
    @Nullable
    public List<JavaClass> getImplementsInterfaces() {
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
     * @param name the name of the method to retrieve
     * @return the method that has that name
     */
    public Optional<Method> getMethodByName(String name) {
        return methods.stream().filter(m -> m.getName().equals(name)).findAny();
    }

    public Optional<Method> getMethodBySignature(MethodSignature sig) {
        return methods.stream().filter(m -> m.getSignature().equals(sig)).findAny();
    }

    @Override
    public String toString() {
        if (isInterface()) {
            return "<<" + name + ">>";
        }
        return name;
    }

    @Override public boolean equals(Object other) {
        if (other instanceof JavaClass c) {
            return getName().equals(c.getName());
        } else {
            return false;
        }
    }
}
