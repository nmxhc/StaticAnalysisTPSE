package SootAPI;

import java.util.List;

/**
 * Analysed package, with contained classes.
 */
public class Package {

    protected List<Class> classes;

    /**
     * New Package with
     * @param classes contained.
     */
    public Package(List<Class> classes) {
        this.classes = classes;
    }

    /**
     * @return classes of package
     */
    public List<Class> getClasses() {
        return classes;
    }
}
