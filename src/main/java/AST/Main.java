package AST;
import AST.CodeStructure.*;
import AST.CodeStructure.Package;
import AST.Statements.Statement;
import SootUp.InternalUtil;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;

import javax.swing.plaf.nimbus.State;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Package p = Util.loadPackage("simple");
        for (JavaClass c : p.getClasses()) {
            if (c.hasClassDeclaration()) {
                ClassDeclaration cd = c.getClassDeclaration();
                for (Method m : cd.getMethods()) {
                    if (m.hasMethodDeclaration() && m.getName().equals("test")) {
                        System.out.println("Printing basic blocks for " + m.getName() + " in " + c.getName());
                        MethodDeclaration md = m.getMethodDeclaration();
                        ControlFlowGraph cfg = md.getControlFlowGraph();
                        for (BasicBlock bb : cfg.getBasicBlocks()) {
                            System.out.println("Printing statements for basicBlock");
                            for (Statement s : bb.getStatements()) {
                                System.out.println(s);
                            }
                        }
                        return;
                    }
                }
            }
        }
    }

}