package AST.Expressions;

/**
 * Class for compare operations: ==, !=, <, >, <=, >=
 */
public class CompareExpression extends Expression {

    private final ComparisonOperator operator;
    private final Expression lhs;
    private final Expression rhs;

    public CompareExpression(Expression lhs, ComparisonOperator operator, Expression rhs) {
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }

    public Expression getLhs() {
        return lhs;
    }

    public Expression getRhs() {
        return rhs;
    }
}
