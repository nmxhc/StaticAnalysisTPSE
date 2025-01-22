package SootAPI;

import java.util.List;

public class AnalysedMethod {

    public AnalysedType returnType;
    public List<AnalysedType> parameterTypes;

    public List<AnalysedStatement> statements;

    public AnalysedMethod(AnalysedType returnType, List<AnalysedType> parameterTypes, List<AnalysedStatement> statements) {
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.statements = statements;
    }

}
