package SootUp;

import org.junit.jupiter.api.Test;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
import sootup.core.views.View;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.views.JavaView;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicTests {
    @Test
    void listMethods() {
        // Create a AnalysisInputLocation, which points to a directory. All class files will be loaded
        // from the directory
        Path pathToBinary = Paths.get("basicTests/");
        AnalysisInputLocation inputLocation = PathBasedAnalysisInputLocation.create(pathToBinary, null);

        // Create a view for project, which allows us to retrieve classes
        View view = new JavaView(inputLocation);

        // Create a signature for the class we want to analyze
        ClassType classType = view.getIdentifierFactory().getClassType("Test");

        // Check if the class "Test" is present in the project.
        assert(view.getClass(classType).isPresent());

        // Retrieve the specified class from the project.
        SootClass sootClass = view.getClass(classType).get();

        ArrayList<String> names = new ArrayList<String>();
        for (SootMethod s : sootClass.getMethods()) {
            names.add(s.getName());
        }
        assert(names.contains("inc"));
        assert(names.contains("add"));
        assert(names.contains("print42"));
        assert(names.contains("<init>"));
        assertEquals(4, names.size());
    }
}

