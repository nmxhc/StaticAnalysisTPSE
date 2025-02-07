package AST.Expressions;


/**
 * Representing arithmetic expressions in Java, i.e. +, -, *, /
 *
 * @see ArithmeticOperator
 */
public class ArithmeticExpression extends Expression {

    private final ArithmeticOperator operator;
    private final Expression lhs;
    private final Expression rhs;

    /**
     *
     * @param lhs
     * @param operator
     * @param rhs
     */
    public ArithmeticExpression(Expression lhs, ArithmeticOperator operator, Expression rhs) {
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * @return arithmetic operator of expression.
     */
    public ArithmeticOperator getOperator() {
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
