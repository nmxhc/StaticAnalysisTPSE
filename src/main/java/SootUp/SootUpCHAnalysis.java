package SootUp;

import sootup.core.types.Type;
import java.util.Set;

/**
 * Subclass of {@link SootUpAnalysis} implementing class hierarchy analysis using SootUp.
 */
public class SootUpCHAnalysis extends SootUpAnalysis {
    /**
     * Create CHA object.
     * @param path of analysis input location
     */
    public SootUpCHAnalysis(String path) {
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
