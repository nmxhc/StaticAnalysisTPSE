package AST.CodeStructure;

import AST.Statements.Statement;
import AST.Types.Type;

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
    protected MethodDeclaration methodDeclaration = null;

    /**
     * New AnalysedMethod
     * @param name of method
     */
    public Method(String name) {
        this.name = name;
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
}
