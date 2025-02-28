package AST.CodeStructure;

import AST.Statements.Statement;
import AST.Types.Type;
import AST.Types.RefType;
import fj.data.Java;

import java.util.List;

/**
 * Analysed Method containing
 * ---refType---
 * signature of method,
 * controlFlowGraph and
 * whether method isAbstract.
 *
 * @see MethodSignature
 * @see ControlFlowGraph
 */
public class Method {

    private final RefType refType;
    private final MethodSignature signature;
    protected ControlFlowGraph controlFlowGraph;
    protected boolean isAbstract;

    /**
     * Create new Method with
     * @param type class of Method
     * @param name of Method
     */
    public Method(RefType type, String name) {
        this.refType = type;
        this.signature = new MethodSignature(name);
    }

    /**
     * Create new Method with
     * @param type class of Method
     * @param signature of Method
     */
    public Method(RefType type, MethodSignature signature) {
        this.refType = type;
        this.signature = signature;
    }

    /**
     * @return this method's name.
     */
    public String getName() {
        return signature.getName();
    }

    /**
     * @return this method's return type.
     */
    public Type getReturnType() {
        return signature.getReturnType();
    }

    /**
     * This method doesn't return the declared parameter names, as they are lost during java compilation.
     * The parameters occur in the same order as in the code, so `foo(int a, String b)` would yield `[int, String]`
     * @return the types of the parameters
     */
    public List<Type> getParameters() {
        return signature.getParameters();
    }

    /**
     * @return the control flow graph of the method, if it isn't abstract
     * @throws RuntimeException if it's abstract
     */
    public ControlFlowGraph getControlFlowGraph() {
        if (isAbstract() || isUnknown()) {
            throw new RuntimeException("Tried to get body of abstract or unknown method " + getName());
        }
        return controlFlowGraph;
    }

    /**
     * @return whether method is abstract.
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * @return true if refType of this method is not known.
     */
    public boolean isUnknown() {
        return refType.isUnknown();
    }

    /**
     * @return
     */
    public JavaClass getJavaClass() {
        return refType.getClassType();
    }

    /**
     * @return method's signature.
     */
    public MethodSignature getSignature() {
        return signature;
    }

    /**
     * @return refType of this method.
     */
    public RefType getRefType() {
        return refType;
    }

    @Override
    public String toString() {
        return "<" + refType.getName() + ": " + signature.toString() + ">";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Method m) return getRefType().equals(m.getRefType()) && getSignature().equals(m.getSignature());
        else return false;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(getRefType(), getSignature());
    }
}
