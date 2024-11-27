package DOT.API;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Builds DOT-String from input Strings,
 * writes DOT-String to file
 */
public class DotFileGenerator {

    //Set to track nodes to ensure uniqueness
    private Set<String> nodes;

    public DotFileGenerator() {
        nodes = new HashSet<>();
    }

    /**
     * Generates a DOT string from the given input.
     * Input "A -> B/n" for edge from node A to B.
     *
     * @param input A string representing the graph in custom format
     * @return DOT string
     */
    public String generateDotString(String input) {
        StringBuilder dotString = new StringBuilder();

        //initialize the dot-structure
        dotString.append("digraph G {\n");

        //Split in line foreach /n
        String[] lines = input.split("\n");

        for (String line : lines) {
            //remove whitespaces
            line = line.trim();
            if (line.isEmpty()) continue;

            //Split line into node and edge (in the format "A -> B")
            String[] parts = line.split("->");
            if (parts.length == 2) {
                String node1 = parts[0].trim();
                String node2 = parts[1].trim();

                //Add nodes if not already present
                nodes.add(node1);
                nodes.add(node2);

                //Add edge to DOT string
                dotString.append("  ").append(node1).append(" -> ").append(node2).append(";\n");
            }
        }

        //Add nodes to DOT string
        for (String node : nodes) {
            dotString.append("  ").append(node).append(";\n");
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
    public void writeDotFile(String filename, String dotString) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(dotString);
        }
    }
}
