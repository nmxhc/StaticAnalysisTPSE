package SootAPI;

import java.util.List;

public class AnalysedClass {

    public List<AnalysedAttribute> attributes;
    public List<AnalysedMethod> methods;

    public AnalysedClass(List<AnalysedAttribute> attributes, List<AnalysedMethod> methods) {
        this.attributes = attributes;
        this.methods = methods;
    }
}
