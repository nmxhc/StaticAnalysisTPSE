package AST.Expressions;

/**
 * Representing the null constant in analysed code.
 */
public class NullLiteral extends Expression {
    /**
     * @return 'null'
     */
    @Override
    public String toString() {
        return "null";
    }
}
