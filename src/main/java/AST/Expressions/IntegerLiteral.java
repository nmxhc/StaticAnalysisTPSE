package AST.Expressions;

/**
 * Representing integer literals in analysed code.
 */
public class IntegerLiteral extends Expression {

    private final int value;

    /**
     *
     * @param value
     */
    public IntegerLiteral(int value) {
        this.value = value;
    }

    /**
     * @return int value of this Integer.
     */
    public int getValue() {
        return value;
    }
}
