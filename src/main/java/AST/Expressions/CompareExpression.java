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
     * Create new CompareExpression with
     * @param lhs of comparison
     * @param operator comparing arguments
     * @param rhs of comparison
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

    /**
     * @return CompareExpression in form 'lhs [operator] rhs'.
     */
    @Override
    public String toString() {
        return lhs + " " + switch (operator) {
            case EQ -> "==";
            case NEQ -> "!=";
            case LT -> "<";
            case GT -> ">";
            case LEQ -> "<=";
            case GEQ -> ">=";
        } + " " + rhs;

    }

}
