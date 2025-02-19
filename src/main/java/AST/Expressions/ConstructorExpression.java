package AST.Expressions;

import AST.CodeStructure.JavaClass;

import java.util.List;

/**
 * Represents expressions creating a new class.
 */
public class ConstructorExpression extends Expression {

    private final JavaClass javaClass;
    private final List<Expression> arguments;

    /**
     *
     * @param javaClass
     * @param arguments
     */
    public ConstructorExpression(JavaClass javaClass, List<Expression> arguments) {
        this.javaClass = javaClass;
        this.arguments = arguments;
    }

    /**
     * @return Class of object being created.
     */
    public JavaClass getJavaClass() {
        return javaClass;
    }

    /**
     * @return list of arguments passed to constructor;
     * may be empty.
     */
    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "new " + javaClass.getName();
    }
}
