package AST.CodeStructure;

import java.util.List;

public class ControlFlowGraph {

    private List<BasicBlock> basicBlocks;
    private BasicBlock entryBlock;

    public ControlFlowGraph(List<BasicBlock> basicBlocks, BasicBlock entryBlock) {
        this.basicBlocks = basicBlocks;
        this.entryBlock = entryBlock;
    }

    public List<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }

    public BasicBlock getEntryBlock() {
        return entryBlock;
    }
}
