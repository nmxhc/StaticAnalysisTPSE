package AST.CodeStructure;

import AST.Types.Type;

import java.util.List;

public class MethodDeclaration {
    protected Type returnType;
    protected List<Type> parameters;
    protected ControlFlowGraph controlFlowGraph;
    protected boolean isAbstract;

    /**
     * New AnalysedMethod
     * @param returnType of method
     * @param parameters of method
     * @param controlFlowGraph of method
     * @param isAbstract method declared abstract
     */
    public MethodDeclaration(Type returnType, List<Type> parameters, ControlFlowGraph controlFlowGraph, boolean isAbstract) {
        this.parameters = parameters;
        this.returnType = returnType;
        this.controlFlowGraph = controlFlowGraph;
        this.isAbstract = isAbstract;
    }

    /**
     * @return returnType of method
     */
    public Type getReturnType() {
        return returnType;
    }

    /**
     * @return parameters of method
     */
    public List<Type> getParameters() {
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
