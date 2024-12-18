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
    public static Graph<java.lang.String> parseDotString(java.lang.String dotString) {
        java.lang.String[] lines = dotString.split("\\r?\\n");
        if (!lines[0].equals("digraph G {")){
            return new Graph<String>();
        }
        Graph<String> g = new Graph<>();
        HashMap<String, Node<String>> nodes = new HashMap<String, Node<String>>();
        for (int i = 1; i < lines.length; i++){

            if (lines[i].contains("}")) {
                break;
            }
            java.lang.String[] nodesInLine = lines[i].split(" -> ");
            for (int j = 0; j < nodesInLine.length; j++){
                nodesInLine[j] = nodesInLine[j].replace(";", " ").strip();
                if (nodes.get(nodesInLine[j]) == null) {
                    Node<String> node = new Node<String>(nodesInLine[j]);
                    nodes.put(nodesInLine[j], node);
                    g.addNode(node);
                }
                if (j >= 1 ){
                    g.addEdge(nodes.get(nodesInLine[j-1]), nodes.get(nodesInLine[j]));
                }
            }
        }
        return g;
    }

    /**
     * Reads the DOT string from a file.
     *
     * @param filename  The name of the file to read from
     * @throws IOException If an error occurs during file reading
     * @return dotString The DOT format string
     */
    public static String readDotFile(String filename) throws IOException {
        return Files.readString(Path.of(filename));
    }

    /**
     * Parse the DOT string from a file.
     *
     * @param filename  The name of the file to read from
     * @throws IOException If an error occurs during file reading
     * @return graph
     */
    public static Graph<String> parseDotFile(String filename) throws IOException {
        return parseDotString(Files.readString(Path.of(filename)));
    }
}
