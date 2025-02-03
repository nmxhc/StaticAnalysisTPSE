package AST;

import SootUp.InternalUtil;
import sootup.core.graph.StmtGraph;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.common.expr.AbstractConditionExpr;
import sootup.core.jimple.common.stmt.JIfStmt;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
import sootup.core.types.Type;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.JavaSootClass;
import sootup.java.core.JavaSootField;
import sootup.java.core.JavaSootMethod;
import sootup.java.core.types.JavaClassType;
import sootup.java.core.views.JavaView;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Util {

    /**
     * Analyses package located in project folder
     * @param path local path of package in folder
     * @return AnalysedPackage with all classes and methods contained
     */
    public static Package loadPackage(String path) {
        Path pathToBinary = Paths.get(path);
        AnalysisInputLocation inputLocation = PathBasedAnalysisInputLocation.create(pathToBinary, null);
        JavaView view = new JavaView(inputLocation);
        Collection<JavaSootClass> availableClasses = view.getClasses();

        /* Convert each class to the format of our API */
        List<ClassDeclaration> classes = new ArrayList<>();
        for (JavaSootClass c : availableClasses) {
            classes.add(convertJavaSootClassToAnalysedClass(c));
        }

        return new Package(addHierarchy(availableClasses, classes));
    }

    private static ClassDeclaration convertJavaSootClassToAnalysedClass(JavaSootClass c) {
        List<Attribute> attributes = new ArrayList<>();
        List<Method> methods = new ArrayList<>();

        for (JavaSootMethod m : c.getMethods()) {
            methods.add(convertJavaSootMethodToAnalysedMethod(m));
        }

        for (JavaSootField f : c.getFields()) {
            attributes.add(convertJavaSootFieldToAnalysedAttribute(f));
        }

        return new ClassDeclaration(c.getName(), attributes, methods, null, null, c.isAbstract(), c.isInterface());
    }


    private static List<ClassDeclaration> addHierarchy(Collection<JavaSootClass> javaSootClasses, List<ClassDeclaration> analysedClasses){
        for(ClassDeclaration analysedClass : analysedClasses) {
            JavaSootClass sootClass = findSootClass(analysedClass.getName(), javaSootClasses);
            Optional<JavaClassType> superClass = sootClass.getSuperclass();
            if (superClass.isPresent()) {
                analysedClass.setExtendsClass(findAnalysedClass(superClass.get().getClassName(), analysedClasses));
            }

            if (sootClass.getInterfaces() != null) {
                List<ClassDeclaration> interfaces = new LinkedList<>();

                for (ClassType classType : sootClass.getInterfaces()) {
                    interfaces.add(findAnalysedClass(classType.getClassName(), analysedClasses));
                }

                analysedClass.setImplementsInterfaces(interfaces);
            }
        }
        return analysedClasses;
    }

    private static JavaSootClass findSootClass(String className, Collection<JavaSootClass> javaSootClasses){
        for(JavaSootClass j : javaSootClasses) {
            if (j.getName().equals(className)) {
                return j;
            }
        }
        throw new IllegalArgumentException("Unexpected Error");
    }

    private static ClassDeclaration findAnalysedClass(String className, List<ClassDeclaration> analysedClasses){
        for(ClassDeclaration a : analysedClasses){
            if(a.getName().equals(className)){
                return a;
            }
        }
        throw new IllegalArgumentException("Error: not all super classes included in package");
    }


    private static Attribute convertJavaSootFieldToAnalysedAttribute(JavaSootField f) {
        return new Attribute(f.getType().toString(), f.getName());
    }


    private static Method convertJavaSootMethodToAnalysedMethod(JavaSootMethod m) {
        String returnType = m.getReturnType().toString();

        List<AST.Type> parameterTypes = new ArrayList<>();
        for (sootup.core.types.Type t : m.getParameterTypes()) {
            //TODO differentiate to primitive types
            parameterTypes.add(new RefType(t.toString()));
        }

        if (m.hasBody()) {
            List<Statement> statements = new ArrayList<>();
            for (Stmt s : m.getBody().getStmts()) {
                statements.add(convertSootStmtToAnalysedStatement(s));
            }

            return new Method(m.getName(), returnType, parameterTypes, statements, false);
        }

        return new Method(m.getName(), returnType, parameterTypes, null, true);
    }


    private static Statement convertSootStmtToAnalysedStatement(Stmt s) {
        //tbd
        //was soll bei Statements analisiert werden?
        return new IfStatement(null, null, null);
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
