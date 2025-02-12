package AST.Expressions;

import AST.Statements.Variable;
import AST.Types.Type;
import com.google.errorprone.annotations.Var;

/**
 * Representing a variable expression, i.e. declaration, in the analysed java code.
 */
public class VariableExpression extends Expression {

    private final Variable variable;

    /**
     *
     * @param variable
     */
    public VariableExpression(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }

    @Override
    public String toString() {
        return variable.toString();
    }
}