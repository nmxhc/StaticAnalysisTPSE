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
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"en\">\n");
        html.append("<head>\n");
        html.append("<meta charset=\"UTF-8\">\n");
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("<title>Embedded DOT Graph</title>\n");
        html.append("<script type=\"text/javascript\" src=\"https://cdn.jsdelivr.net/npm/graphviz@0.9.0/dist/graphviz.min.js\"></script>\n");
        html.append("</head>\n");
        html.append("<body>\n");

        // Embed the DOT content using Graphviz JS
        html.append("<h1>Graphviz DOT Representation</h1>\n");
        html.append("<div id=\"graph\"></div>\n");

        html.append("<script type=\"text/javascript\">\n");
        html.append("var dot = `").append(dotContent.replace("`", "\\`")).append("`;\n");
        html.append("var viz = new Viz();\n");
        html.append("viz.renderSVGElement(dot)\n");
        html.append("  .then(function(element) {\n");
        html.append("    document.getElementById('graph').appendChild(element);\n");
        html.append("  })\n");
        html.append("  .catch(error => { console.error(error); });\n");
        html.append("</script>\n");

        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }
}
