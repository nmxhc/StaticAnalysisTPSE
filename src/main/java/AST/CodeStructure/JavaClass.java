package AST.CodeStructure;

public class JavaClass {

    private final String name;
    protected ClassDeclaration classDeclaration = null;

    public JavaClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean hasClassDeclaration() {
        return classDeclaration != null;
    }

    public ClassDeclaration getClassDeclaration() {
        if (!hasClassDeclaration()) {
            throw new RuntimeException("Tried to get class declaration for opaque class " + name);
        }
        return classDeclaration;
    }
}
