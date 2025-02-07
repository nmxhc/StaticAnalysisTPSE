package AST.Expressions;

import AST.CodeStructure.ClassDeclaration;
import java.util.List;

/**
 * Represents expressions creating a new element of a {@link ClassDeclaration}.
 */
public class ConstructorExpression extends Expression {

    private final ClassDeclaration classDeclaration;
    private final List<Expression> arguments;

    /**
     *
     * @param classDeclaration
     * @param arguments
     */
    public ConstructorExpression(ClassDeclaration classDeclaration, List<Expression> arguments) {
        this.classDeclaration = classDeclaration;
        this.arguments = arguments;
    }

    /**
     * @return Class of object being created.
     */
    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }

    /**
     * @return list of arguments passed to constructor;
     * may be empty.
     */
    public List<Expression> getArguments() {
        return arguments;
    }
}
