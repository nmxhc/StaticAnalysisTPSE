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
     * Create new ConstructorExpression with
     * @param javaClass being constructed
     * @param arguments passed to constructor
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

    /**
     * @return ConstructorExpression in form 'new ClassName(param1, ...)'
     */
    @Override
    public String toString() {
        StringBuilder val = new StringBuilder("new " + javaClass.getName() + "(");
        for(int i=0; i<arguments.size(); i++){
            val.append(arguments.get(i).toString());
            if (i < arguments.size() - 1)
                val.append(", ");
        }
        val.append(")");
        return val.toString();
    }
}
