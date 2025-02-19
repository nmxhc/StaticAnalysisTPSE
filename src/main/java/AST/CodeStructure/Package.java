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

    /**
     * @param name the name of the class to retrieve
     * @return the class that has that name
     * @throws RuntimeException if no such class exists
     */
    public JavaClass getClassByName(String name) {
        for (JavaClass c : classes) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        throw new RuntimeException("Class not found in package: " + name);
    }
}
