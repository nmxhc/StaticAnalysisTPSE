package SootAPI;

import java.util.List;

/**
 * Analysed package, with contained classes.
 */
public class AnalysedPackage {

    protected List<AnalysedClass> classes;

    /**
     * New Package with
     * @param classes contained.
     */
    public AnalysedPackage(List<AnalysedClass> classes) {
        this.classes = classes;
    }

    /**
     * @return classes of package
     */
    public List<AnalysedClass> getClasses() {
        return classes;
    }
}
