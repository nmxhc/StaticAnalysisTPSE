package SootUp;

import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootClass;
import sootup.core.model.SootField;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.views.JavaView;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class InternalUtil {

    /**
     * Loads a class from demo/ as a SootClass object or returns Optional.empty() if not found
     * @param className (without .java)
     * @return A valid SootClass object or an exception
     */
    public static SootClass loadClass(String className) {
        Path pathToBinary = Paths.get("demo/"); //brauchen ober-ordner incl package folder
        //intern compilieren
        AnalysisInputLocation inputLocation = PathBasedAnalysisInputLocation.create(pathToBinary, null);

        JavaView view = new JavaView(inputLocation);

        // Create a signature for the class we want to analyze
        ClassType classType = view.getIdentifierFactory().getClassType(className);

        // Check if the class "Test" is present in the project.
        if (view.getClass(classType).isEmpty()) {
            throw new RuntimeException("Class not found: " + classType.getClassName());
        }

        return view.getClass(classType).get();
    }

    /**
     *
     * @param c The class to look at
     * @return Its methods
     */
    public static Set<? extends SootMethod> getMethods(SootClass c) {
        return c.getMethods();
    }

    /**
     *
     * @param c The class to look at
     * @return Its attributes
     */
    public static Set<? extends SootField> getFields(SootClass c) {
        return c.getFields();
    }

    public static List<Stmt> getStatements(SootMethod m) {
        return m.getBody().getStmts();
    }
}