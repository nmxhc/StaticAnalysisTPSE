package AST.CodeStructure;

import AST.Statements.Statement;
import AST.Types.Type;
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

    protected JavaClass javaClass;
    protected ControlFlowGraph controlFlowGraph;
    protected boolean isAbstract;
    protected MethodSignature signature;

    public Method(String name) {
        this.signature = new MethodSignature(name);
    }

    public Method(MethodSignature signature) {
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
        if (isAbstract) {
            throw new RuntimeException("Tried to get body of abstract method " + getName());
        }
        return controlFlowGraph;
    }

    /**
     * @return whether its abstract
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    public JavaClass getJavaClass() {
        return javaClass;
    }

    public MethodSignature getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "<" + javaClass.getName() + ": " + signature.toString() + ">";
    }
}
