# Static Analysis Team Project



###  What the project does

This project's goal is creating an easy-to-use framework to implement static analysis algorithms on Java source code imported via SootUp.
The results of this analysis are to then be displayed using the DOT-format.
    
### Why the Project is Useful

This project serves to improve teaching in TU Darmstadt's **Static Analysis** course.
Static Analysis is the act of analysing source code regarding method calls and structure
rather than running it to visualize and improve the program's structure.
For the students to gain experience in implementing static analysis algorithms on Java code, a suitable framework is required.
To this day however, there is no appropriate API for this, as current solutions are too complex to be used in teaching.

### How users can get started with the project

Put the compile java file(s) in *demo/* folder, then use the implemented library of RTA or CHA for example in the main method:
            
            public class Main {            
                public static void main(String[] args) {

                
                   Graph<String> g = Analyses.CHA("demo/className", "methodname");
                   String s = DotFileGenerator.generateDotString(g);
                   
                    try {
                        DotFileGenerator.writeDotFile("output.dot", s);
                    } catch (Exception e) {
                        System.out.printf("Error writing file: %s\n", e.getMessage());
                    }
                }
            }

