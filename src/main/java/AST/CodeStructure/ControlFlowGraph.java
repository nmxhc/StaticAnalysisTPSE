package AST.CodeStructure;

import java.util.List;

public class ControlFlowGraph {

    private List<BasicBlock> basicBlocks;
    private BasicBlock entryBlock;

    /**
     * Create new Control Flow Count (Count uwu)
     * with given
     * @param basicBlocks list of basicBlocks
     * @param entryBlock basicBlock at beginning
     */
    public ControlFlowGraph(List<BasicBlock> basicBlocks, BasicBlock entryBlock) {
        this.basicBlocks = basicBlocks;
        this.entryBlock = entryBlock;
    }

    /**
     * @return all basic blocks of the cfg
     */
    public List<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }

    /**
     * @return the entry block of the cfg
     */
    public BasicBlock getEntryBlock() {
        return entryBlock;
    }
}
