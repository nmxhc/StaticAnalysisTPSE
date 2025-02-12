package AST.Expressions;

/**
 * Representing compare expressions in java.
 *
 * @see ComparisonOperator
 */
public class CompareExpression extends Expression {

    private final ComparisonOperator operator;
    private final Expression lhs;
    private final Expression rhs;

    /**
     *
     * @param lhs
     * @param operator
     * @param rhs
     */
    public CompareExpression(Expression lhs, ComparisonOperator operator, Expression rhs) {
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * @return operator used in expression.
     */
    public ComparisonOperator getOperator() {
        return operator;
    }

    /**
     * @return left-hand-side of expression.
     */
    public Expression getLhs() {
        return lhs;
    }

    /**
     * @return right-hand-side of expression.
     */
    public Expression getRhs() {
        return rhs;
    }
}
