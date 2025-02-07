package AST.Expressions;

/**
 * Representing String literals in analysed code.
 */
public class StringLiteral extends Expression {

    private final String value;

    /**
     *
     * @param value
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

}
