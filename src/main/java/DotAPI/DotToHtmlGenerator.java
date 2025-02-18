package DotAPI;

import java.io.*;
import java.nio.file.*;

public class DotToHtmlGenerator {

    // Method to embed the DOT content into an HTML file
    public static void embedDotInHtml(String dotFilePath, String htmlOutputPath, String graphName) {
        try {
            // Read the DOT file content
            String dotContent = new String(Files.readAllBytes(Paths.get(dotFilePath)));

            // Generate the HTML content with embedded DOT file
            String htmlContent = generateHtml(dotContent, graphName);

            // Write the HTML content to the output file
            Files.write(Paths.get(htmlOutputPath), htmlContent.getBytes());

            System.out.println("HTML file generated successfully: " + htmlOutputPath);
        } catch (IOException e) {
            System.err.println("An error occurred while embedding DOT in HTML: " + e.getMessage());
        }
    }

    // Method to generate HTML content with embedded DOT content
    private static String generateHtml(String dotContent, String graphName) {
        return """
                <!DOCTYPE html>
                <meta charset="utf-8">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Rendered Dot Graph</title>
                </head>
                <body>
                    <h1>"""
                + graphName +"""
                </h1>
                    
                    <script src="https://d3js.org/d3.v7.min.js" integrity="sha384-CjloA8y00+1SDAUkjs099PVfnY2KmDC2BZnws9kh8D/lX1s46w6EPhpXdqMfjK6i" crossorigin="anonymous"></script>
                    <script src="https://unpkg.com/@hpcc-js/wasm@2.20.0/dist/graphviz.umd.js" integrity="sha384-MuWg2iUw8i0NVYbS/E8m9g6C3mtfMFok9RDhu1F8MA7odUrz5J9AQktZ9IUERhKM" crossorigin="anonymous"></script>
                    <script src="https://unpkg.com/d3-graphviz@5.6.0/build/d3-graphviz.js" integrity="sha384-Oq7yaIx9v1EBL0o2ExmWc1LX7v4qsyulAZ38Pw/PBLKK0xavsLFTAf1zviZXOPBC" crossorigin="anonymous"></script>
                    <div id="graph" style="text-align: center;"></div>
                        <script>
                        
                        d3.select("#graph").graphviz()
                            .renderDot(`
                        """  + dotContent.replace("`", "\\`") + "`);\n" +
                        """
                        </script>
                </body>
                </html>
                """;
    }
}
