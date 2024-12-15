package DOT.API;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Builds DOT-String from input Strings,
 * writes DOT-String to file
 */
public class DotFileGenerator {

    /**
     * Generates a DOT string from the given input.
     * Input "A -> B/n" for edge from node A to B.
     *
     * @param graph The graph in custom format
     * @return DOT string
     */
    public static <T> String generateDotString(Graph<T> graph) {
        StringBuilder dotString = new StringBuilder();

        //initialize the dot-structure
        dotString.append("digraph G {\n");

        //List all nodes in DOT string
        for (Node<T> node : graph.getNodes()) {
            dotString.append("  ").append(node.toString()).append(";\n");
        }

        //List edges
        for(Edge<T> edge : graph.getEdges()){
            dotString.append("  ").append(edge.getOriginNode()).append(" -> ").append(edge.getTargetNode()).append(";\n");
        }

        dotString.append("}\n");

        return dotString.toString();
    }

    /**
     * Writes the DOT string to a file.
     *
     * @param filename  The name of the file to write to
     * @param dotString The DOT format string
     * @throws IOException If an error occurs during file writing
     */
    public static void writeDotFile(String filename, String dotString) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(dotString);
        }
    }
}
