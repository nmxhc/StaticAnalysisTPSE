package AST.Statements;

import AST.CodeStructure.BasicBlock;
import AST.Expressions.IntegerLiteral;

/**
 * Represents a switch branch in a {@link AST.CodeStructure.BasicBlock}
 * of analysed java code.
 * case value: target
 * OR
 * default: target
 */
public class SwitchBranch {
    private final boolean isDefault;
    private final IntegerLiteral value;
    private final BasicBlock target;

    /**
     * Create new SwitchBranch with
     * @param target block to execute
     */
    public SwitchBranch(BasicBlock target) {
        this.isDefault = true;
        this.value = null;
        this.target = target;
    }

    /**
     * Create new SwitchBranch with
     * @param value used in switch
     * @param target block to execute
     */
    public SwitchBranch(IntegerLiteral value, BasicBlock target) {
        this.isDefault = false;
        this.value = value;
        this.target = target;
    }

    /**
     * @return if the branch is the default branch.
     */
    public boolean isDefault() {
        return isDefault;
    }

    /**
     * @return value of the switch case.
     */
    public IntegerLiteral getValue() {
        return value;
    }

    /**
     * @return target block of the switch case.
     */
    public BasicBlock getTargetBlock() {
        return target;
    }

    /**
     * @return switch branch in String.
     */
    @Override
    public String toString() {
        return (isDefault() ? "default" : "case " + getValue().toString()) + ": " + getTargetBlock().toString();
    }
}
