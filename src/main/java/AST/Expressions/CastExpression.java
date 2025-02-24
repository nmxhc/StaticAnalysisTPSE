package AST.Expressions;

import AST.Types.Type;


/**
 * Representing cast expressions in Java
 *
 * (Type) operand
 */
public class CastExpression extends Expression {

    private final Type type;
    private final Expression operand;

    /**
     *
     * @param type
     * @param operand
     */
    public CastExpression(Type type, Expression operand) {
        this.type = type;
        this.operand = operand;
    }

    /**
     * @return operand of cast.
     */
    public Expression getOperand() {
        return operand;
    }

    /**
     * @return the type the operand is casted to.
     */
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "(" + getType().toString() + ") " + getOperand().toString();
    }
}
