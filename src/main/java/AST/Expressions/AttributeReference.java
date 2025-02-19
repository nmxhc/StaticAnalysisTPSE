package AST.Expressions;

import AST.CodeStructure.Attribute;

public class AttributeReference extends Variable {

    private final Variable object;
    private final Attribute attribute;

    /**
     *
     * @param attribute the attribute referenced
     * @param object the object to which it belongs, is null if the attribute is static
     */
    public AttributeReference(Attribute attribute, Variable object) {
        this.attribute = attribute;
        this.object = object;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public Variable getObject() {
        if (attribute.isStatic()) {
            throw new RuntimeException("Tried to get base object of static attribute " + attribute.getName());
        }
        return object;
    }

    @Override
    public String toString() {
        if (attribute.isStatic()) {
            return "CLASS." + attribute;
        }
        return object + "." + attribute;
    }
}
