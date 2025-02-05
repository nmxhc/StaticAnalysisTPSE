package AST.Expressions;

import AST.CodeStructure.ClassDeclaration;
import java.util.List;

public class ConstructorExpression extends Expression {

    private final ClassDeclaration classDeclaration;
    private final List<Expression> arguments;

    public ConstructorExpression(ClassDeclaration classDeclaration, List<Expression> arguments) {
        this.classDeclaration = classDeclaration;
        this.arguments = arguments;
    }

    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }

    public List<Expression> getArguments() {
        return arguments;
    }
}
