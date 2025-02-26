package SootUp;

import sootup.core.types.Type;
import java.util.Set;

public class CHAAnalysis extends Analysis {
    public CHAAnalysis(String path) {
        super(path);
    }

    /**
     * Retrieves the set of constructed types.
     * @return A set of constructed types.
     */
    protected Set<Type> getConstructedTypes() {
        return null;
    }
}
