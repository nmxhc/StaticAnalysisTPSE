package AST.CodeStructure;

import AST.Expressions.*;
import AST.Statements.*;
import AST.Types.*;
import com.google.errorprone.annotations.Var;
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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Util {

    private static List<JavaClass> availableClasses;
    /**
     * Analyses package located in project folder
     * @param path local path of package in folder
     * @return AnalysedPackage with all classes and methods contained
     */
    public static AST.CodeStructure.Package loadPackage(String path) {
        Path pathToBinary = Paths.get(path);
        AnalysisInputLocation inputLocation = PathBasedAnalysisInputLocation.create(pathToBinary, null);
        JavaView view = new JavaView(inputLocation);
        Collection<JavaSootClass> sootClasses = view.getClasses();

        /* Convert each class to the format of our API */
        availableClasses = new ArrayList<>();
        for (JavaSootClass c : sootClasses) {
            availableClasses.add(new JavaClass(c.getName()));
        }

        for (JavaSootClass c : sootClasses) {
            JavaClass correspondingClass = getClassByName(c.getName());
            correspondingClass.classDeclaration = convertJavaSootClassToClassDeclaration(c);

            for (JavaSootMethod m : c.getMethods()) {
                Method correspondingMethod = getMethodByName(correspondingClass, m.getName());
                correspondingMethod.methodDeclaration = convertJavaSootMethodToMethodDeclaration(m);
            }

        }
        
        return new Package(availableClasses);
    }

    /**
     * Rewrote implementation to be pure. Now doesn't modify original classes, but rather returns a list of new classes
     * with the added information of which classes they extend and which interfaces they implement
     * @param sootClass
     * @return
     */
    private static ClassDeclaration convertJavaSootClassToClassDeclaration(JavaSootClass sootClass) {
        List<Attribute> attributes = new ArrayList<>();
        List<Method> methods = new ArrayList<>();
        JavaClass inheritsFrom;
        List<JavaClass> interfaces = new ArrayList<>();

        // Add all attributes and methods
        {
            for (JavaSootMethod m : sootClass.getMethods()) {
                methods.add(new Method(m.getName(), getClassByName(sootClass.getName())));
            }

            for (JavaSootField f : sootClass.getFields()) {
                attributes.add(convertJavaSootFieldToAnalysedAttribute(f));
            }
        }

        // Add interfaces and inheritance
        {
            Optional<JavaClassType> superClass = sootClass.getSuperclass();
            if (superClass.isPresent()) {
                inheritsFrom = getClassByName(superClass.get().getClassName());
            } else {
                inheritsFrom = getClassByName("Object"); // decided to set superclass to object
            }

            if (sootClass.getInterfaces() != null) {
                for (ClassType classType : sootClass.getInterfaces()) {
                    interfaces.add(getClassByName(classType.getClassName()));
                }
            }
        }

        return new ClassDeclaration(attributes, methods, inheritsFrom, interfaces, sootClass.isAbstract(), sootClass.isInterface());
    }

    private static Method getMethodByName(JavaClass javaClass, String methodName) {
        if (javaClass.hasClassDeclaration()) {
            for (Method m : javaClass.getClassDeclaration().getMethods()) {
                if (m.getName().equals(methodName)) {
                    return m;
                }
            }
        }


        // method doesn't exist, create new one.
        System.out.println("Creating new unknown method " + methodName + " for " + javaClass.getName());
        Method newMethod = new Method(methodName, javaClass);
        javaClass.opaqueMethods.add(newMethod);
        return newMethod;
    }

    private static JavaClass getClassByName(String className){
        if (className.equals("unknown"))
            throw new RuntimeException("Class name is unknown");
        for(JavaClass a : availableClasses){
            if(a.getName().equals(className)){
                return a;
            }
        }

        System.out.println("Creating new unknown class of name " + className);
        // this is the case if the requested class is not user-defined, but part of a library
        // classDeclaration for this class is null
        JavaClass newlyCreated = new JavaClass(className);
        availableClasses.add(newlyCreated);
        return newlyCreated;
    }


    private static Attribute convertJavaSootFieldToAnalysedAttribute(JavaSootField f) {
        return new Attribute(stringToType(f.getType().toString()), f.getName(), f.isStatic());
    }

    private static Type stringToType(String typeName) {
        return switch (typeName) {
            case "int" -> new IntType();
            case "boolean" -> new BooleanType();
            case "void" -> new VoidType();
            default -> new RefType(getClassByName(typeName));
        };
    }

    private static MethodDeclaration convertJavaSootMethodToMethodDeclaration(JavaSootMethod sootMethod) {
        Type returnType = stringToType(sootMethod.getReturnType().toString());
        List<Type> parameterTypes = sootMethod.getParameterTypes().stream().map(t -> stringToType(t.toString())).toList();
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
            basicBlocks.get(i).statements = sootBlocks.get(i).getStmts().stream().map(s -> convertSootStmtToAnalysedStatement(s, sootMethod, basicBlocks)).toList();
        }

        ControlFlowGraph cfg = new ControlFlowGraph(basicBlocks, basicBlocks.get(0));

        return new MethodDeclaration(returnType, parameterTypes, cfg, false);
    }

    public static Attribute getAttributeByName(String attributeName, String className) {
        JavaClass correspondingClass = getClassByName(className);
        if (!correspondingClass.hasClassDeclaration()) {
            // throw new RuntimeException("Not implemented: Handling fields for external classes");
            return null;
        }

        for (Attribute a : correspondingClass.getClassDeclaration().getAttributes()) {
            if (a.getName().equals(attributeName)) {
                return a;
            }
        }

        throw new RuntimeException("Couldn't find attribute " + attributeName + " for class " + className);
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

        System.out.println(sootStmt);

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
            return new AttributeReference(getAttributeByName(v.getFieldSignature().getName(), v.getFieldSignature().getDeclClassType().getClassName()), (Variable) convertValue(v.getBase()));
        } else if (expr instanceof JStaticFieldRef v) {
            return new AttributeReference(getAttributeByName(v.getFieldSignature().getName(), v.getFieldSignature().getDeclClassType().getClassName()), null);
        } else if (expr instanceof sootup.core.jimple.basic.Local v) {
            String typeName = v.getType().toString();
            if (typeName.equals("unknown")) {
                return new Local(v.getName(), null);
            }
            System.out.println(typeName);
            return new Local(v.getName(), stringToType(typeName));
        } else if (expr instanceof JNewExpr e) {
            JavaClass javaClass = getClassByName(e.getType().toString());
            return new ConstructorExpression(javaClass, new ArrayList<>()); // Parameter passing is seperate statement
        } else if (expr instanceof JStaticInvokeExpr e) {
            JavaClass javaClass = getClassByName(e.getMethodSignature().getDeclClassType().getClassName());
            Method method = getMethodByName(javaClass, e.getMethodSignature().getName());
            List<Expression> arguments = e.getArgs().stream().map(Util::convertValue).toList();
            return new CallExpression(javaClass, method, arguments, null); // no receiver object
        } else if (expr instanceof JVirtualInvokeExpr e) {
            JavaClass javaClass = getClassByName(e.getMethodSignature().getDeclClassType().getClassName());
            Method method = getMethodByName(javaClass, e.getMethodSignature().getName());
            List<Expression> arguments = e.getArgs().stream().map(Util::convertValue).toList();
            Variable variable = (Variable) convertValue(e.getBase());
            return new CallExpression(javaClass, method, arguments, variable);
        } else if (expr instanceof JInterfaceInvokeExpr e) {
            JavaClass javaClass = getClassByName(e.getMethodSignature().getDeclClassType().getClassName());
            Method method = getMethodByName(javaClass, e.getMethodSignature().getName());
            List<Expression> arguments = e.getArgs().stream().map(Util::convertValue).toList();
            Variable variable = (Variable) convertValue(e.getBase());
            return new CallExpression(javaClass, method, arguments, variable);
        } else if (expr instanceof JDynamicInvokeExpr e) {
            JavaClass javaClass = getClassByName(e.getMethodSignature().getDeclClassType().getClassName());
            Method method = getMethodByName(javaClass, e.getMethodSignature().getName());
            List<Expression> arguments = e.getArgs().stream().map(Util::convertValue).toList();
            return new CallExpression(javaClass, method, arguments, null);
      } else if (expr instanceof JSpecialInvokeExpr e) {
            JavaClass javaClass = getClassByName(e.getMethodSignature().getDeclClassType().getClassName());
            Method method = getMethodByName(javaClass, e.getMethodSignature().getName());
            List<Expression> arguments = e.getArgs().stream().map(Util::convertValue).toList();
            Variable variable = (Variable) convertValue(e.getBase());
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

        throw new IllegalArgumentException("Not implemented: Can't convert value of type " + expr.getClass().getName());
    }
}
