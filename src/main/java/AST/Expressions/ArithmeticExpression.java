package AST.Expressions;


/**
 * Class for arithmetic expressions in Java, that is: +, -, *, /
 */
public class ArithmeticExpression extends Expression {

    private final ArithmeticOperator operator;
    private final Expression lhs;
    private final Expression rhs;

    public ArithmeticExpression(Expression lhs, ArithmeticOperator operator, Expression rhs) {
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public ArithmeticOperator getOperator() {
        return operator;
    }

    public Expression getLhs() {
        return lhs;
    }

    public Expression getRhs() {
        return rhs;
    }

}
