package AST.Statements;

import AST.Expressions.Expression;

public class AssignStatement extends Statement {

    private Variable lhs;
    private Expression rhs;

    public AssignStatement(Variable lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Variable getLhs() {
        return lhs;
    }

    public Expression getRhs() {
        return rhs;
    }
}
