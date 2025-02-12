package AST.CodeStructure;

import java.util.List;

/**
 * Analysed package, with contained classes.
 */
public class Package {

    private final List<JavaClass> classes;

    /**
     * New Package with
     * @param classes contained.
     */
    public Package(List<JavaClass> classes) {
        this.classes = classes;
    }

    /**
     * @return classes of package
     */
    public List<JavaClass> getClasses() {
        return classes;
    }
}
