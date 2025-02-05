import AST.CodeStructure.ClassDeclaration;
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
        for (ClassDeclaration c : analysedPackage.getClasses()) {
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
        ClassDeclaration[] analysedClasses = analysedPackage.getClasses().toArray(new ClassDeclaration[0]);
        int i = 0;
        while(!Objects.equals(analysedClasses[i].getName(), "NodeAbs")) i++;

        assert(analysedClasses[i].isAbstract());
    }

    @Test
    public void interfaceFound(){
        ClassDeclaration[] analysedClasses = analysedPackage.getClasses().toArray(new ClassDeclaration[0]);
        int i = 0;
        while(!Objects.equals(analysedClasses[i].getName(), "QueueInt")) i++;

        assert(analysedClasses[i].isInterface());
    }
}
