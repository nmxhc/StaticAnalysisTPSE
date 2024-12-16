package DOT.API;

// Importing required classes
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.lang.String.*;


/**
 * reads DOT-String from file,
 * Parses DOT-String from input Strings
 */
public class DotFileParser {

    /**
     * Parses a simple DOT-String to our graph format.
     *
     * @param dotString the graph in DOT format
     * @return graph
     */
    public static <String> Graph<String> parseDotString(java.lang.String dotString) {

        java.lang.String[] lines = dotString.split("\\r?\\n");
        if (lines[0] != "digraph G {"){
            return new Graph<String>();
        }
        Graph<String> g = new Graph<>();
        HashMap<String, Node<String>> nodes = new HashMap<String, Node<String>>();
        for (int i = 1; i < lines.length; i++){
            java.lang.String[] nodesInLine = lines[i].split(" -> ");
            for (i = 0; i < nodesInLine.length; i++){
                if (nodes.get((String) nodesInLine[i]) == null) {
                    Node<String> node = new Node<String>();
                    node.setValue((String) nodesInLine[i]);
                    nodes.put((String) nodesInLine[i], node);
                    g.addNode(node);
                }
                if (i >= 1 ){
                    g.addEdge(nodes.get((String) nodesInLine[i-1]), nodes.get((String) nodesInLine[i]));
                }
            }
        }
        return g;
    }

    /**
     * Reads the DOT string from a file.
     *
     * @param filename  The name of the file to read from
     * @throws IOException If an error occurs during file writing
     * @return dotString The DOT format string
     */
    public static String readDotFile(String filename) throws IOException {
        return Files.readString(Path.of(filename));
    }
}
