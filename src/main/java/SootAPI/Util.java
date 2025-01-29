package SootAPI;

import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.common.stmt.Stmt;
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
    public static AnalysedPackage loadPackage(String path) {
        Path pathToBinary = Paths.get(path);
        AnalysisInputLocation inputLocation = PathBasedAnalysisInputLocation.create(pathToBinary, null);
        JavaView view = new JavaView(inputLocation);
        Collection<JavaSootClass> availableClasses = view.getClasses();

        /* Convert each class to the format of our API */
        List<AnalysedClass> classes = new ArrayList<>();
        for (JavaSootClass c : availableClasses) {
            classes.add(convertJavaSootClassToAnalysedClass(c));
        }

        return new AnalysedPackage(addHierarchy(availableClasses, classes));
    }

    private static AnalysedClass convertJavaSootClassToAnalysedClass(JavaSootClass c) {
        List<AnalysedAttribute> attributes = new ArrayList<>();
        List<AnalysedMethod> methods = new ArrayList<>();

        for (JavaSootMethod m : c.getMethods()) {
            methods.add(convertJavaSootMethodToAnalysedMethod(m));
        }

        for (JavaSootField f : c.getFields()) {
            attributes.add(convertJavaSootFieldToAnalysedAttribute(f));
        }

        return new AnalysedClass(c.getName(), attributes, methods, null, null, c.isAbstract(), c.isInterface());
    }


    private static List<AnalysedClass> addHierarchy(Collection<JavaSootClass> javaSootClasses, List<AnalysedClass> analysedClasses){
        for(AnalysedClass analysedClass : analysedClasses) {
            JavaSootClass sootClass = findSootClass(analysedClass.getName(), javaSootClasses);
            Optional<JavaClassType> superClass = sootClass.getSuperclass();
            if (superClass.isPresent()) {
                analysedClass.setExtendsClass(findAnalysedClass(superClass.get().getClassName(), analysedClasses));
            }

            if (sootClass.getInterfaces() != null) {
                List<AnalysedClass> interfaces = new LinkedList<>();

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

    private static AnalysedClass findAnalysedClass(String className, List<AnalysedClass> analysedClasses){
        for(AnalysedClass a : analysedClasses){
            if(a.getName().equals(className)){
                return a;
            }
        }
        throw new IllegalArgumentException("Error: not all super classes included in package");
    }


    private static AnalysedAttribute convertJavaSootFieldToAnalysedAttribute(JavaSootField f) {
        return new AnalysedAttribute(f.getType().toString(), f.getName());
    }


    private static AnalysedMethod convertJavaSootMethodToAnalysedMethod(JavaSootMethod m) {
        String returnType = m.getReturnType().toString();

        List<String> parameterTypes = new ArrayList<>();
        for (Type t : m.getParameterTypes()) {
            parameterTypes.add((t.toString()));
        }

        if (m.hasBody()) {
            List<AnalysedStatement> statements = new ArrayList<>();
            for (Stmt s : m.getBody().getStmts()) {
                statements.add(convertSootStmtToAnalysedStatement(s));
            }

            return new AnalysedMethod(m.getName(), returnType, parameterTypes, statements, false);
        }

        return new AnalysedMethod(m.getName(), returnType, parameterTypes, null, true);
    }


    private static AnalysedStatement convertSootStmtToAnalysedStatement(Stmt s) {
        //tbd
        //was soll bei Statements analisiert werden?
        return new IfStatement();
    }
}
