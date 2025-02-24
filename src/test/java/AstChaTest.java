import AST.CodeStructure.JavaClass;
import AST.CodeStructure.Package;
import AST.CodeStructure.Util;
import DotAPI.Graph;
import DotAPI.Node;
import org.junit.jupiter.api.Test;

class AstChaTest {
    @Test
    void cha() {
        Package p = Util.loadPackage("demo");
        Graph<String> g = new Graph<String>();
        for (JavaClass c : p.getClasses()) {
            Node<String> n = new Node<>(c.getName());
            g.addNode(n);
            if (c.hasClassDeclaration()) {
                JavaClass parent = c.getClassDeclaration().getExtendsClass();
                if (parent != null) {
                    g.addEdge(n,new Node<>(parent.getName()) );
                }
            }
        }


    }

}
