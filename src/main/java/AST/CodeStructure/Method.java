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
    protected Type returnType;
    protected List<Type> parameters;
    protected ControlFlowGraph controlFlowGraph;
    protected boolean isAbstract;

    public Method(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Type getReturnType() {
        return returnType;
    }

    public List<Type> getParameters() {
        return parameters;
    }

    public ControlFlowGraph getControlFlowGraph() {
        if (isAbstract) {
            throw new RuntimeException("Tried to get body of abstract method " + name);
        }
        return controlFlowGraph;
    }

    public boolean isAbstract() {
        return isAbstract;
    }
}
