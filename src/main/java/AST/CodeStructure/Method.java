package AST.CodeStructure;

import AST.Statements.Statement;
import AST.Types.Type;

import java.util.List;

/**
 * Represents a Java Method of analysed code
 *
 * @see ClassDeclaration
 * @see ControlFlowGraph
 * @see BasicBlock
 */
public class Method {

    protected String name;
    protected Type returnType;
    protected List<Parameter> parameters;
    protected ControlFlowGraph controlFlowGraph;
    protected boolean isAbstract;

    /**
     * New AnalysedMethod
     * @param name of method
     * @param returnType of method
     * @param parameters of method
     * @param controlFlowGraph of method
     * @param isAbstract method declared abstract
     */
    public Method(String name, Type returnType, List<Parameter> parameters, ControlFlowGraph controlFlowGraph, boolean isAbstract) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.controlFlowGraph = controlFlowGraph;
        this.isAbstract = isAbstract;
    }

    /**
     * @return name of method
     */
    public String getName() {
        return name;
    }

    /**
     * @return returnType of method {@link Type}
     */
    public Type getReturnType() {
        return returnType;
    }

    /**
     * @return parameters of method {@link Parameter}
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * @return control flow graph of method
     */
    public ControlFlowGraph getControlFlowGraph() {
        return controlFlowGraph;
    }

    /**
     * @return method declared abstract
     */
    public boolean isAbstract() {
        return isAbstract;
    }
}
