package SootAPI;

import java.util.List;

public class AnalysedClass {

    public final String name;

    public List<AnalysedAttribute> attributes;
    public List<AnalysedMethod> methods;

    public AnalysedClass(String name, List<AnalysedAttribute> attributes, List<AnalysedMethod> methods) {
        this.name = name;
        this.attributes = attributes;
        this.methods = methods;
    }
}
