package SootUp;

import sootup.core.jimple.common.expr.JNewExpr;
import sootup.core.jimple.common.stmt.JAssignStmt;
import sootup.core.types.Type;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Subclass of {@link SootUpAnalysis} implementing rapid type analysis using SootUp.
 */
public class SootUpRTAnalysis extends SootUpAnalysis {
    private Set<Type> constructedTypes;

    /**
     * Create RTA object.
     * @param path of analysis input location
     */
    public SootUpRTAnalysis(String path) {
        super(path);

        constructedTypes = view.getClasses().stream()
            .flatMap(c -> c.getMethods().stream())
            .flatMap(m -> InternalUtil.getStatements(m).stream())
            .filter(s -> s instanceof JAssignStmt)
            .map(s -> ((JAssignStmt) s).getRightOp())
            .filter(v -> v instanceof JNewExpr)
            .map(v -> ((JNewExpr) v).getType())
            .collect(Collectors.toSet());
    }

    protected Set<Type> getConstructedTypes() {
        return constructedTypes;
    }
}
