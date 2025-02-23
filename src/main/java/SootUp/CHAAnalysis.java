package SootUp;

import AST.CodeStructure.JavaClass;
import AST.CodeStructure.Method;
import AST.CodeStructure.Package;
import AST.CodeStructure.Util;
import DotAPI.Edge;
import DotAPI.Graph;
import DotAPI.Node;
import sootup.core.jimple.common.expr.AbstractInvokeExpr;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootClass;
import sootup.core.model.SootClassMember;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
import sootup.core.types.Type;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CHAAnalysis extends Analysis {
    public CHAAnalysis() {
        super();
    }


    /**
     * Performs Class Hierarchy Analysis (CHA) on the specified method in the given class file.
     * @param path The path of package to analyze.
     * @return An Optional containing the call graph if the method is found, or an empty Optional otherwise.
     */
    public static Optional<Graph<String>> CHA(String path) {
        Package p = Util.loadPackage(path);
        Graph<String> g = new Graph<String>();
        for (JavaClass c : p.getClasses()) {
            if ( Objects.equals(c.getName(), "Object")) continue;
            Node<String> n = new Node<>(c.getName());
            g.addNode(n);
            List<Method> methodsP = c.getClassDeclaration().getMethods();
            if (!methodsP.isEmpty()) {
                for (Method m : methodsP) {
                    AddMethodToGraph(g, c, m);
                }
            }

        }
        return Optional.of(g);
    }

    private static void AddMethodToGraph(Graph<String> g, JavaClass javaClass, Method m) {
        Node<String> methodNode = new Node<>("m_" + m.getName());
        Node<String> cNode = new Node<>(javaClass.getName());
        JavaClass parent = javaClass.getClassDeclaration().getExtendsClass();
        if (parent == null){
            return;
        } else if ( !Objects.equals(parent.getName(), "Object")){
            g.addEdgeS(new Node<>(parent.getName()), cNode);
        }

        if (!Objects.equals(parent.getName(), "Object")&&parent.getClassDeclaration().getMethods().contains(m)) {
            if (!g.getEdges().contains(new Edge<>(methodNode, new Node<>(parent.getName())))) {
                AddMethodToGraph(g, parent, m);
            }
        } else {
            if ( Objects.equals(m.getName(), "<init>")) return;
            g.addEdgeS(cNode, methodNode);
        }
    }


    /**
     * Retrieves the set of constructed types.
     * @return A set of constructed types.
     */
    protected Set<Type> getConstructedTypes() {
        return null;
    }
}
