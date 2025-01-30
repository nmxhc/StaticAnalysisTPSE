package SootAPI;

import SootUp.InternalUtil;
import sootup.core.graph.StmtGraph;
import sootup.core.jimple.common.expr.AbstractConditionExpr;
import sootup.core.jimple.common.stmt.BranchingStmt;
import sootup.core.jimple.common.stmt.JIfStmt;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;

import javax.swing.plaf.nimbus.State;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        SootClass c = InternalUtil.loadClass("BranchTest");

        for (SootMethod m : InternalUtil.getMethods(c)) {
            if (m.getName().equals("foo")) {
                for (int i = 0; i < stmtList.size(); i++) {
                    Stmt s = stmtList.get(i);
                    if (s instanceof JIfStmt ifStmt) {
                    }
                }
            }
        }
    }

    public IfStatement convertSootIfStatement(SootMethod method, JIfStmt statement) {
        List<Stmt> stmtList = InternalUtil.getStatements(method);
        AbstractConditionExpr condition = statement.getCondition(); // TODO


        /* Collect all statements of the then block */
        List<Statement> thenBlock = new LinkedList<>();


        /* Collect all statements of the else block */
        List<Statement> elseBlock = new LinkedList<>();
        List<Stmt> elseStmts = method.getBody().getBranchTargetsOf(statement); /* returns first statement of else branch */
        int firstElseStmtIndex = stmtList.indexOf(elseStmts.getFirst());

        StmtGraph g = method.getBody().getStmtGraph();

        List<Stmt> l = g.getBranchTargetsOf(statement);

        for (Stmt target : l) {
            System.out.println(l);
        }

        if (firstElseStmtIndex < 0) {
            throw new RuntimeException("Something, somewhere went horribly wrong"); /* should be impossible */
        }

        return new IfStatement(null, thenBlock, elseBlock);
    }
}
