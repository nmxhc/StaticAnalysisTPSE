package AST.CodeStructure;

import java.util.List;

/**
 * Represents Java package of analysed code.
 * Contains list of classes in package.
 *
 * @see ClassDeclaration
 * @see Project
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
     * @return classes of package {@link ClassDeclaration}
     */
    public List<ClassDeclaration> getClasses() {
        return classes;
    }
}
