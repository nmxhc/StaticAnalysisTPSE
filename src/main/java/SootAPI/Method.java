package SootAPI;

import java.util.List;

public class Method {

    public final String name;
    public Type returnType;
    public List<Type> parameterTypes;

    public List<Statement> statements;

    public boolean hasBody;

    public Method(String name, Type returnType, List<Type> parameterTypes, List<Statement> statements, boolean hasBody) {
        this.hasBody = hasBody;
        this.name = name;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.statements = statements;
    }

}
