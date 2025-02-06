package AST.CodeStructure;

import AST.Statements.Statement;
import AST.Types.BooleanType;
import AST.Types.IntType;
import AST.Types.RefType;
import AST.Types.Type;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootField;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.JavaSootClass;
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
            classes.add(convertJavaSootClassToClassSkeleton(c));
        }

        return new Package(addSupplementaryInfo(availableClasses, classes));
    }

    private static ClassDeclaration convertJavaSootClassToClassSkeleton(JavaSootClass c) {
        return new ClassDeclaration(c.getName(), c.isAbstract(), c.isInterface());
    }

    /**
     * Updates the provided class skeletons with information on attributes, methods, super class, interfaces
     * @param javaSootClasses
     * @param analysedClasses
     * @return The *same* classes, but updated with the information
     */
    private static List<ClassDeclaration> addSupplementaryInfo(Collection<JavaSootClass> javaSootClasses, List<ClassDeclaration> analysedClasses) {
        for(ClassDeclaration analysedClass : analysedClasses) {

            JavaSootClass sootClass = findSootClass(analysedClass.getName(), javaSootClasses);

            // Add hierarchy info: Implemented interfaces and extended class
            Optional<JavaClassType> superClass = sootClass.getSuperclass();
            if (superClass.isPresent()) {
                analysedClass.setExtendsClass(getClassDeclaration(superClass.get().getClassName(), analysedClasses));
            }

            if (sootClass.getInterfaces() != null) {
                List<ClassDeclaration> interfaces = new ArrayList<>();

                for (ClassType classType : sootClass.getInterfaces()) {
                    interfaces.add(getClassDeclaration(classType.getClassName(), analysedClasses));
                }

                analysedClass.setImplementsInterfaces(interfaces);
            }

            // Add methods
            List<Method> methods = new ArrayList<>();
            for (SootMethod m : sootClass.getMethods()) {
                methods.add(convertJavaSootMethodToAnalysedMethod(m, analysedClasses));
            }
            analysedClass.setMethods(methods);

            // Add attributes
            List<Attribute> attributes = new ArrayList<>();
            for (SootField f : sootClass.getFields()) {
                attributes.add(convertJavaSootFieldToAnalysedAttribute(f, analysedClasses));
            }
            analysedClass.setAttributes(attributes);
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
        throw new IllegalArgumentException("Error: Tried to get class declaration of undefined class " + className);
    }


    private static Attribute convertJavaSootFieldToAnalysedAttribute(SootField f, List<ClassDeclaration> availableClasses) {
        return new Attribute(stringToType(f.getType().toString(), availableClasses), f.getName());
    }

    private static Type stringToType(String typeName, List<ClassDeclaration> availableClasses) {
        return switch (typeName) {
            case "int" -> new IntType();
            case "boolean" -> new BooleanType();
            default -> new RefType(getClassDeclaration(typeName, availableClasses));
        };
    }

    /**
     * Add successors and predecessors to basicBlocks
     * @param sootBasicBlocks
     * @param basicBlocks
     */
    public static void addInfoToBasicBlocks(List<? extends sootup.core.graph.BasicBlock<?>> sootBasicBlocks, List<BasicBlock> basicBlocks) {
        for (int i = 0; i < sootBasicBlocks.size(); i++) {
            List<BasicBlock> successors = new ArrayList<>();
            for (sootup.core.graph.BasicBlock<?> successor : sootBasicBlocks.get(i).getSuccessors()) {
                successors.add(basicBlocks.get(sootBasicBlocks.indexOf(successor)));
            }
            basicBlocks.get(i).setSuccessors(successors);

            List<BasicBlock> predecessors = new ArrayList<>();
            for (sootup.core.graph.BasicBlock<?> predecessor : sootBasicBlocks.get(i).getPredecessors()) {
                predecessors.add(basicBlocks.get(sootBasicBlocks.indexOf(predecessor)));
            }
            basicBlocks.get(i).setPredecessors(predecessors);
        }
    }

    private static Method convertJavaSootMethodToAnalysedMethod(SootMethod m, List<ClassDeclaration> availableClasses) {
        Type returnType = stringToType(m.getReturnType().toString(), availableClasses);

        List<Parameter> parameters = new ArrayList<>(
                m.getParameterTypes()
                        .stream()
                        .map(t -> new Parameter(t.toString(), stringToType(t.toString(), availableClasses))
                        ).toList()
        );

        if (m.isAbstract()) {
            return new Method(m.getName(), returnType, parameters, null, true);
        }

        // from here on: m is not abstract, so extract the CFG

        /* used var in this case, couldn't get the types right,
           might be a bug / design issue in Soot or Java */
        var sootBasicBlocks = m.getBody().getStmtGraph().getBlocksSorted();
        List<BasicBlock> basicBlocks = new ArrayList<>();

        for (sootup.core.graph.BasicBlock<?> sootBB : sootBasicBlocks) {
            basicBlocks.add(sootBasicBlockToBasicBlockSkeleton(sootBB));
        }

        // now 'basicBlocks' and 'sootCFG.getBlockSorted()' correspond 1 to 1.

        addInfoToBasicBlocks(sootBasicBlocks, basicBlocks);

        return new Method(m.getName(),
                returnType,
                parameters,
                new ControlFlowGraph(basicBlocks, basicBlocks.get(0)),
                false
        );
    }

    private static BasicBlock sootBasicBlockToBasicBlockSkeleton(sootup.core.graph.BasicBlock<?> b) {
        List<Statement> statements = b.getStmts().stream().map(Util::convertSootStmtToAnalysedStatement).toList();

        return new BasicBlock(statements);
    }

    private static Statement convertSootStmtToAnalysedStatement(Stmt s) {
        System.out.println(s);

        return null;
    }
}
