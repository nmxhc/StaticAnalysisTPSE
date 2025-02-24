import SootUp.InternalUtil;
import org.junit.jupiter.api.Test;
import sootup.core.model.SootClass;
import sootup.core.model.SootField;
import sootup.core.model.SootMethod;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InternalUtilTest {

    @Test
    public void testLoadClass() {

        SootClass realClass = InternalUtil.loadClass("Test"); /* No exception should be thrown here */
        SootClass imaginaryClass = null;

        try {
            imaginaryClass = InternalUtil.loadClass("DOESNOTEXIST");
        } catch (Exception e) {
            /* this is intended behaviour */
        }

        if (imaginaryClass != null)
            throw new RuntimeException("Class DOESNOTEXIST shouldn't exist");
    }

    @Test
    public void testGetMethods() {
        SootClass unwrappedClass = InternalUtil.loadClass("Test");

        Set<String> methodNames = new HashSet<>();
        for (SootMethod m : InternalUtil.getMethods(unwrappedClass)) {
            methodNames.add(m.getName());
        }

        assert(methodNames.contains("inc"));
        assert(methodNames.contains("add"));
        assert(methodNames.contains("print42"));
        assert(methodNames.size() == 4);
    }

    @Test
    public void testGetFields() {
        SootClass unwrappedClass = InternalUtil.loadClass("Test");

        Set<String> fieldNames = new HashSet<>();
        for (SootField f : InternalUtil.getFields(unwrappedClass)) {
            fieldNames.add(f.getName());
        }

        assert(fieldNames.contains("foo"));
        assert(fieldNames.contains("bar"));
        assert(fieldNames.size() == 2);
    }



}
