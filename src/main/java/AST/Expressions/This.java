package AST.Expressions;

/**
 * Represents `this` in code
 */
public class This extends Variable {

    /**
     * @return '@this'
     */
    @Override
    public String toString() {
        return "@this";
    }
}
