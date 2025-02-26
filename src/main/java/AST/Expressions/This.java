package AST.Expressions;

/**
 * Represents `this` in code
 */
public class This extends Variable {

    @Override
    public String toString() {
        return "@this";
    }
}
