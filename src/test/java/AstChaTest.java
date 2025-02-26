import SootUp.CHAAnalysis;
import DotAPI.Graph;
import DotAPI.DotFileGenerator;
import org.junit.jupiter.api.Test;

class AstChaTest {
    @Test
    void cha() {
        var g = new CHAAnalysis("demo").run("ComplexTest", "main");
        assert(g.isPresent());
        System.out.println(DotFileGenerator.generateDotString(g.get()));
    }

}
