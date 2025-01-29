package SootAPI;

import fj.data.Java;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootClass;
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
import java.util.stream.Stream;

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

        return new AnalysedPackage(classes);
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

    private static void addHierarchyWrapper(Collection<JavaSootClass> javaSootClasses){
        for(JavaSootClass c : javaSootClasses){
            
        }
    }

    private static void addHierarchy(JavaSootClass c){
        AnalysedClass analysedClass = findClass(c.getName());
        Optional<JavaClassType> superClass = c.getSuperclass();
        if(superClass.isPresent()){
            analysedClass.setExtendsClass(findClass(superClass.get().getClassName()));
        }

        if(c.getInterfaces() != null){
            List<AnalysedClass> interfaces = new LinkedList<>();

            Set<? extends ClassType> sootInterfaces = c.getInterfaces();
            for(ClassType classType : sootInterfaces){
                interfaces.add(findClass(classType.getClassName()));
            }

            analysedClass.setImplementsInterfaces(interfaces);
        }
    }

    private static AnalysedClass findClass(String className){

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
