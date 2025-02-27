package AST.CodeStructure;

import AST.Expressions.*;
import AST.Statements.*;
import AST.Types.*;
import fj.data.Java;
import org.apache.commons.lang3.ObjectUtils;
import org.w3c.dom.Attr;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.BooleanConstant;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.constant.StringConstant;
import sootup.core.jimple.common.constant.NullConstant;
import sootup.core.jimple.common.expr.*;
import sootup.core.jimple.common.ref.*;
import sootup.core.jimple.common.stmt.*;
import sootup.core.jimple.javabytecode.stmt.JSwitchStmt;
import sootup.core.types.ClassType;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.JavaSootClass;
import sootup.java.core.JavaSootField;
import sootup.java.core.JavaSootMethod;
import sootup.java.core.types.JavaClassType;
import sootup.java.core.views.JavaView;

import java.lang.String;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Util {

    private static List<JavaClass> availableClasses;

    /**
     * Analyses package located in project folder
     * @param path local path of package in folder
     * @return Package with all classes and methods contained
     */
    public static AST.CodeStructure.Package loadPackage(String path) {
        Path pathToBinary = Paths.get(path);
        AnalysisInputLocation inputLocation = PathBasedAnalysisInputLocation.create(pathToBinary, null);
        JavaView view = new JavaView(inputLocation);
        Collection<JavaSootClass> sootClasses = view.getClasses();


        /* Convert each class to the format of our API */
        availableClasses = new ArrayList<>();
        availableClasses.add(new JavaClass("java.lang.Object"));
        availableClasses.add(new JavaClass("java.lang.String"));
        for (JavaSootClass c : sootClasses) {
            availableClasses.add(new JavaClass(c.getName()));
        }

        populateObjectAndString();

        for (JavaSootClass c : sootClasses) {
            JavaClass correspondingClass = getClassByName(c.getName());
            correspondingClass.methods = c.getMethods().stream()
                    .map(m -> new Method(convertSignature(m.getSignature()))).toList();
            correspondingClass.attributes = c.getFields().stream()
                    .map(f -> new Attribute(stringToType(f.getType().toString()), f.getName(), f.isStatic())).toList();
            correspondingClass.extendsClass = c.isInterface() ? null : c.getSuperclass()
                    .map(javaClassType -> getClassByName(javaClassType.getFullyQualifiedName()))
                    .orElseGet(() -> getClassByName("java.lang.Object"));
            correspondingClass.implementsInterfaces = c.getInterfaces().stream()
                    .map(i -> getClassByName(i.getFullyQualifiedName())).toList();
            correspondingClass.isAbstract = c.isAbstract();
            correspondingClass.isInterface = c.isInterface();
        }

        for (JavaSootClass c : sootClasses) {
            JavaClass correspondingClass = getClassByName(c.getName());
            for (JavaSootMethod m : c.getMethods()) {
                Method correspondingMethod = getMethodBySignature(correspondingClass, m.getSignature());
                correspondingMethod.isAbstract = m.isAbstract();
                correspondingMethod.javaClass = correspondingClass;
                correspondingMethod.controlFlowGraph = createCFGForMethod(m);
            }

        }

        return new Package(availableClasses);
    }

    private static MethodSignature convertSignature(sootup.core.signatures.MethodSignature sig) {
        return new MethodSignature(
            sig.getName(),
            stringToType(sig.getType().toString()),
            sig.getParameterTypes().stream().map(t -> stringToType(t.toString())).toList()
        );
    }

    private static void populateObjectAndString() {
        BasicBlock dummyBlock = new BasicBlock();
        dummyBlock.statements = new ArrayList<>();
        dummyBlock.successors = new ArrayList<>();
        dummyBlock.predecessors = new ArrayList<>();

        List<BasicBlock> dummyBlocks = new ArrayList<>();
        dummyBlocks.add(dummyBlock);

        ControlFlowGraph emptyCFG = new ControlFlowGraph(dummyBlocks, dummyBlock);

        availableClasses.get(0).attributes = new ArrayList<>();
        availableClasses.get(0).methods = new ArrayList<>();
        availableClasses.get(0).methods.add(new Method(new MethodSignature("<init>", stringToType("void"), new ArrayList<>())));
        availableClasses.get(0).methods.get(0).javaClass = availableClasses.get(0);
        availableClasses.get(0).methods.get(0).isAbstract = false;
        availableClasses.get(0).methods.get(0).controlFlowGraph = emptyCFG;
        availableClasses.get(0).implementsInterfaces = new ArrayList<>();
        availableClasses.get(0).isAbstract = false;
        availableClasses.get(0).isInterface = false;

        availableClasses.get(1).attributes = new ArrayList<>();
        availableClasses.get(1).methods = new ArrayList<>();
        availableClasses.get(1).methods.add(new Method(new MethodSignature("<init>", stringToType("void"), new ArrayList<>())));
        availableClasses.get(1).methods.get(0).javaClass = availableClasses.get(1);
        availableClasses.get(1).methods.get(0).isAbstract = false;
        availableClasses.get(1).methods.get(0).controlFlowGraph = emptyCFG;
        availableClasses.get(1).extendsClass = getClassByName("java.lang.Object");
        availableClasses.get(1).implementsInterfaces = new ArrayList<>();
        availableClasses.get(1).isAbstract = false;
        availableClasses.get(1).isInterface = false;
    }

    private static JavaSootClass getSootClassByName(String name, Collection<JavaSootClass> sootClasses) {
        for (JavaSootClass c : sootClasses) {
            if (c.getName().equals(name)) {
                return c;
            }
        }

        throw new IllegalArgumentException(name + " is not a SootClass.");
    }

    private static Attribute getAttributeByName(JavaClass javaClass, String attributeName) {
        for (Attribute a : javaClass.getAttributes()) {
            if (a.getName().equals(attributeName)) {
                return a;
            }
        }

        throw new IllegalArgumentException(attributeName + " is unknown, no such attribute in " + javaClass.getName());
    }

    private static Method getMethodByName(JavaClass javaClass, String methodName) {
        var x = javaClass.getMethodByName(methodName);
        if (!x.isPresent())
            throw new IllegalArgumentException(methodName + " is unknown, no such method in " + javaClass.getName());
        return x.get();
    }

    private static Method getMethodBySignature(JavaClass javaClass, sootup.core.signatures.MethodSignature sig) {
        var convSig = convertSignature(sig);
        var x = javaClass.getMethodBySignature(convSig);
        if (!x.isPresent())
            throw new IllegalArgumentException("<" + convSig.toString() + "> is unknown, no such method in " + javaClass.getName());
        return x.get();
    }

    private static JavaClass getClassByName(String className){
        for(JavaClass a : availableClasses){
            if(a.getName().equals(className)){
                return a;
            }
        }

        throw new IllegalArgumentException(className + " is unknown, no such class in project.");
    }

    private static Type stringToType(String typeName) {
        return switch (typeName) {
            case "int" -> new IntType();
            case "boolean" -> new BooleanType();
            case "void" -> new VoidType();
            default -> new RefType(getClassByName(typeName));
        };
    }

    private static ControlFlowGraph createCFGForMethod(JavaSootMethod sootMethod) {
        if (sootMethod.isAbstract()) {
            return null;
        }

        // method has body, extract it

        List<BasicBlock> basicBlocks = new ArrayList<>();

        sootMethod.getBody().getStmtGraph().getBlocksSorted();
        for (sootup.core.graph.BasicBlock<?> b : sootMethod.getBody().getStmtGraph().getBlocksSorted()) {
            basicBlocks.add(new BasicBlock());
        }

        // initialize all blocks

        var sootBlocks = sootMethod.getBody().getStmtGraph().getBlocksSorted();
        for (int i = 0; i < sootBlocks.size(); i++) {
            basicBlocks.get(i).successors = sootBlocks.get(i).getSuccessors().stream().map(s -> basicBlocks.get(sootBlocks.indexOf(s))).toList();
            basicBlocks.get(i).predecessors = sootBlocks.get(i).getPredecessors().stream().map(p -> basicBlocks.get(sootBlocks.indexOf(p))).toList();
            basicBlocks.get(i).statements = new ArrayList<>(sootBlocks.get(i).getStmts().stream().map(s -> convertSootStmtToAnalysedStatement(s, sootMethod, basicBlocks)).toList());

            if (basicBlocks.get(i).getSuccessors().size() == 1 && !(basicBlocks.get(i).getStatements().get(basicBlocks.get(i).getStatements().size()-1) instanceof GotoStatement)) {
                basicBlocks.get(i).getStatements().add(new GotoStatement(basicBlocks.get(i).getSuccessors().get(0)));
            }
        }

        return new ControlFlowGraph(basicBlocks, basicBlocks.get(0));
    }


    /**
     * Converts a soot Stmt to an AST Statement. Doesn't work for all types of statements
     * @param sootStmt the soot statement to convert
     * @param sootMethod the method in which it occurs
     * @param basicBlocks the List of AST basic blocks of the method, should correspond 1:1 with getBlocksSorted()!
     * @return
     */
    private static Statement convertSootStmtToAnalysedStatement(Stmt sootStmt, JavaSootMethod sootMethod, List<BasicBlock> basicBlocks) {
        var stmtGraph = sootMethod.getBody().getStmtGraph();

        if (sootStmt instanceof JGotoStmt s) {
            List<Stmt> targetStmts = s.getTargetStmts(sootMethod.getBody()); // should only contain one statement
            if (targetStmts.size() != 1) {
                throw new RuntimeException("DEBUG: Goto statement has" + targetStmts.size() + " target statements. This probably a bug.");
            }
            int targetBlockIndex = stmtGraph.getBlocksSorted().indexOf(stmtGraph.getBlockOf(targetStmts.get(0)));
            return new GotoStatement(basicBlocks.get(targetBlockIndex));
        } else if (sootStmt instanceof JIfStmt s) {
            List<Stmt> methodStatements = sootMethod.getBody().getStmts();
            List<Stmt> targetStmts = s.getTargetStmts(sootMethod.getBody());
            if (targetStmts.size() != 1) {
                throw new RuntimeException("DEBUG: If statement has" + targetStmts.size() + " target statements. This probably a bug.");
            }

            Expression condition = convertValue(s.getCondition());
            BasicBlock ifTrueBlock = basicBlocks.get(stmtGraph.getBlocksSorted().indexOf(stmtGraph.getBlockOf(targetStmts.get(0))));
            BasicBlock ifFalseBlock = basicBlocks.get(stmtGraph.getBlocksSorted().indexOf(stmtGraph.getBlockOf(methodStatements.get(methodStatements.indexOf(s) + 1))));

            return new BranchStatement(condition, ifTrueBlock, ifFalseBlock);
        } else if (sootStmt instanceof JInvokeStmt s) {
            return new CallStatement((CallExpression) convertValue(s.getInvokeExpr()));
        } else if (sootStmt instanceof JAssignStmt s) {
            Variable v = (Variable) convertValue(s.getLeftOp());
            Expression e = convertValue(s.getRightOp());
            return new AssignStatement(v, e);
        } else if (sootStmt instanceof JIdentityStmt s) {
            Variable v = (Variable) convertValue(s.getLeftOp());
            Expression e = convertValue(s.getRightOp());
            return new AssignStatement(v, e);
        } else if (sootStmt instanceof JReturnStmt s) {
            return new ReturnStatement(convertValue(s.getOp()));
        } else if (sootStmt instanceof JReturnVoidStmt s) {
            return new ReturnStatement(null);
        } else if (sootStmt instanceof JSwitchStmt s) {
            var branches = new ArrayList<SwitchBranch>();
            var values = s.getValues();
            int i = 0;
            for (var stmt : s.getTargetStmts(sootMethod.getBody())) {
                int targetBlockIndex = stmtGraph.getBlocksSorted().indexOf(stmtGraph.getBlockOf(stmt));
                var targetBlock = basicBlocks.get(targetBlockIndex);
                branches.add(values.size() < i
                    ? new SwitchBranch((IntegerLiteral) convertValue(values.get(i)), targetBlock)
                    : new SwitchBranch(targetBlock));
                i++;
            }
            return new SwitchStatement(convertValue(s.getKey()), branches);
        } else if (sootStmt instanceof JThrowStmt s) {
            return new ThrowStatement(convertValue(s.getOp()));
        }

        throw new IllegalArgumentException("Not implemented: No Converter for " + sootStmt.getClass().getName() + " in " + sootMethod.getName());
    }

    /**
     * Connverts a soot Value to an AST Expression. Doesn't support all types of expressions
     * @param expr the soot value to convert
     * @return The converted expression
     */
    private static Expression convertValue(Value expr) {
        // A very big switch. Could be refactored using the visitor pattern, but I find this more readable

        if (expr instanceof JThisRef) {
            return new This();
        } else if (expr instanceof JParameterRef e) {
            int index = e.getIndex();
            return new Local("@parameter" + index, stringToType(e.getType().toString()));
        } else if (expr instanceof JInstanceFieldRef v) {
            JavaClass referencedClass = getClassByName(v.getFieldSignature().getDeclClassType().getFullyQualifiedName());
            return new AttributeReference(getAttributeByName(referencedClass, v.getFieldSignature().getName()), referencedClass, (Variable) convertValue(v.getBase()));
        } else if (expr instanceof JStaticFieldRef v) {
            JavaClass referencedClass = getClassByName(v.getFieldSignature().getDeclClassType().getFullyQualifiedName());
            return new AttributeReference(getAttributeByName(referencedClass, v.getFieldSignature().getName()), referencedClass, null);
        } else if (expr instanceof sootup.core.jimple.basic.Local v) {
            String typeName = v.getType().toString();
            if (typeName.equals("unknown")) {
                return new Local(v.getName(), null);
            }
            return new Local(v.getName(), stringToType(typeName));
        } else if (expr instanceof JNewExpr e) {
            JavaClass javaClass = getClassByName(e.getType().getFullyQualifiedName());
            return new ConstructorExpression(javaClass, new ArrayList<>()); // Parameter passing is seperate statement
        } else if (expr instanceof AbstractInvokeExpr e) {
            var sig = e.getMethodSignature();
            JavaClass javaClass = getClassByName(sig.getDeclClassType().getFullyQualifiedName());
            Method method = getMethodBySignature(javaClass, sig);
            List<Expression> arguments = e.getArgs().stream().map(Util::convertValue).toList();
            Variable variable = null;
            if (e instanceof AbstractInstanceInvokeExpr inst)
                variable = (Variable) convertValue(inst.getBase());
            return new CallExpression(javaClass, method, arguments, variable);
        } else if (expr instanceof JCastExpr e) {
            return new CastExpression(stringToType(e.getType().toString()), convertValue(e.getOp()));
        } else if (expr instanceof IntConstant e) {
            return new IntegerLiteral(e.getValue());
        } else if (expr instanceof StringConstant e) {
            return new StringLiteral(e.getValue());
        } else if (expr instanceof NullConstant e) {
            return new NullLiteral();
        } else if (expr instanceof JAddExpr e) {
            return new ArithmeticExpression(convertValue(e.getOp1()), ArithmeticOperator.ADD, convertValue(e.getOp2()));
        } else if (expr instanceof JSubExpr e) {
            return new ArithmeticExpression(convertValue(e.getOp1()), ArithmeticOperator.SUB, convertValue(e.getOp2()));
        } else if (expr instanceof JMulExpr e) {
            return new ArithmeticExpression(convertValue(e.getOp1()), ArithmeticOperator.MUL, convertValue(e.getOp2()));
        } else if (expr instanceof JDivExpr e) {
            return new ArithmeticExpression(convertValue(e.getOp1()), ArithmeticOperator.DIV, convertValue(e.getOp2()));

        } else if (expr instanceof JEqExpr e) {
            return new CompareExpression(convertValue(e.getOp1()), ComparisonOperator.EQ, convertValue(e.getOp2()));
        } else if (expr instanceof JNeExpr e) {
            return new CompareExpression(convertValue(e.getOp1()), ComparisonOperator.NEQ, convertValue(e.getOp2()));
        } else if (expr instanceof JLtExpr e) {
            return new CompareExpression(convertValue(e.getOp1()), ComparisonOperator.LT, convertValue(e.getOp2()));
        } else if (expr instanceof JGtExpr e) {
            return new CompareExpression(convertValue(e.getOp1()), ComparisonOperator.GT, convertValue(e.getOp2()));
        } else if (expr instanceof JLeExpr e) {
            return new CompareExpression(convertValue(e.getOp1()), ComparisonOperator.LEQ, convertValue(e.getOp2()));
        } else if (expr instanceof JGeExpr e) {
            return new CompareExpression(convertValue(e.getOp1()), ComparisonOperator.GEQ, convertValue(e.getOp2()));


        } else if (expr instanceof BooleanConstant e) {
            throw new RuntimeException("This branch shouldn't be reachable, as booleans are represented as ints in Byte Code. This is a bug.");
        }

        throw new IllegalArgumentException("Not implemented: Can't convert value of type " + expr.getClass().getName() + ": " + expr);
    }
}
