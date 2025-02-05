package AST.Expressions;

/**
 * Class for integer literals
 */
public class IntegerLiteral extends Expression {

    private final int value;

    public IntegerLiteral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
