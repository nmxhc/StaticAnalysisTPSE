import DotAPI.DotFileGenerator;
import org.junit.jupiter.api.Test;
import ReferenceImplementations.CHAReference;
import AST.CodeStructure.Util;

class AstChaTest {
    @Test
    void cha() {
        // var g = new CHAAnalysis("demo").run("ComplexTest", "main");
        // var g = CHAReference.run(Util.loadPackage("src/test/sources"), "Node", "expressionTester");
        var g = CHAReference.run(Util.loadPackage("demo"), "ComplexTest", "main");
        System.out.println(DotFileGenerator.generateDotString(g));
    }

}
