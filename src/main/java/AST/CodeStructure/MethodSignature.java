package AST.CodeStructure;

import AST.Statements.Statement;
import AST.Types.Type;
import fj.data.Java;

import java.util.List;

/**
 * Signature object of {@link Method},
 * containing name, returnType and parameters of method.
 */
public class MethodSignature {

    private final String name;
    private Type returnType;
    private List<Type> parameters;

    /**
     * Create new MethodSignature with given
     * @param name of method.
     */
    public MethodSignature(String name) {
        this.name = name;
    }

    /**
     * Create new MethodSignature with given
     * @param name of method
     * @param returnType of method
     * @param parameters of method
     */
    public MethodSignature(String name, Type returnType, List<Type> parameters) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the return type
     */
    public Type getReturnType() {
        return returnType;
    }

    /**
     * This method doesn't return parameter names, as they are lost during compilation.
     * The parameters occur in the same order as in the code, so `foo(int a, String b)` would yield `[int, String]`
     * @return the types of the parameters
     */
    public List<Type> getParameters() {
        return parameters;
    }

    /**
     * @return signature in form 'returnType methodName (param1...)'
     */
    @Override
    public String toString() {
        StringBuilder parameterString = new StringBuilder();
        for (int i = 0; i < parameters.size(); i++) {
            parameterString.append(parameters.get(i).getName());
            if (i < parameters.size()-1) {
                parameterString.append(",");
            }
        }
        return returnType.getName() + " " + name + "(" + parameterString + ")";
    }

    /**
     * @param other MethodSignature to be compared
     * @return if name, return type and parameters are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof MethodSignature s) {
            return
                getName().equals(s.getName()) &&
                getReturnType().equals(s.getReturnType()) &&
                getParameters().equals(s.getParameters());
        } else {
            return false;
        }
    }

    /**
     * @return hash of MethodSignature.
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(getName(), getReturnType(), getParameters());
    }
}
