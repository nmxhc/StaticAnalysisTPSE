package AST.CodeStructure;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the analysed java project,
 * containing list of all packages in project.
 *
 * @see Package
 */
public class Project {
    protected List<Package> packages;

    /**
     * Create new project with empty list of packages.
     */
    public Project(){
        packages = new LinkedList<>();
    }

    /**
     * @return List of packages in Project.
     */
    public List<Package> getPackages() {
        return packages;
    }

    /**
     * Add package
     * @param packageToAdd to project.
     */
    public void addPackages(Package packageToAdd) {
        packages.add(packageToAdd);
    }
}
