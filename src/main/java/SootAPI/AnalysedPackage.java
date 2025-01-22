package SootAPI;

import java.util.List;

public class AnalysedPackage {

    public List<AnalysedClass> classes;

    public AnalysedPackage(List<AnalysedClass> classes) {
        this.classes = classes;
    }

}
