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
     * Create new ConstructorExpression with
     * @param refType being constructed
     * @param arguments passed to constructor
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

    /**
     * @return ConstructorExpression in form 'new ClassName(param1, ...)'
     */
    @Override
    public String toString() {
        StringBuilder val = new StringBuilder("new " + refType.getName() + "(");
        for(int i=0; i<arguments.size(); i++){
            val.append(arguments.get(i).toString());
            if (i < arguments.size() - 1)
                val.append(", ");
        }
        val.append(")");
        return val.toString();
    }
}
