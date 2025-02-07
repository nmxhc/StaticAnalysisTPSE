package AST.CodeStructure;

import java.util.List;

/**
 * Represents a Control Flow Graph CFG of an analysed Java method.
 * The CFG contains a list of {@link BasicBlock},
 * along with an entry Block.
 *
 * @see Method
 * @see BasicBlock
 */
public class ControlFlowGraph {

    private List<BasicBlock> basicBlocks;
    private BasicBlock entryBlock;

    public ControlFlowGraph(List<BasicBlock> basicBlocks, BasicBlock entryBlock) {
        this.basicBlocks = basicBlocks;
        this.entryBlock = entryBlock;
    }

    /**
     * @return list of {@link BasicBlock} of Control Flow Graph
     */
    public List<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }

    /**
     * @return Entry Block {@link BasicBlock} of Control Flow Graph
     */
    public BasicBlock getEntryBlock() {
        return entryBlock;
    }
}
