package AST.Expressions;

/**
 * Class for String literals
 */
public class StringLiteral extends Expression {

    private final String value;

    public StringLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
