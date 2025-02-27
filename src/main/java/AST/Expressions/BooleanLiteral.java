package AST.Expressions;

/**
 * Representing a final boolean literal of an analysed expression.
 */
public class BooleanLiteral extends Expression {

    private final boolean value;

    /**
     * @param value of BooleanLiteral.
     */
    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    /**
     * @return boolean value of literal.
     */
    public boolean getValue() {
        return value;
    }

}
