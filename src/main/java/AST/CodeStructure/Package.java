package AST.CodeStructure;

import java.util.List;

/**
 * Analysed package, with contained classes.
 */
public class Package {

    protected List<ClassDeclaration> classes;

    /**
     * New Package with
     * @param classes contained.
     */
    public Package(List<ClassDeclaration> classes) {
        this.classes = classes;
    }

    /**
     * @return classes of package
     */
    public List<ClassDeclaration> getClasses() {
        return classes;
    }
}
