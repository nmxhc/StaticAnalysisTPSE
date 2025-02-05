package AST.CodeStructure;

import AST.Statements.BranchStatement;
import AST.Statements.Statement;
import AST.Types.BooleanType;
import AST.Types.IntType;
import AST.Types.RefType;
import AST.Types.Type;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.types.ClassType;
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
    public static AST.CodeStructure.Package loadPackage(String path) {
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

        return new ClassDeclaration(c.getName(), attributes, methods, c.isAbstract(), c.isInterface());
    }

    /**
     * Rewrote implementation to be pure. Now doesn't modify original classes, but rather returns a list of new classes
     * with the added information of which classes they extend and which interfaces they implement
     * @param javaSootClasses
     * @param analysedClasses
     * @return
     */
    private static List<ClassDeclaration> addHierarchy(Collection<JavaSootClass> javaSootClasses, List<ClassDeclaration> analysedClasses) {
        for(ClassDeclaration analysedClass : analysedClasses) {
            JavaSootClass sootClass = findSootClass(analysedClass.getName(), javaSootClasses);
            Optional<JavaClassType> superClass = sootClass.getSuperclass();
            if (superClass.isPresent()) {
                analysedClass.setExtendsClass(getClassDeclaration(superClass.get().getClassName(), analysedClasses));
            }

            if (sootClass.getInterfaces() != null) {
                List<ClassDeclaration> interfaces = new LinkedList<>();

                for (ClassType classType : sootClass.getInterfaces()) {
                    interfaces.add(getClassDeclaration(classType.getClassName(), analysedClasses));
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

    private static ClassDeclaration getClassDeclaration(String className, List<ClassDeclaration> analysedClasses){
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

    private static Type stringToType(String typeName, List<ClassDeclaration> availableClasses) {
        return switch (typeName) {
            case "int" -> new IntType();
            case "boolean" -> new BooleanType();
            default -> new RefType(getClassDeclaration(typeName, availableClasses));
        };
    }

    private static Method convertJavaSootMethodToAnalysedMethod(JavaSootMethod m, List<ClassDeclaration> availableClasses) {
        Type returnType = stringToType(m.getReturnType().toString(), availableClasses);

        List<Type> parameterTypes = new ArrayList<>(
                m.getParameterTypes().stream().map(t -> stringToType(t.toString(), availableClasses)).toList()
        );

        m.getBody().getStmtGraph().getStartingStmtBlock();

        // TODO

        if (m.hasBody()) {
            List<Statement> statements = new ArrayList<>();
            for (Stmt s : m.getBody().) {
                statements.add(convertSootStmtToAnalysedStatement(s));
            }

                return new Method(m.getName(), returnType, parameterTypes, statements, false);
        }

        return new Method(m.getName(), returnType, parameterTypes, null, true);
    }

    private static BasicBlock addSuccessors(BasicBlock basicBlock, sootup.core.graph.BasicBlock<?> sootBB) {
    }

    private static BasicBlock convertSootBBToBasicBlock(sootup.core.graph.BasicBlock<?> b) {
        List<Statement> statements = b.getStmts().stream().map(Util::convertSootStmtToAnalysedStatement).toList();

        return new BasicBlock(statements, null);
    }

    private static Statement convertSootStmtToAnalysedStatement(Stmt s) {
        // TODO

        throw new RuntimeException("convertSootStmtToAnalysedStatement not implemented");

        return new BranchStatement(null, null, null);
    }
}
