package SootUp;

import sootup.core.types.Type;
import java.util.Set;

/**
 * Subclass of {@link Analysis} implementing class hierarchy analysis.
 */
public class CHAAnalysis extends Analysis {
    /**
     * Create CHA object.
     * @param path of analysis input location
     */
    public CHAAnalysis(String path) {
        super(path);
    }

    /**
     * Retrieves the set of constructed types.
     * @return a set of constructed types.
     */
    protected Set<Type> getConstructedTypes() {
        return null;
    }
}
