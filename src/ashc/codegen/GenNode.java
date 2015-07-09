package ashc.codegen;

import static org.objectweb.asm.Opcodes.*;

import java.io.*;
import java.util.*;

import org.objectweb.asm.*;

import ashc.grammar.Node.IExpression;
import ashc.grammar.Node.NodeBinary;
import ashc.grammar.Node.NodeNull;
import ashc.semantics.Member.Field;
import ashc.semantics.*;
import ashc.semantics.Semantics.TypeI;

/**
 * Ash
 *
 * @author samtebbs, 11:23:26 - 7 Jul 2015
 */
public abstract class GenNode {

    public static LinkedList<GenNodeType> types = new LinkedList<GenNodeType>();
    public static Stack<GenNodeType> typeStack = new Stack<GenNodeType>();
    public static HashSet<String> generatedTupleClasses = new HashSet<String>();
    public static GenNodeFunction currentFunction;

    public abstract void generate(Object visitor);

    public static void addGenNodeType(final GenNodeType node) {
	types.add(node);
	typeStack.push(node);
    }

    public static void exitGenNodeType() {
	typeStack.pop();
    }

    public static interface IGenNodeStmt {

    }

    public static interface IGenNodeExpr {

    }

    public static enum EnumInstructionOperand {
	REFERENCE, BOOL, BYTE, CHAR, INT, LONG, FLOAT, DOUBLE, ARRAY, SHORT, VOID
    }

    public static class GenNodeType extends GenNode {

	public String name, superclass;
	public String[] interfaces;
	public int modifiers;
	public LinkedList<GenNodeField> fields = new LinkedList<GenNodeField>();
	private final LinkedList<GenNodeFunction> functions = new LinkedList<GenNodeFunction>();

	public GenNodeType(final String name, final String superclass, final String[] interfaces, final int modifiers) {
	    this.name = name;
	    this.superclass = superclass;
	    this.interfaces = interfaces;
	    this.modifiers = modifiers;
	}

	public void addFunction(final GenNodeFunction func) {
	    functions.add(func);
	}

	@Override
	public void generate(final Object visitor) {
	    final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
	    cw.visit(Opcodes.V1_6, modifiers | Opcodes.ACC_SUPER, name, null, superclass, interfaces);
	    final String[] folders = name.split("\\.");
	    int i = 0;
	    final StringBuffer dirSb = new StringBuffer("classes/");
	    for (; i < (folders.length - 1); i++)
		dirSb.append(folders[i] + "/");
	    final String shortName = folders[i];
	    final File parentFolders = new File(dirSb.toString());
	    parentFolders.mkdirs();
	    cw.visitSource(shortName + ".ash", null);

	    for (final GenNodeField field : fields)
		field.generate(cw);
	    for (final GenNodeFunction func : functions)
		func.generate(cw);

	    cw.visitEnd();

	    final File classFile = new File(dirSb.toString() + shortName + ".class");
	    if (classFile.exists()) classFile.delete();
	    try {
		System.out.println("Generating: " + classFile.getAbsolutePath());
		classFile.createNewFile();
		final FileOutputStream fos = new FileOutputStream(classFile);
		fos.write(cw.toByteArray());
		fos.close();
	    } catch (final IOException e) {
		e.printStackTrace();
	    }
	}

    }

    public static class GenNodeFunction extends GenNode {

	public String name;
	public int modifiers;
	public String type;
	public LinkedList<TypeI> params = new LinkedList<TypeI>();
	public LinkedList<GenNode> stmts = new LinkedList<GenNode>();

	public GenNodeFunction(final String name, final int modifiers, final String type) {
	    currentFunction = this;
	    this.name = name;
	    this.modifiers = modifiers;
	    this.type = type;
	}

	@Override
	public void generate(final Object visitor) {
	    final ClassWriter cw = (ClassWriter) visitor;
	    final StringBuffer signature = new StringBuffer("(");
	    if (params != null) for (final TypeI type : params)
		signature.append(type.toBytecodeName());
	    signature.append(")" + type);
	    final MethodVisitor mv = cw.visitMethod(modifiers, name, signature.toString(), null, null);
	    mv.visitCode();
	    for (final GenNode stmt : stmts)
		stmt.generate(mv);
	    mv.visitEnd();
	}

	@Override
	public String toString() {
	    return "GenNodeFunction [name=" + name + ", modifiers=" + modifiers + ", type=" + type + ", params=" + params + ", stmts=" + stmts + "]";
	}

    }

    public static class GenNodeField extends GenNode {

	public int modifiers;
	public String name, type;

	public GenNodeField(final int modifiers, final String name, final String type) {
	    this.modifiers = modifiers;
	    this.name = name;
	    this.type = type;
	}

	public GenNodeField(final Field field) {
	    modifiers = field.modifiers;
	    name = field.qualifiedName.toString();
	    type = field.type.toBytecodeName();
	}

	@Override
	public void generate(final Object visitor) {
	    final ClassWriter cw = (ClassWriter) visitor;
	    final FieldVisitor fieldV = cw.visitField(modifiers, name, type, null, null);
	    fieldV.visitEnd();
	}

	@Override
	public String toString() {
	    return "GenNodeField [modifiers=" + modifiers + ", name=" + name + ", type=" + type + "]";
	}

    }

    public static class GenNodeVarAssign extends GenNode implements IGenNodeStmt {

	public int varID;
	public IGenNodeExpr expr;
	public EnumInstructionOperand operand;

	public GenNodeVarAssign(final int varID, final IGenNodeExpr expr, final EnumInstructionOperand operand) {
	    this.varID = varID;
	    this.expr = expr;
	    this.operand = operand;
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    ((GenNode) expr).generate(mv);
	    final int opcode = ASTORE;
	    mv.visitVarInsn(opcode, varID);
	}

    }

    public static class GenNodeFieldAssign extends GenNode implements IGenNodeStmt {

	public String varName;
	public String enclosingType;
	public String type;
	public IGenNodeExpr expr;

	public GenNodeFieldAssign(final String varName, final String enclosingType, final String type, final IGenNodeExpr expr) {
	    this.varName = varName;
	    this.enclosingType = enclosingType;
	    this.type = type;
	    this.expr = expr;
	}

	@Override
	public void generate(final Object visitor) {
	    System.out.println(this);
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    ((GenNode) expr).generate(mv);
	    mv.visitFieldInsn(PUTFIELD, enclosingType, varName, type);
	}

	@Override
	public String toString() {
	    final StringBuilder builder = new StringBuilder();
	    builder.append("GenNodeFieldAssign [varName=");
	    builder.append(varName);
	    builder.append(", enclosingType=");
	    builder.append(enclosingType);
	    builder.append(", type=");
	    builder.append(type);
	    builder.append(", expr=");
	    builder.append(expr);
	    builder.append("]");
	    return builder.toString();
	}

    }

    public static class GenNodeFieldLoad extends GenNode implements IGenNodeExpr {

	public String varName, enclosingType, type;

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    mv.visitFieldInsn(GETFIELD, enclosingType, varName, type);
	}

    }

    public static class GenNodeFieldStore extends GenNode implements IGenNodeStmt {

	public String varName, enclosingType, type;

	public GenNodeFieldStore(final String varName, final String enclosingType, final String type) {
	    this.varName = varName;
	    this.enclosingType = enclosingType;
	    this.type = type;
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitFieldInsn(PUTFIELD, enclosingType, varName, type);
	}

    }

    public static class GenNodeVarLoad extends GenNode implements IGenNodeExpr {

	public EnumInstructionOperand operand;
	public int varID;

	public GenNodeVarLoad(final EnumInstructionOperand operand, final int varID) {
	    this.operand = operand;
	    this.varID = varID;
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    int opcode = ALOAD;
	    switch (operand) {
		case ARRAY:
		    opcode = AALOAD;
		    break;
		case BYTE:
		case INT:
		case SHORT:
		case BOOL:
		case CHAR:
		    opcode = ILOAD;
		    break;
		case DOUBLE:
		    opcode = DLOAD;
		    break;
		case FLOAT:
		    opcode = FLOAD;
		    break;
		case LONG:
		    opcode = LLOAD;
		    break;
		case REFERENCE:
		    opcode = ALOAD;
	    }
	    mv.visitVarInsn(opcode, varID);
	}

    }

    public static class GenNodeFuncCall extends GenNode implements IGenNodeExpr, IGenNodeStmt {

	public String enclosingType, name, signature;
	public boolean interfaceFunc, publicFunc, staticFunc, constructor;

	public GenNodeFuncCall(final String enclosingType, final String name, final String signature, final boolean interfaceFunc, final boolean publicFunc, final boolean staticFunc) {
	    this.enclosingType = enclosingType;
	    this.name = name;
	    this.signature = signature;
	    this.interfaceFunc = interfaceFunc;
	    this.publicFunc = publicFunc;
	    this.staticFunc = staticFunc;
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    int opcode = INVOKESPECIAL;
	    if (interfaceFunc) opcode = INVOKEINTERFACE;
	    else if (staticFunc) opcode = INVOKESTATIC;
	    else if (publicFunc) opcode = INVOKEVIRTUAL;
	    if (constructor) mv.visitTypeInsn(NEW, enclosingType);
	    // INVOKESPECIAL for constructors and private methods,
	    // INVOKEINTERFACE for methods overriden from interfaces and
	    // INVOKEVIRTUAL for others
	    mv.visitMethodInsn(opcode, enclosingType, name, signature, interfaceFunc);
	}

    }

    public static class GenNodeNull extends GenNode implements IGenNodeExpr {

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitInsn(ACONST_NULL);
	}

    }

    public static class GenNodeInt extends GenNode implements IGenNodeExpr {
	public int val;

	public GenNodeInt(final int val) {
	    this.val = val;
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    switch (val) {
		case -1:
		    mv.visitInsn(ICONST_M1);
		case 0:
		    mv.visitInsn(ICONST_0);
		    break;
		case 1:
		    mv.visitInsn(ICONST_1);
		    break;
		case 2:
		    mv.visitInsn(ICONST_2);
		    break;
		case 3:
		    mv.visitInsn(ICONST_3);
		    break;
		case 4:
		    mv.visitInsn(ICONST_4);
		    break;
		case 5:
		    mv.visitInsn(ICONST_5);
		    break;
		default:
		    if ((val >= Byte.MIN_VALUE) && (val <= Byte.MAX_VALUE)) mv.visitIntInsn(BIPUSH, val);
		    else if ((val >= Short.MIN_VALUE) && (val <= Short.MAX_VALUE)) mv.visitIntInsn(SIPUSH, val);
		    else mv.visitLdcInsn(new Integer(val));
	    }
	}
    }

    public static class GenNodeString extends GenNode implements IGenNodeExpr {

	public String val;

	public GenNodeString(final String val) {
	    this.val = val;
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitLdcInsn(val);
	}

    }

    public static class GenNodeDouble extends GenNode implements IGenNodeExpr {

	public double val;

	public GenNodeDouble(final double val) {
	    this.val = val;
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    if (Double.compare(val, 0.0) == 0) mv.visitInsn(DCONST_0);
	    else if (Double.compare(val, 1.0) == 0) mv.visitInsn(DCONST_1);
	    else mv.visitLdcInsn(new Double(val));
	}

    }

    public static class GenNodeFloat extends GenNode implements IGenNodeExpr {

	public float val;

	public GenNodeFloat(final float val) {
	    this.val = val;
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    if (Float.compare(val, 0.0f) == 0) mv.visitInsn(FCONST_0);
	    else if (Float.compare(val, 1.0f) == 0) mv.visitInsn(FCONST_1);
	    else mv.visitLdcInsn(new Float(val));
	}

    }

    public static class GenNodeLong extends GenNode implements IGenNodeExpr {

	public long val;

	public GenNodeLong(final long val) {
	    this.val = val;
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    if (val == 0) mv.visitInsn(LCONST_0);
	    else if (val == 1) mv.visitInsn(LCONST_1);
	    else mv.visitLdcInsn(new Long(val));
	}

    }

    public static class GenNodeReturn extends GenNode {

	EnumInstructionOperand type;

	public GenNodeReturn(final EnumInstructionOperand type) {
	    this.type = type;
	}

	@Override
	public void generate(final Object visitor) {
	    int opcode = RETURN;
	    switch (type) {
		case ARRAY:
		case REFERENCE:
		    opcode = ARETURN;
		    break;
		case BOOL:
		case BYTE:
		case CHAR:
		case INT:
		case SHORT:
		    opcode = IRETURN;
		    break;
		case DOUBLE:
		    opcode = DRETURN;
		    break;
		case FLOAT:
		    opcode = FRETURN;
		    break;
		case LONG:
		    opcode = LRETURN;
		    break;
		case VOID:
		    opcode = RETURN;
		    break;
	    }
	    ((MethodVisitor) visitor).visitInsn(opcode);
	}

    }

    public static class GenNodeThis extends GenNode {

	@Override
	public void generate(final Object visitor) {
	    // The instance is stored as the first local variable
	    ((MethodVisitor) visitor).visitIntInsn(ALOAD, 0);
	}

    }

    public static class GenNodeConditionalJump extends GenNode {

	public IExpression expr;
	public Label label;
	public int opcode;

	public GenNodeConditionalJump(final IExpression expr, final Label label) {
	    this.expr = expr;
	    this.label = label;
	}

	public GenNodeConditionalJump(final int opcode, final Label label) {
	    this(null, label);
	    this.opcode = opcode;
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    if (expr != null) if (expr instanceof NodeBinary) {
		final NodeBinary node = (NodeBinary) expr;
		if (node.operatorOverloadFunc == null) {
		    // Get the precedent type of the operands to decide how they
		    // should be compared
		    final EnumInstructionOperand type = Semantics.getPrecedentType(node.exprType1, node.exprType2).getInstructionType();

		    // Cast the binary expression's sub expressions if necessary
		    if (node.exprType1.getInstructionType() != type) (new GenNodePrimitiveCast(node.exprType1.getInstructionType(), type)).generate(mv);
		    else if (node.exprType2.getInstructionType() != type) (new GenNodePrimitiveCast(node.exprType2.getInstructionType(), type)).generate(mv);

		    // Decide which opcode to use
		    switch (type) {
			case INT:
			case BYTE:
			case SHORT:
			case BOOL:
			    specialGenerate(mv, node.expr1, node.expr2);

			    // Integer type operands are handled by one single
			    // opcode
			    switch (node.operator.operation) {
				case LESS:
				    opcode = IF_ICMPLT;
				    break;
				case GREATER:
				    opcode = IF_ICMPGT;
				    break;
				case EQUAL:
				    opcode = IF_ICMPEQ;
				    break;
				case NOT_EQUAL:
				    opcode = IF_ICMPNE;
				    break;
				case LESS_EQUAL:
				    opcode = IF_ICMPLE;
				    break;
				case GREATER_EQUAL:
				    opcode = IF_ICMPGE;
				    break;
			    }
			    break;
			case LONG:
			case DOUBLE:
			case FLOAT:
			    specialGenerate(mv, node.expr1, node.expr2);
			    // Non integer-types must be compared first
			    if (type == EnumInstructionOperand.LONG) mv.visitInsn(LCMP);
			    else if (type == EnumInstructionOperand.DOUBLE) mv.visitInsn(DCMPG);
			    else if (type == EnumInstructionOperand.FLOAT) mv.visitInsn(FCMPG);
			    switch (node.operator.operation) {
				case LESS:
				    opcode = IFLT;
				    break;
				case GREATER:
				    opcode = IFGT;
				    break;
				case EQUAL:
				    opcode = IFEQ;
				    break;
				case NOT_EQUAL:
				    opcode = IFNE;
				    break;
				case LESS_EQUAL:
				    opcode = IFLE;
				    break;
				case GREATER_EQUAL:
				    opcode = IFGE;
				    break;
			    }
			    break;
			default:
			    // If it is a null comparison expression
			    final boolean expr1IsNull = node.expr1 instanceof NodeNull,
			    expr2IsNull = node.expr2 instanceof NodeNull;
			    if (expr1IsNull ^ expr2IsNull) {

				// Generate the expression that isn't a null
				// expression
				if (!expr1IsNull) node.expr1.generate();
				else node.expr2.generate();
				switch (node.operator.operation) {
				    case NOT_EQUAL:
					opcode = IFNONNULL;
					break;
				    case EQUAL:
					opcode = IFNULL;
					break;
				}
			    } else // Compare the references
			    switch (node.operator.operation) {
				case NOT_EQUAL:
				    opcode = IF_ACMPNE;
				    break;
				case EQUAL:
				    opcode = IF_ACMPEQ;
				    break;
			    }

		    }
		} else {
		    // If it is an operator overloaded expression, then generate
		    // the function call and then check if the return value was
		    // true
		    specialGenerate(mv, expr);
		    opcode = IFEQ;
		}
	    } else {
		// If it isn't a binary expression, then generate it and check
		// if the return value was true
		specialGenerate(mv, expr);
		opcode = IFEQ;
	    }
	    mv.visitJumpInsn(opcode, label);
	}

	/**
	 * Generates the IExpressions, and then generates the resulting GenNodes
	 * 
	 * @param visitor
	 * @param exprs
	 *
	 */
	private void specialGenerate(final MethodVisitor visitor, final IExpression... exprs) {
	    final int stmtPtr = currentFunction.stmts.size();
	    for (final IExpression expr : exprs) {
		expr.generate();
		int i = 0;
		final int num = currentFunction.stmts.size() - stmtPtr;
		// Run through the GenNodes generated by the expressions, remove
		// them from the function statements
		// and write them to the methodvisitor instead
		while (i++ < num)
		    currentFunction.stmts.remove(stmtPtr).generate(visitor);
	    }
	}

    }

    public static class GenNodePrimitiveCast extends GenNode {

	public EnumInstructionOperand fromType, toType;

	public GenNodePrimitiveCast(final EnumInstructionOperand fromType, final EnumInstructionOperand toType) {
	    this.fromType = fromType;
	    this.toType = toType;
	}

	@Override
	public void generate(final Object visitor) {}

    }

    public static class GenNodeJump extends GenNode {

	public Label label;

	public GenNodeJump(final Label label) {
	    this.label = label;
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitJumpInsn(GOTO, label);
	}

    }

    public static class GenNodeLabel extends GenNode {
	public Label label;

	public GenNodeLabel(final Label label) {
	    this.label = label;
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitLabel(label);
	}
    }

}
