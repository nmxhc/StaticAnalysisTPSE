package AST.Expressions;

import AST.Types.Type;


/**
 * Representing a type-cast expressions in Java '(Type) operand'.
 */
public class CastExpression extends Expression {

    private final Type type;
    private final Expression operand;

    /**
     * Create new CastExpression with
     * @param type to be cast to
     * @param operand to be cast
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
     * @return the type the operand is cast to.
     */
    public Type getType() {
        return type;
    }

    /**
     * @return CastExpression in form (Type) operand.
     */
    @Override
    public String toString() {
        return "(" + getType().toString() + ") " + getOperand().toString();
    }
}
