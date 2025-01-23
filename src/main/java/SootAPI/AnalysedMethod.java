package SootAPI;

import java.util.List;

/**
 * Analysed Method containing
 * name of method,
 * returnType of method,
 * parameterTypes of method,
 * statements listed,
 * isAbstract method.
 */
public class AnalysedMethod {

    protected String name;
    protected String returnType;
    protected List<String> parameterTypes;
    protected List<AnalysedStatement> statements;
    protected boolean isAbstract;

    /**
     * New AnalysedMethod
     * @param name of method
     * @param returnType of method
     * @param parameterTypes of method
     * @param statements of method
     * @param isAbstract method declared abstract
     */
    public AnalysedMethod(String name, String returnType, List<String> parameterTypes, List<AnalysedStatement> statements, boolean isAbstract) {
        this.name = name;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.statements = statements;
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
    public String getReturnType() {
        return returnType;
    }

    /**
     * @return parameters of method
     */
    public List<String> getParameterTypes() {
        return parameterTypes;
    }

    /**
     * @return statements of method
     */
    public List<AnalysedStatement> getStatements() {
        return statements;
    }

    /**
     * @return method declared abstract
     */
    public boolean isAbstract() {
        return isAbstract;
    }
}
