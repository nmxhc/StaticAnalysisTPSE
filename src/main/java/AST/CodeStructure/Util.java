package AST.CodeStructure;

import AST.Expressions.*;
import AST.Statements.*;
import AST.Types.*;
import com.google.errorprone.annotations.Var;
import com.sun.jna.platform.unix.solaris.LibKstat;
import fj.data.Java;
import org.apache.commons.lang3.NotImplementedException;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.basic.Immediate;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.BooleanConstant;
import sootup.core.jimple.common.constant.Constant;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.constant.StringConstant;
import sootup.core.jimple.common.expr.*;
import sootup.core.jimple.common.ref.JParameterRef;
import sootup.core.jimple.common.ref.JThisRef;
import sootup.core.jimple.common.stmt.*;
import sootup.core.model.Body;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
import sootup.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import sootup.java.core.JavaSootClass;
import sootup.java.core.JavaSootField;
import sootup.java.core.JavaSootMethod;
import sootup.java.core.types.JavaClassType;
import sootup.java.core.views.JavaView;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Util {

    /**
     * Analyses package located in project folder
     * @param path local path of package in folder
     * @return AnalysedPackage with all classes and methods contained
     */
    public static AST.CodeStructure.Package loadPackage(String path) {
        Path pathToBinary = Paths.get(path);
        AnalysisInputLocation inputLocation = PathBasedAnalysisInputLocation.create(pathToBinary, null);
        JavaView view = new JavaView(inputLocation);
        Collection<JavaSootClass> availableClasses = view.getClasses();

        /* Convert each class to the format of our API */
        List<JavaClass> classes = new ArrayList<>();
        for (JavaSootClass c : availableClasses) {
            classes.add(new JavaClass(c.getName()));
        }

        for (JavaSootClass c : availableClasses) {
            JavaClass correspondingClass = getClassByName(c.getName(), classes);
            correspondingClass.classDeclaration = convertJavaSootClassToClassDeclaration(c, classes);

            for (JavaSootMethod m : c.getMethods()) {
                Method correspondingMethod = getMethodByName(m.getName(), correspondingClass.getClassDeclaration().getMethods());
                correspondingMethod.methodDeclaration = convertJavaSootMethodToMethodDeclaration(m, classes);
            }

        }
        
        return new Package(classes);
    }

    /**
     * Rewrote implementation to be pure. Now doesn't modify original classes, but rather returns a list of new classes
     * with the added information of which classes they extend and which interfaces they implement
     * @param sootClass
     * @param availableClasses
     * @return
     */
    private static ClassDeclaration convertJavaSootClassToClassDeclaration(JavaSootClass sootClass, List<JavaClass> availableClasses) {
        List<Attribute> attributes = new ArrayList<>();
        List<Method> methods = new ArrayList<>();
        JavaClass inheritsFrom;
        List<JavaClass> interfaces = new ArrayList<>();

        // Add all attributes and methods
        {
            for (JavaSootMethod m : sootClass.getMethods()) {
                methods.add(new Method(m.getName()));
            }

            for (JavaSootField f : sootClass.getFields()) {
                attributes.add(convertJavaSootFieldToAnalysedAttribute(f, availableClasses));
            }
        }

        // Add interfaces and inheritance
        {
            Optional<JavaClassType> superClass = sootClass.getSuperclass();
            if (superClass.isPresent()) {
                inheritsFrom = getClassByName(superClass.get().getClassName(), availableClasses);
            } else {
                inheritsFrom = getClassByName("Object", availableClasses); // decided to set superclass to object
            }

            if (sootClass.getInterfaces() != null) {
                for (ClassType classType : sootClass.getInterfaces()) {
                    interfaces.add(getClassByName(classType.getClassName(), availableClasses));
                }
            }
        }

        return new ClassDeclaration(attributes, methods, inheritsFrom, interfaces, sootClass.isAbstract(), sootClass.isInterface());
    }

    private static Method getMethodByName(String methodName, List<Method> methods) {
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }

        // method doesn't exist, create new one.
        System.out.println("Asked for unknown method, creating new one");
        return new Method(methodName);
        //throw new RuntimeException("Tried to get unknown method " + methodName);
    }


//    private static JavaSootClass getSootClassByName(String className, Collection<JavaSootClass> javaSootClasses){
//        for(JavaSootClass j : javaSootClasses) {
//            if (j.getVariable().equals(className)) {
//                return j;
//            }
//        }
//        throw new RuntimeException("This probably shouldn't happen. Wanted to get unknown SootClass of name " + className);
//    }

    private static JavaClass getClassByName(String className, List<JavaClass> analysedClasses){
        for(JavaClass a : analysedClasses){
            if(a.getName().equals(className)){
                return a;
            }
        }

        System.out.println("Creating new unknown class of name " + className);
        // this is the case if the requested class is not user-defined, but part of a library
        // classDeclaration for this class is null
        JavaClass newlyCreated = new JavaClass(className);
        analysedClasses.add(newlyCreated);
        return newlyCreated;
    }


    private static Attribute convertJavaSootFieldToAnalysedAttribute(JavaSootField f, List<JavaClass> availableClasses) {
        return new Attribute(stringToType(f.getType().toString(), availableClasses), f.getName());
    }

    private static Type stringToType(String typeName, List<JavaClass> availableClasses) {
        if (typeName.equals("boolean")) {
            System.out.println("is bool!");
        }
        return switch (typeName) {
            case "int" -> new IntType();
            case "boolean" -> new BooleanType();
            case "void" -> new VoidType();
            default -> new RefType(getClassByName(typeName, availableClasses));
        };
    }

    private static MethodDeclaration convertJavaSootMethodToMethodDeclaration(JavaSootMethod sootMethod, List<JavaClass> availableClasses) {
        Type returnType = stringToType(sootMethod.getReturnType().toString(), availableClasses);
        List<Type> parameterTypes = sootMethod.getParameterTypes().stream().map(t -> stringToType(t.toString(), availableClasses)).toList();
        // parameterTypes is List<Type>, not List<Parameter> because we can't retrieve the names after compiling to Bytecode

        if (!sootMethod.hasBody()) {
            return new MethodDeclaration(returnType, parameterTypes, null, true);
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
            basicBlocks.get(i).statements = sootBlocks.get(i).getStmts().stream().map(s -> convertSootStmtToAnalysedStatement(s, sootMethod, basicBlocks, availableClasses)).toList();
        }

        ControlFlowGraph cfg = new ControlFlowGraph(basicBlocks, basicBlocks.get(0));

        return new MethodDeclaration(returnType, parameterTypes, cfg, false);
    }

    /**
     * Converts a soot Stmt to an AST Statement. Doesn't work for all types of statements
     * @param sootStmt the soot statement to convert
     * @param sootMethod the method in which it occurs
     * @param basicBlocks the List of AST basic blocks of the method, should correspond 1:1 with getBlocksSorted()!
     * @param availableClasses All available classes
     * @return
     */
    private static Statement convertSootStmtToAnalysedStatement(Stmt sootStmt, JavaSootMethod sootMethod, List<BasicBlock> basicBlocks, List<JavaClass> availableClasses) {
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
            String methodName = s.getInvokeExpr().getMethodSignature().getName();
            return null;
            //return new CallStatement(getMethodByName(methodName));
        } else if (sootStmt instanceof JAssignStmt s) {
            Variable v = new Variable(s.getLeftOp().toString());
            Expression e = convertValue(s.getRightOp());
            return new AssignStatement(v, e);
        } else if (sootStmt instanceof JIdentityStmt s) {
            Variable v = new Variable(s.getLeftOp().toString());
            Expression e = convertValue(s.getRightOp());
            // might be relevant
            return new AssignStatement(v, e);
            //throw new RuntimeException("Not implemented: Converter for JIdentityStmt");
        } else if (sootStmt instanceof JReturnStmt s) {
            return new ReturnStatement(convertValue(s.getOp()));
        } else if (sootStmt instanceof JReturnVoidStmt s) {
            return new ReturnStatement(null);
        }

        throw new IllegalArgumentException("Not implemented: No Converter for " + sootStmt.getClass().getName() + " in " + sootMethod.getName());
    }

    /**
     * Connverts a soot Value to an AST Expression. Doesn't support all types of expressions
     * @param expr the soot value to convert
     * @return The converted expression
     */
    private static Expression convertValue(Value expr) {
        // A very big switch. Could be refactored using visitor, but personally, I find this more readable

        System.out.println(expr.getClass().getName());
        if (expr instanceof JThisRef) {
            System.out.println("got thisref");
            return null;
        } else if (expr instanceof JParameterRef e){
            int index = e.getIndex();
            return new VariableExpression(new Variable("@parameter" + index));
        } else if (expr instanceof Local e) {
            String varName = e.getName();
            return new VariableExpression(new Variable(varName));
        } else if (expr instanceof JStaticInvokeExpr e) { // dynamic, virtual etc.
            //Method method = getMethodByName(e.getMethodSignature().getName());
            //List<Expression> arguments = e.getArgs().stream().map(Util::convertValue).toList();
            //return new CallExpression(method, arguments);
            return null;
        } else if (expr instanceof IntConstant e) {
            return new IntegerLiteral(e.getValue());
        } else if (expr instanceof BooleanConstant e) {
            throw new RuntimeException("This branch shouldn't be reachable, as booleans are represented as ints in Byte Code");
        } else if (expr instanceof StringConstant e) {
            return new StringLiteral(e.getValue());

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
        }

        throw new IllegalArgumentException("Not implemented: Can't convert value of type " + expr.getClass().getName());
    }
}
