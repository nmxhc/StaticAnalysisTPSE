package AST.Expressions;

/**
 * Representing String literals in analysed code.
 */
public class StringLiteral extends Expression {

    private final String value;

    /**
     * Create new StringLiteral with
     * @param value of the String
     */
    public StringLiteral(String value) {
        this.value = value;
    }

    /**
     * @return value of this String.
     */
    public String getValue() {
        return value;
    }

    /**
     * @return '"value"'
     */
    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
