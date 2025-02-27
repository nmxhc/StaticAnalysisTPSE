package AST.CodeStructure;

import AST.Statements.Statement;
import AST.Types.Type;
import AST.Types.RefType;
import fj.data.Java;

import java.util.List;

/**
 * Analysed Method containing
 * name of method,
 * returnType of method,
 * parameterTypes of method,
 * statements listed,
 * isAbstract method.
 */
public class Method {

    private final RefType refType;
    private final MethodSignature signature;
    protected ControlFlowGraph controlFlowGraph;
    protected boolean isAbstract;

    /**
     * Create new Method with
     * @type class of Method
     * @param name of Method
     */
    public Method(RefType type, String name) {
        this.refType = type;
        this.signature = new MethodSignature(name);
    }

    /**
     * Create new Method with
     * @type class of Method
     * @param signature of Method
     */
    public Method(RefType type, MethodSignature signature) {
        this.refType = type;
        this.signature = signature;
    }

    /**
     * @return the name
     */
    public String getName() {
        return signature.getName();
    }

    /**
     * @return the return type
     */
    public Type getReturnType() {
        return signature.getReturnType();
    }

    /**
     * This method doesn't return parameter names, as they are lost during compilation.
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
     * @return whether its abstract
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    public boolean isUnknown() {
        return refType.isUnknown();
    }

    public JavaClass getJavaClass() {
        return refType.getClassType();
    }

    public MethodSignature getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "<" + refType.getName() + ": " + signature.toString() + ">";
    }
}
