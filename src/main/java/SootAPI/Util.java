package SootAPI;

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
import sootup.java.core.views.JavaView;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Util {

    public static AnalysedPackage loadPackage(String path) {
        Path pathToBinary = Paths.get(path);
        AnalysisInputLocation inputLocation = PathBasedAnalysisInputLocation.create(pathToBinary, null);
        JavaView view = new JavaView(inputLocation);
        Collection<JavaSootClass> availableClasses = view.getClasses();

        /* Convert each class to the format of our API */
        List<AnalysedClass> classes = new ArrayList<>();
        for (JavaSootClass c : availableClasses) {
            classes.add(convertJavaSootClassToAnalysedClass(c));
            System.out.println(c.getName());
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

        return new AnalysedClass(c.getName(), attributes, methods);
    }

    private static AnalysedAttribute convertJavaSootFieldToAnalysedAttribute(JavaSootField f) {
        return new AnalysedAttribute(new AnalysedType(f.getType().toString()), f.getName());
    }

    private static AnalysedMethod convertJavaSootMethodToAnalysedMethod(JavaSootMethod m) {
        AnalysedType returnType = new AnalysedType(m.getBody().getMethodSignature().getType().toString());

        List<AnalysedType> parameterTypes = new ArrayList<>();
        for(Type t : m.getBody().getMethodSignature().getParameterTypes()){
            parameterTypes.add(new AnalysedType(t.toString()));
        }


        List<AnalysedStatement> statements = new ArrayList<>();
        for (Stmt s : m.getBody().getStmts()) {
            statements.add(convertSootStmtToAnalysedStatement(s));
        }

        return new AnalysedMethod(m.getName(), returnType, parameterTypes, statements);
    }

    private static AnalysedStatement convertSootStmtToAnalysedStatement(Stmt s) {
        //tbd
        return new AnalysedStatement();
    }
}
