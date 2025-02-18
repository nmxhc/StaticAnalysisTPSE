package DotAPI;

import SootUp.CHAAnalysis;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        DotToHtmlGenerator.embedDotInHtml("output.dot", "basicTests/HtmlTest.html", "TestName");

    }

}
