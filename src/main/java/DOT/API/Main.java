package DOT.API;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String input = "A -> B\nB -> C\nC -> A\nA -> D";

        DotFileGenerator generator = new DotFileGenerator();
        String dotString = generator.generateDotString(input);
        System.out.println(dotString);

        // Optionally, write to a file
        try {
            generator.writeDotFile("output.dot", dotString);
            System.out.println("DOT file has been written to output.dot");
        } catch (
                IOException e) {
            System.err.println("Error writing DOT file: " + e.getMessage());
        }
    }

}
