package AST.Expressions;

/**
 * Representing integer literals in analysed code.
 */
public class IntegerLiteral extends Expression {

    private final int value;

    /**
     * Create new IntegerLiteral with
     * @param value int value
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

    /**
     * @return Integer value
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
