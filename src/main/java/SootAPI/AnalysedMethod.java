package SootAPI;

import java.util.List;

public class AnalysedMethod {

    public final String name;
    public AnalysedType returnType;
    public List<AnalysedType> parameterTypes;

    public List<AnalysedStatement> statements;

    public boolean hasBody;

    public AnalysedMethod(String name, AnalysedType returnType, List<AnalysedType> parameterTypes, List<AnalysedStatement> statements, boolean hasBody) {
        this.hasBody = hasBody;
        this.name = name;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.statements = statements;
    }

}
