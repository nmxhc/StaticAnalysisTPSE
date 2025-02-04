package SootUp;

import java.nio.file.Path;
import java.nio.file.Paths;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
import sootup.core.views.View;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.views.JavaView;

public class BasicSetup {

    public static void main(String[] args) {
        // Create a AnalysisInputLocation, which points to a directory. All class files will be loaded
        // from the directory
        Path pathToBinary = Paths.get("demo/");
        AnalysisInputLocation inputLocation = PathBasedAnalysisInputLocation.create(pathToBinary, null);

        // Create a view for project, which allows us to retrieve classes
        View view = new JavaView(inputLocation);

        // Create a signature for the class we want to analyze
        ClassType classType = view.getIdentifierFactory().getClassType("Test");

        // Check if the class "Test" is present in the project.
        if (view.getClass(classType).isEmpty()) {
            System.out.println("Class not found!");
            return;
        }

        // Retrieve the specified class from the project.
        SootClass sootClass = view.getClass(classType).get();

        for (SootMethod s : sootClass.getMethods()) {
            System.out.println(s.getName());
        }
    }
}