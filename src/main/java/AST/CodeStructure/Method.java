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
    protected JavaClass javaClass;
    protected Type returnType;
    protected List<Type> parameters;
    protected ControlFlowGraph controlFlowGraph;
    protected boolean isAbstract;

    public Method(String name) {
        this.name = name;
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
     * @return the control flow graph of the method, if it isn't abstract
     * @throws RuntimeException if it's abstract
     */
    public ControlFlowGraph getControlFlowGraph() {
        if (isAbstract) {
            throw new RuntimeException("Tried to get body of abstract method " + name);
        }
        return controlFlowGraph;
    }

    /**
     * @return whether its abstract
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    public JavaClass getJavaClass() {
        return javaClass;
    }

    @Override
    public String toString() {
        StringBuilder parameterString = new StringBuilder();
        for (int i = 0; i < parameters.size(); i++) {
            parameterString.append(parameters.get(i).getName());
            if (i < parameters.size()-1) {
                parameterString.append(",");
            }
        }
        return javaClass.getName() + "." + name; // + "(" + parameterString + ") -> " + returnType.getName();
    }
}
