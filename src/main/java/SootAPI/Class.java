package SootAPI;

import java.util.List;

public class Class {

    public final String name;

    public List<Attribute> attributes;
    public List<Method> methods;

    public Class(String name, List<Attribute> attributes, List<Method> methods) {
        this.name = name;
        this.attributes = attributes;
        this.methods = methods;
    }
}