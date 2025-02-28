import AST.CodeStructure.Util;
import DotAPI.DotFileGenerator;
import ReferenceImplementations.CHAReference;
import ReferenceImplementations.RTAReference;
import org.junit.jupiter.api.Test;

public class AstRtaTest {
    @Test
    void rta() {
        // var g = new CHAAnalysis("demo").run("ComplexTest", "main");
        // var g = CHAReference.run(Util.loadPackage("src/test/sources"), "Node", "expressionTester");
        var g = RTAReference.run(Util.loadPackage("demo"), "ComplexTest", "main");
        System.out.println(DotFileGenerator.generateDotString(g));
    }
}
