import SootUp.Util;
import com.googlecode.dex2jar.tools.BaseCmd;
import org.junit.jupiter.api.Test;
import sootup.core.model.SootClass;
import sootup.core.model.SootField;
import sootup.core.model.SootMethod;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {

    @Test
    public void testLoadClass() {
        Optional<SootClass> realClass = Util.loadClass("Test");
        assert(!realClass.isEmpty());

        Optional<SootClass> imaginaryClass = Util.loadClass("DOESNOTEXIST");
        assert(imaginaryClass.isEmpty());
    }

    @Test
    public void testGetMethods() {
        Optional<SootClass> wrappedClass = Util.loadClass("Test");
        assert(!wrappedClass.isEmpty());

        SootClass unwrappedClass = wrappedClass.get();

        Set<String> methodNames = new HashSet<>();
        for (SootMethod m : Util.getMethods(unwrappedClass)) {
            methodNames.add(m.getName());
        }

        assert(methodNames.contains("inc"));
        assert(methodNames.contains("add"));
        assert(methodNames.contains("print42"));
        assert(methodNames.size() == 4);
    }

    @Test
    public void testGetFields() {
        Optional<SootClass> wrappedClass = Util.loadClass("Test");
        assert(!wrappedClass.isEmpty());

        SootClass unwrappedClass = wrappedClass.get();

        Set<String> fieldNames = new HashSet<>();
        for (SootField f : Util.getFields(unwrappedClass)) {
            fieldNames.add(f.getName());
        }

        assert(fieldNames.contains("foo"));
        assert(fieldNames.contains("bar"));
        assert(fieldNames.size() == 2);
    }



}
