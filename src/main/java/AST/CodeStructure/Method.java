package AST.CodeStructure;

import AST.Statements.Statement;
import AST.Types.Type;
import fj.data.Java;

import java.util.List;

/**
 * Analysed Method containing
 * name of method,
 * returnType of method,
 * parameterTypes of method,
 * statements listed,
 * isAbstract method.
 */
public class Method {

    private final String name;
    private final JavaClass javaClass;
    protected MethodDeclaration methodDeclaration = null;

    /**
     * New AnalysedMethod
     * @param name of method
     */
    public Method(String name, JavaClass javaClass) {
        this.name = name;
        this.javaClass = javaClass;
    }

    /**
     * @return name of method
     */
    public String getName() {
        return name;
    }

    public boolean hasMethodDeclaration() {
        return methodDeclaration != null;
    }

    public MethodDeclaration getMethodDeclaration() {
        if (!hasMethodDeclaration()) {
            throw new RuntimeException("Tried to get method declaration for opaque method " + name);
        }
        return methodDeclaration;
    }

    public JavaClass getJavaClass() {
        return javaClass;
    }
}
