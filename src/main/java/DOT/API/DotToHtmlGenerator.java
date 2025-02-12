package DOT.API;

import java.io.*;
import java.nio.file.*;

public class DotToHtmlGenerator {

    // Method to embed the DOT content into an HTML file
    public static void embedDotInHtml(String dotFilePath, String htmlOutputPath) {
        try {
            // Read the DOT file content
            String dotContent = new String(Files.readAllBytes(Paths.get(dotFilePath)));

            // Generate the HTML content with embedded DOT file
            String htmlContent = generateHtml(dotContent);

            // Write the HTML content to the output file
            Files.write(Paths.get(htmlOutputPath), htmlContent.getBytes());

            System.out.println("HTML file generated successfully: " + htmlOutputPath);
        } catch (IOException e) {
            System.err.println("An error occurred while embedding DOT in HTML: " + e.getMessage());
        }
    }

    // Method to generate HTML content with embedded DOT content
    private static String generateHtml(String dotContent) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Embedded DOT Graph</title>
                    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/graphviz@0.9.0/dist/graphviz.min.js"></script>
                </head>
                <body>
                    <!-- Embed the DOT content using Graphviz JS -->
                    <h1>Graphviz DOT Representation</h1>
                    <div id="graph"></div>
                
                    <script type="text/javascript">
                """ +
                "var dot = `" + dotContent.replace("`", "\\`") + "`;\n" +
                """    
                        var viz = new Viz();
                            viz.renderSVGElement(dot)
                              .then(function(element) {
                                document.getElementById('graph').appendChild(element);
                              })
                              .catch(error => { console.error(error); });
                            </script>
                        </body>
                        </html>
                        """;
    }
}
