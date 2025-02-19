import AST.CodeStructure.JavaClass;
import AST.CodeStructure.Package;
import AST.CodeStructure.Util;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SootAPITest {
    static Package analysedPackage;

    @BeforeAll
    public static void before(){
        analysedPackage = Util.loadPackage("src/test/sources");
    }

    @Test
    public void loadPackage(){
        assert(!analysedPackage.getClasses().isEmpty());
    }

    @Test
    public void allClassesFound(){
        List<String> names = new LinkedList<>();
        for (JavaClass c : analysedPackage.getClasses()) {
            names.add(c.getName());
        }

        assert(names.contains("DoubleLinkedQueue"));
        assert(names.contains("Queue"));
        assert(names.contains("HaltingProblemDecider"));
        assert(names.contains("QueueInt"));
        assert(names.contains("Node"));
        assert(names.contains("DoubleKeyNode"));
        assert(names.contains("NodeAbs"));
    }

    @Test
    public void abstractClassFound(){
        JavaClass[] analysedClasses = analysedPackage.getClasses().toArray(new JavaClass[0]);
        int i = 0;
        if(analysedClasses.length > 0)
            while(!Objects.equals(analysedClasses[i].getName(), "NodeAbs")) i++;

        assert(analysedClasses.length > 0&&analysedClasses[i].getClassDeclaration().isAbstract());
    }

    @Test
    public void interfaceFound(){
        JavaClass[] analysedClasses = analysedPackage.getClasses().toArray(new JavaClass[0]);
        int i = 0;
        if(analysedClasses.length > 0)
            while(!Objects.equals(analysedClasses[i].getName(), "QueueInt")) i++;

        assert(analysedClasses.length > 0&&analysedClasses[i].getClassDeclaration().isInterface());
    }

    // Mind. 4 Klassen: 1x abstract, 1x interface,
    // 1x normal, 1x unbekannte Implementierung
    // Jeden return-Typ: void, int, boolean, String, Klasse
    // Jede Expression (auch in allen Formen z.B. +-*/) einmal
    // Jede Art von Statement einmal
    // Successors von BasicBlocks sind richtig
    // Methode mit 0, 1, n Parametern
}
