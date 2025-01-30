package SootAPI;

import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.JavaSootClass;
import sootup.java.core.JavaSootField;
import sootup.java.core.JavaSootMethod;
import sootup.java.core.views.JavaView;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Util {

    public static Package loadPackage(String path) {
        Path pathToBinary = Paths.get(path);
        AnalysisInputLocation inputLocation = PathBasedAnalysisInputLocation.create(pathToBinary, null);
        JavaView view = new JavaView(inputLocation);
        Collection<JavaSootClass> availableClasses = view.getClasses();

        /* Convert each class to the format of our API */
        List<Class> classes = new ArrayList<>();
        for (JavaSootClass c : availableClasses) {
            classes.add(convertJavaSootClassToAnalysedClass(c));
        }

        return new Package(classes);
    }

    private static Class convertJavaSootClassToAnalysedClass(JavaSootClass c) {
        List<Attribute> attributes = new ArrayList<>();
        List<Method> methods = new ArrayList<>();

        for (JavaSootMethod m : c.getMethods()) {
            methods.add(convertJavaSootMethodToAnalysedMethod(m));
        }

        for (JavaSootField f : c.getFields()) {
            attributes.add(convertJavaSootFieldToAnalysedAttribute(f));
        }

        return new Class(c.getName(), attributes, methods);
    }

    private static Attribute convertJavaSootFieldToAnalysedAttribute(JavaSootField f) {
        return new Attribute(new Type(f.getType().toString()), f.getName());
    }

    private static Method convertJavaSootMethodToAnalysedMethod(JavaSootMethod m) {
        Type returnType = new Type(m.getReturnType().toString());

        List<Type> parameterTypes = new ArrayList<>();
        for (sootup.core.types.Type t : m.getParameterTypes()) {
            parameterTypes.add(new Type(t.toString()));
        }

        if (m.hasBody()) {
            List<Statement> statements = new ArrayList<>();
            for (Stmt s : m.getBody().getStmts()) {
                statements.add(convertSootStmtToAnalysedStatement(s));
            }

            return new Method(m.getName(), returnType, parameterTypes, statements, true);
        }

        return new Method(m.getName(), returnType, parameterTypes, null, false);
    }

    private static Statement convertSootStmtToAnalysedStatement(Stmt s) {
        //tbd
        return new Statement();
    }
}
