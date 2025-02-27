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
     * Create new ArithmeticExpression with given
     * @param lhs of Expression
     * @param operator used in Expression
     * @param rhs of Expression
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

    /**
     * @return ArithmeticExpression in form 'lhs [operator] rhs'
     */
    @Override
    public String toString() {
        return lhs + " " + switch (operator) {
            case ADD -> "+";
            case SUB -> "-";
            case MUL -> "*";
            case DIV -> "/";
        } + " " + rhs;
    }
}
