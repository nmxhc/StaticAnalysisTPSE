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

    public String getName() {
        return name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Method> getMethods() {
        return methods;
    }

    @Nullable
    public JavaClass getExtendsClass() {
        return extendsClass;
    }

    @Nullable
    public List<JavaClass> getImplementsInterfaces() {
        return implementsInterfaces;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public boolean isInterface() {
        return isInterface;
    }
}