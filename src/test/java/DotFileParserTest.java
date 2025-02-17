import DotAPI.DotFileParser;
import DotAPI.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DotFileParserTest {
    @Test
    void testRead(){
        try {
            Assertions.assertEquals("digraph G {\n    a -> b -> c;\n    b -> d;\n}\n", DotFileParser.readDotFile("basicTests/basicGraph.dot"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testParse(){
        try {
            Graph<String> res = DotFileParser.parseDotFile("basicTests/basicGraph.dot");
            assertEquals(4, res.getNodes().size());
            assertEquals(3, res.getEdges().size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

