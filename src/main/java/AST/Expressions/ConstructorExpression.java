package AST.Expressions;

import AST.Types.RefType;
import AST.CodeStructure.JavaClass;

import java.util.List;

/**
 * Represents expressions creating a new class.
 */
public class ConstructorExpression extends Expression {

    private final RefType refType;
    private final List<Expression> arguments;

    /**
     *
     * @param javaClass
     * @param arguments
     */
    public ConstructorExpression(RefType refType, List<Expression> arguments) {
        this.refType = refType;
        this.arguments = arguments;
    }

    /**
     * @return Class of object being created.
     */
    public JavaClass getJavaClass() {
        return refType.getClassType();
    }

    public RefType getRefType() {
        return refType;
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
        return "new " + refType.getName();
    }
}
