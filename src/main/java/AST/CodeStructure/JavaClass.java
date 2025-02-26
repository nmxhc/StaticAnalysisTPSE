package AST.CodeStructure;

import org.w3c.dom.Attr;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
     * @throws RuntimeException if no such method exists
     */
    public Method getMethodByName(String name) {
        for (Method m : methods) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        throw new RuntimeException("Method not found in class " + this.name + ": " + name);
    }

    @Override
    public String toString() {
        if (isInterface()) {
            return "<<" + name + ">>";
        }
        return name;
    }
}