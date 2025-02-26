package AST.Expressions;

import AST.CodeStructure.Attribute;
import AST.CodeStructure.JavaClass;

/**
 * Representing a method executed
 */
public class AttributeReference extends Variable {

    private final JavaClass referencedClass;
    private final Variable object;
    private final Attribute attribute;

    /**
     * Create new AttributeReference
     * @param attribute the attribute referenced
     * @param referencedClass
     * @param object the object to which it belongs, is null if the attribute is static
     */
    public AttributeReference(Attribute attribute, JavaClass referencedClass, Variable object) {
        this.attribute = attribute;
        this.referencedClass = referencedClass;
        this.object = object;
    }

    /**
     * @return the attribute that is referenced
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * If the attribute is non-static, this returns the variable in which object lives whose attribute is referenced here
     * @return the object
     * @throws RuntimeException if the attribute is a static attribute
     */
    public Variable getObject() {
        if (attribute.isStatic()) {
            throw new RuntimeException("Tried to get base object of static attribute " + attribute.getName());
        }
        return object;
    }

    @Override
    public String toString() {
        if (attribute.isStatic()) {
            return referencedClass.toString() + "." + attribute;
        }
        return object + "." + attribute;
    }
}
