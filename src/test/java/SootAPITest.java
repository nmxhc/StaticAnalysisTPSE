import SootAPI.AnalysedClass;
import SootAPI.AnalysedPackage;
import SootAPI.Util;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SootAPITest {
    @Test
    public void loadPackage(){
        AnalysedPackage analysedPackage = Util.loadPackage("src/test/sources");
        assert(!analysedPackage.getClasses().isEmpty());
    }

    @Test
    public void allClassesFound(){
        AnalysedPackage analysedPackage = Util.loadPackage("src/test/sources");

        List<String> names = new LinkedList<>();
        for (AnalysedClass c : analysedPackage.getClasses()) {
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
    public void abstractFound(){
        AnalysedPackage analysedPackage = Util.loadPackage("src/test/sources");
        AnalysedClass[] analysedClasses = analysedPackage.getClasses().toArray(new AnalysedClass[0]);
        int i = 0;
        while(!Objects.equals(analysedClasses[i].getName(), "NodeAbs")) i++;

        assert(analysedClasses[i].isAbstract());
    }

    @Test
    public void interfaceFound(){
        AnalysedPackage analysedPackage = Util.loadPackage("src/test/sources");
        AnalysedClass[] analysedClasses = analysedPackage.getClasses().toArray(new AnalysedClass[0]);
        int i = 0;
        while(!Objects.equals(analysedClasses[i].getName(), "QueueInt")) i++;

        assert(analysedClasses[i].isInterface());
    }
}
