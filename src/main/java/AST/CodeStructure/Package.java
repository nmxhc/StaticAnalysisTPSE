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

    public JavaClass getClassByName(String name) {
        for (JavaClass c : classes) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        throw new RuntimeException("Class not found in package: " + name);
    }
}
