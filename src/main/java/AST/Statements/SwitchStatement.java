package AST.Statements;

import AST.Expressions.Expression;

import java.util.List;

/**
 * Represents a switch statement in a {@link AST.CodeStructure.BasicBlock}
 * of analysed java code.
 * switch(value) {
 *     ....
 * }
 */
public class SwitchStatement extends Statement {
    private final Expression key;
    private final List<SwitchBranch> branches;

    /**
     * Create new SwitchStatement with
     * @param key Expression switched upon
     * @param branches being switched to
     */
    public SwitchStatement(Expression key, List<SwitchBranch> branches) {
        this.key = key;
        this.branches = branches;
    }

    /**
     * @return expression that is being switched on.
     */
    public Expression getKey() {
        return key;
    }

    /**
     * @return branches of the switch statement.
     */
    public List<SwitchBranch> getBranches() {
        return branches;
    }

    /**
     * @return switch to String.
     */
    @Override
    public String toString() {
        String s = "switch (" + getKey().toString() + ") {";
        for (var branch : branches) {
            s += branch.toString();
        }
        s += "}";
        return s;
    }
}

