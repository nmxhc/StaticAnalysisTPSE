package AST;

import java.util.List;

public class IfStatement extends Statement {
    //replace with conditional branch statement
    public Expression condition;
    public List<Statement> thenBlock;
    public List<Statement> elseBlock;

    public IfStatement(Expression condition, List<Statement> thenBlock, List<Statement> elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public boolean hasElse() {
        return this.elseBlock != null;
    }
}