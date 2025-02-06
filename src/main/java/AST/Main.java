package AST;
import AST.CodeStructure.ClassDeclaration;
import AST.CodeStructure.Package;
import AST.CodeStructure.Util;
import SootUp.InternalUtil;
import sootup.core.graph.BasicBlock;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;

public class Main {
    public static void main(String[] args){
        Package analysedPackage = Util.loadPackage("src/test/sources");

        for (ClassDeclaration c : analysedPackage.getClasses()){
            System.out.println(c.getName());
        }


//        SootClass c = InternalUtil.loadClass("ComplexTest");
//
//        for (SootMethod m : c.getMethods()) {
//            if (m.getName().equals("main")) {
//                for (BasicBlock<?> bb : m.getBody().getStmtGraph().getBlocksSorted()) {
//                    System.out.println(bb.toString());
//                    System.out.println("Statements for this BB:");
//                    for (Stmt s : bb.getStmts()) {
//                        System.out.println(s.toString());
//                    }
//                }
//            }
//        }

    }

}