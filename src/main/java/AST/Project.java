package AST;

import java.util.List;

/**
 * contains packages
 */
public class Project {
    protected List<Package> packages;

    public Project(){

    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
}
