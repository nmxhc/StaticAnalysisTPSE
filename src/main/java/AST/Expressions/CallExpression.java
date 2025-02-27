package AST.Expressions;

import AST.CodeStructure.JavaClass;
import AST.CodeStructure.Method;

import java.util.List;

/**
 * Representing a method call in the analysed java code.
 * {@link Variable} = {@link Method}(list of {@link Expression}).
 */
public class CallExpression extends Expression {

    private final JavaClass javaClass;
    private final Method method;
    private final List<Expression> arguments;

    private final Variable object;

    /**
     * Create new CallExpression with
     * @param javaClass the corresponding class
     * @param method the method that is called
     * @param arguments the arguments it's called with
     * @param object optional: if it is non-static, the receiver object
     */
    public CallExpression(JavaClass javaClass, Method method, List<Expression> arguments, Variable object) {
        this.javaClass = javaClass;
        this.method = method;
        this.arguments = arguments;

        this.object = object;
    }

    /**
     * If the method is non-static, this returns the variable in which object lives whose method is referenced here
     * @return the object
     * @throws RuntimeException if the method is a static method
     */
    public Variable getObject() {
        if (isStaticCall()) {
            throw new RuntimeException("Tried to get receiver object of a static call");
        }
        return object;
    }

    /**
     * @return whether it is a static method call or a dynamic
     */
    public boolean isStaticCall() {
        return object == null;
    }
    /**
     * @return Method called by Expression.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * @return Arguments given to the method.
     */
    public List<Expression> getArguments() {
        return arguments;
    }

    /**
     * @return the compile-time class (type) to which the method belongs
     */
    public JavaClass getJavaClass() {
        return javaClass;
    }

    /**
     * @return CallExpression in form [obj/class].methodName(param1, ...);
     */
    @Override
    public String toString() {
        String argString = "";
        for (int i = 0; i < arguments.size(); i++) {
            argString += arguments.get(i); // not efficient, but does the job
            if (i < arguments.size() - 1) {
                argString += ", ";
            }
        }
        if (isStaticCall()) {
            return javaClass.getName().toString() + "." + method.getName() + "(" + argString + ")";
        }
        return object.toString() + "." + method.getName() + "(" + argString + ")";
    }
}
