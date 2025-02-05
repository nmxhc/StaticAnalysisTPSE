package AST.CodeStructure;

import AST.Statements.Statement;
import AST.Types.Type;

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

    protected String name;
    protected Type returnType;
    protected List<Parameter> parameters; //CFG
    protected BasicBlock entryBlock;
    protected boolean isAbstract;

    /**
     * New AnalysedMethod
     * @param name of method
     * @param returnType of method
     * @param parameters of method
     * @param entryBlock of method
     * @param isAbstract method declared abstract
     */
    public Method(String name, Type returnType, List<Parameter> parameters, BasicBlock entryBlock, boolean isAbstract) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.entryBlock = entryBlock;
        this.isAbstract = isAbstract;
    }

    /**
     * @return name of method
     */
    public String getName() {
        return name;
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
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * @return statements of method
     */
    public BasicBlock getEntryBlock() {
        return entryBlock;
    }

    /**
     * @return method declared abstract
     */
    public boolean isAbstract() {
        return isAbstract;
    }
}
