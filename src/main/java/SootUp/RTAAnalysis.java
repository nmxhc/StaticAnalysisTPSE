package SootUp;

import sootup.core.jimple.common.expr.JNewExpr;
import sootup.core.jimple.common.stmt.JAssignStmt;
import sootup.core.types.Type;

import java.util.Set;
import java.util.stream.Collectors;

public class RTAAnalysis extends Analysis {
    private Set<Type> constructedTypes;

    public RTAAnalysis(String path) {
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
