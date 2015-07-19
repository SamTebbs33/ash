package ashc.codegen;

import static org.objectweb.asm.Opcodes.*;

import java.io.*;
import java.util.*;

import org.objectweb.asm.*;

import ashc.codegen.GenNode.GenNodeFunction.LocalVariable;
import ashc.error.*;
import ashc.grammar.Node.IExpression;
import ashc.grammar.Node.NodeBinary;
import ashc.grammar.Node.NodeNull;
import ashc.grammar.*;
import ashc.grammar.Operator.EnumOperation;
import ashc.main.*;
import ashc.semantics.Member.Field;
import ashc.semantics.*;

/**
 * Ash
 *
 * @author samtebbs, 11:23:26 - 7 Jul 2015
 */
public abstract class GenNode {

    private static LinkedList<GenNodeType> types = new LinkedList<GenNodeType>();
    private static Stack<GenNodeType> typeStack = new Stack<GenNodeType>();
    public static HashSet<String> generatedTupleClasses = new HashSet<String>();
    private static Stack<GenNodeFunction> functionStack = new Stack<GenNodeFunction>();

    public abstract void generate(Object visitor);

    public static void generate() {
	for (final GenNodeType type : types)
	    type.generate(null);
    }

    public static void addToStackRequirement(final int toAdd) {
	if ((getCurrentFunction().stack + toAdd) > getCurrentFunction().maxStack) getCurrentFunction().maxStack = getCurrentFunction().stack + toAdd;
	getCurrentFunction().stack += toAdd;

    }

    public static void addGenNodeType(final GenNodeType node) {
	types.add(node);
	typeStack.push(node);
    }

    public static void addGenNodeFunction(final GenNodeFunction genNodeFunc) {
	typeStack.peek().functions.add(genNodeFunc);
	functionStack.push(genNodeFunc);
    }

    public static void addGenNodeField(final GenNodeField field) {
	typeStack.peek().fields.add(field);
    }

    public static void addFuncStmt(final GenNode node) {
	// System.out.printf("Adding: %s to func %s%n", node,
	// getCurrentFunction());
	functionStack.peek().stmts.add(node);
    }

    public static void exitGenNodeType() {
	typeStack.pop();
    }

    public static void exitGenNodeFunction() {
	functionStack.pop();
    }

    public static GenNodeFunction getCurrentFunction() {
	return functionStack.peek();
    }

    public static interface IGenNodeStmt {

    }

    public static interface IGenNodeExpr {

    }

    public static enum EnumInstructionOperand {
	REFERENCE(1), BOOL(1), BYTE(1), CHAR(1), INT(1), LONG(2), FLOAT(1), DOUBLE(2), ARRAY(1), SHORT(1), VOID(0);

	public int size;

	private EnumInstructionOperand(final int size) {
	    this.size = size;
	}
    }

    public static class GenNodeType extends GenNode {

	public String name, superclass, shortName;
	public String[] interfaces;
	public int modifiers;
	private final LinkedList<GenNodeField> fields = new LinkedList<GenNodeField>();
	private final LinkedList<GenNodeFunction> functions = new LinkedList<GenNodeFunction>();
	public final LinkedList<String> generics = new LinkedList<String>();

	public GenNodeType(final String name, final String shortName, final String superclass, final String[] interfaces, final int modifiers) {
	    this.name = name;
	    this.superclass = superclass;
	    this.interfaces = interfaces;
	    this.modifiers = modifiers;
	}

	public void addField(final GenNodeField field) {
	    fields.add(field);
	}

	@Override
	public void generate(final Object visitor) {
	    final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
	    StringBuffer genericsSignature = null;
	    if (generics.size() > 0) {
		genericsSignature = new StringBuffer();
		genericsSignature.append("<");
		for (final String g : generics)
		    genericsSignature.append(g + ":" + "Ljava/lang/Object;");
		genericsSignature.append(">Ljava/lang/Object;");
	    }
	    cw.visit(52, modifiers | Opcodes.ACC_SUPER, name, genericsSignature != null ? genericsSignature.toString() : null, superclass, interfaces);
	    final String[] folders = name.split("\\.");
	    int i = 0;
	    final StringBuffer dirSb = new StringBuffer(AshMain.outputDir);
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
	    AshError.verboseMsg("Generating class: " + classFile.getAbsolutePath());
	    try {
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

	public static class LocalVariable {
	    public String name, type;
	    public int id;
	    public boolean endLabelGenerated = true;
	    public GenNodeLabel end = new GenNodeLabel(new Label());

	    public LocalVariable(final String name, final String type, final int id) {
		this.name = name;
		this.type = type;
		this.id = id;
		addFuncStmt(end);
	    }

	    public void updateUse() {
		// endLabelGenerated = true;
		// addFuncStmt(end);
	    }

	}

	public String name, enclosingTypeName;
	public int modifiers;
	public String type;
	public LinkedList<TypeI> params = new LinkedList<TypeI>();
	public LinkedList<GenNode> stmts = new LinkedList<GenNode>();
	public int stack, maxStack;
	private final HashMap<Integer, LocalVariable> locals = new HashMap<Integer, LocalVariable>();

	public GenNodeFunction(final String name, final int modifiers, final String type) {
	    this.name = name;
	    this.modifiers = modifiers;
	    this.type = type;
	    enclosingTypeName = typeStack.peek().name;
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
	    for (int i = 0; i < stmts.size(); i++)
		stmts.get(i).generate(mv);
	    for (final LocalVariable local : locals.values())
		if (!local.endLabelGenerated) mv.visitLabel(local.end.label);
	    // System.out.printf("Function: name=%s, stack=%d, locals=%d%n",
	    // name, maxStack, locals.size());
	    mv.visitMaxs(-1, -1);
	    mv.visitEnd();
	}

	public void addLocal(final LocalVariable local) {
	    locals.put(local.id, local);
	}

	public LocalVariable getLocal(final int id) {
	    return locals.get(id);
	}

    }

    public static class GenNodeField extends GenNode {

	public int modifiers;
	public String name, type, genericType;

	public GenNodeField(final int modifiers, final String name, final String type) {
	    this.modifiers = modifiers;
	    this.name = name;
	    this.type = type;
	}

	public GenNodeField(final int modifiers, final String name, final String type, final String genericType) {
	    this(modifiers, name, type);
	    this.genericType = "T" + genericType + ";";
	}

	public GenNodeField(final Field field) {
	    this(field.modifiers, field.id, field.type.toBytecodeName());
	}

	@Override
	public void generate(final Object visitor) {
	    final ClassWriter cw = (ClassWriter) visitor;
	    final FieldVisitor fieldV = cw.visitField(modifiers, name, type, genericType, null);
	    fieldV.visitEnd();
	}

	@Override
	public String toString() {
	    return "GenNodeField [modifiers=" + modifiers + ", name=" + name + ", type=" + type + "]";
	}

    }

    public static class GenNodeVar extends GenNode {

	public LocalVariable local;
	public Label start = new Label();
	public String generics;

	public GenNodeVar(final String name, final String type, final int id, final String generics) {
	    local = new GenNodeFunction.LocalVariable(name, type, id);
	    this.generics = generics;
	    getCurrentFunction().addLocal(local);
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = ((MethodVisitor) visitor);
	    // mv.visitLabel(start);
	    mv.visitLocalVariable(local.name, local.type, generics, start, local.end.label, local.id);
	}

    }

    public static class GenNodeFieldLoad extends GenNode implements IGenNodeExpr {

	public String varName, enclosingType, type;
	boolean isStatic;

	public GenNodeFieldLoad(final String varName, final String enclosingType, final String type, final boolean isStatic) {
	    this.varName = varName;
	    this.enclosingType = enclosingType;
	    this.type = type;
	    addToStackRequirement(1);
	    this.isStatic = isStatic;
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    mv.visitFieldInsn(isStatic ? GETSTATIC : GETFIELD, enclosingType, varName, type);
	}

    }

    public static class GenNodeFieldStore extends GenNode implements IGenNodeStmt {

	public String varName, enclosingType, type;
	public boolean isStatic;

	public GenNodeFieldStore(final String varName, final String enclosingType, final String type, final boolean isStatic) {
	    this.varName = varName;
	    this.enclosingType = enclosingType;
	    this.type = type;
	    this.isStatic = isStatic;
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitFieldInsn(isStatic ? PUTSTATIC : PUTFIELD, enclosingType, varName, type);
	}

    }

    public static class GenNodeVarStore extends GenNode {

	public EnumInstructionOperand operand;
	public int varID;

	public GenNodeVarStore(final EnumInstructionOperand instructionType, final int localID) {
	    operand = instructionType;
	    varID = localID;
	    addToStackRequirement(-operand.size);
	    getCurrentFunction().getLocal(localID).updateUse();
	}

	@Override
	public void generate(final Object visitor) {
	    int opcode = 0;
	    switch (operand) {
		case BYTE:
		case INT:
		case SHORT:
		case BOOL:
		case CHAR:
		    opcode = ISTORE;
		    break;
		case DOUBLE:
		    opcode = DSTORE;
		    break;
		case FLOAT:
		    opcode = FSTORE;
		    break;
		case LONG:
		    opcode = LSTORE;
		    break;
		case ARRAY:
		case REFERENCE:
		    opcode = ASTORE;
	    }
	    ((MethodVisitor) visitor).visitVarInsn(opcode, varID);
	}

    }

    public static class GenNodeVarLoad extends GenNode implements IGenNodeExpr {

	public EnumInstructionOperand operand;
	public int varID;

	public GenNodeVarLoad(final EnumInstructionOperand operand, final int varID) {
	    this.operand = operand;
	    this.varID = varID;
	    addToStackRequirement(operand.size);
	    getCurrentFunction().getLocal(varID).updateUse();
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    int opcode = 0;
	    switch (operand) {
		case REFERENCE:
		case ARRAY:
		    opcode = ALOAD;
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
	    }
	    mv.visitVarInsn(opcode, varID);
	}

    }

    public static class GenNodeFuncCall extends GenNode implements IGenNodeExpr, IGenNodeStmt {

	public String enclosingType, name, signature;
	public boolean interfaceFunc, privateFunc, staticFunc, constructor;

	public GenNodeFuncCall(final String enclosingType, final String name, final String signature, final boolean interfaceFunc, final boolean privateFunc, final boolean staticFunc, final boolean constructor) {
	    this.enclosingType = enclosingType;
	    this.name = name;
	    this.signature = signature;
	    this.interfaceFunc = interfaceFunc;
	    this.privateFunc = privateFunc;
	    this.staticFunc = staticFunc;
	    this.constructor = constructor;
	    addToStackRequirement(signature.endsWith("V") ? 0 : EnumInstructionOperand.REFERENCE.size);
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    int opcode = INVOKEVIRTUAL;
	    if (interfaceFunc) opcode = INVOKEINTERFACE;
	    if (staticFunc) opcode = INVOKESTATIC;
	    if (constructor || privateFunc) opcode = INVOKESPECIAL;
	    // INVOKESPECIAL for constructors and private methods,
	    // INVOKEINTERFACE for methods overriden from interfaces and
	    // INVOKEVIRTUAL for others
	    mv.visitMethodInsn(opcode, enclosingType, name, signature, interfaceFunc);
	}

    }

    public static class GenNodeNull extends GenNode implements IGenNodeExpr {

	public GenNodeNull() {
	    super();
	    addToStackRequirement(2);
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitInsn(ACONST_NULL);
	}

    }

    public static class GenNodeInt extends GenNode implements IGenNodeExpr {
	public int val;

	public GenNodeInt(final int val) {
	    this.val = val;
	    addToStackRequirement(1);
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
	    addToStackRequirement(1);
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
	    addToStackRequirement(2);
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
	    addToStackRequirement(1);
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
	    addToStackRequirement(2);
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
	    addToStackRequirement(type.size);
	}

	public GenNodeReturn() {
	    this(EnumInstructionOperand.VOID);
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

	public GenNodeThis() {
	    super();
	    addToStackRequirement(1);
	}

	@Override
	public void generate(final Object visitor) {
	    // The instance is stored as the first local variable
	    ((MethodVisitor) visitor).visitVarInsn(ALOAD, 0);
	}

    }

    public static class GenNodeConditionalJump extends GenNode {

	public IExpression expr;
	public Label label;
	public int opcode;

	LinkedList<Integer> extraOpcodes = new LinkedList<Integer>();

	public GenNodeConditionalJump(final IExpression expr, final Label label) {
	    this.expr = expr;
	    this.label = label;
	    compute();
	    addToStackRequirement(-1);
	}

	public GenNodeConditionalJump(final int opcode, final Label label) {
	    this(null, label);
	    this.opcode = opcode;
	}

	public void compute() {
	    if (expr != null) if (expr instanceof NodeBinary) {
		final NodeBinary node = (NodeBinary) expr;
		if (node.operatorOverloadFunc == null) {
		    // Get the precedent type of the operands to decide how they
		    // should be compared
		    final EnumInstructionOperand type = Semantics.getPrecedentType(node.exprType1, node.exprType2).getInstructionType();

		    // Cast the binary expression's sub expressions if necessary
		    if (node.exprType1.getInstructionType() != type) addFuncStmt(new GenNodePrimitiveCast(node.exprType1.getInstructionType(), type));
		    else if (node.exprType2.getInstructionType() != type) addFuncStmt(new GenNodePrimitiveCast(node.exprType2.getInstructionType(), type));

		    // Decide which opcode to use
		    switch (type) {
			case INT:
			case BYTE:
			case SHORT:
			case BOOL:
			    node.expr1.generate();
			    node.expr2.generate();

			    // Integer type operands are handled by one single
			    // opcode
			    switch (node.operator.operation) {
				case LESS:
				    opcode = IF_ICMPGE;
				    break;
				case GREATER:
				    opcode = IF_ICMPLE;
				    break;
				case EQUAL:
				    opcode = IF_ICMPNE;
				    break;
				case NOT_EQUAL:
				    opcode = IF_ICMPEQ;
				    break;
				case LESS_EQUAL:
				    opcode = IF_ICMPGT;
				    break;
				case GREATER_EQUAL:
				    opcode = IF_ICMPLT;
				    break;
			    }
			    break;
			case LONG:
			case DOUBLE:
			case FLOAT:
			    node.expr1.generate();
			    node.expr2.generate();
			    // Non integer-types must be compared first
			    if (type == EnumInstructionOperand.LONG) extraOpcodes.add(Opcodes.LCMP);
			    else if (type == EnumInstructionOperand.DOUBLE) extraOpcodes.add(DCMPG);
			    else if (type == EnumInstructionOperand.FLOAT) extraOpcodes.add(FCMPG);
			    switch (node.operator.operation) {
				case LESS:
				    opcode = IFGE;
				    break;
				case GREATER:
				    opcode = IFLE;
				    break;
				case EQUAL:
				    opcode = IFNE;
				    break;
				case NOT_EQUAL:
				    opcode = IFEQ;
				    break;
				case LESS_EQUAL:
				    opcode = IFGT;
				    break;
				case GREATER_EQUAL:
				    opcode = IFLT;
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
					opcode = IFNULL;
					break;
				    case EQUAL:
					opcode = IFNONNULL;
					break;
				}
			    } else // Compare the references
			    switch (node.operator.operation) {
				case NOT_EQUAL:
				    opcode = IF_ACMPEQ;
				    break;
				case EQUAL:
				    opcode = IF_ACMPNE;
				    break;
			    }

		    }
		} else {
		    // If it is an operator overloaded expression, then generate
		    // the function call and then check if the return value was
		    // true
		    expr.generate();
		    opcode = IFEQ;
		}
	    } else {
		// If it isn't a binary expression, then generate it and check
		// if the return value was true
		expr.generate();
		opcode = IFEQ;
	    }
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    for (final Integer i : extraOpcodes)
		mv.visitInsn(i);
	    mv.visitJumpInsn(opcode, label);
	}

    }

    public static class GenNodePrimitiveCast extends GenNode {

	public EnumInstructionOperand fromType, toType;

	public GenNodePrimitiveCast(final EnumInstructionOperand fromType, final EnumInstructionOperand toType) {
	    this.fromType = fromType;
	    this.toType = toType;
	    addToStackRequirement(toType.size - fromType.size);
	}

	@Override
	public void generate(final Object visitor) {
	    int opcode = 0;
	    switch (fromType) {
		case INT:
		case SHORT:
		case CHAR:
		case BYTE:
		    switch (toType) {
			case BYTE:
			    opcode = I2B;
			    break;
			case CHAR:
			    opcode = I2C;
			    break;
			case SHORT:
			    opcode = I2S;
			    break;
			case LONG:
			    opcode = I2L;
			    break;
			case FLOAT:
			    opcode = I2F;
			    break;
			case DOUBLE:
			    opcode = I2D;
			    break;
		    }
		    break;
		case LONG:
		    switch (toType) {
			case INT:
			case SHORT:
			case CHAR:
			case BYTE:
			    opcode = L2I;
			    break;
			case FLOAT:
			    opcode = L2F;
			    break;
			case DOUBLE:
			    opcode = L2D;
			    break;
		    }
		    break;
		case FLOAT:
		    switch (toType) {
			case INT:
			case SHORT:
			case CHAR:
			case BYTE:
			    opcode = F2I;
			    break;
			case LONG:
			    opcode = F2L;
			    break;
			case DOUBLE:
			    opcode = F2D;
			    break;
		    }
		    break;
		case DOUBLE:
		    switch (toType) {
			case INT:
			case SHORT:
			case CHAR:
			case BYTE:
			    opcode = D2I;
			    break;
			case LONG:
			    opcode = D2L;
			    break;
			case FLOAT:
			    opcode = D2F;
			    break;
		    }
		    break;
	    }
	    ((MethodVisitor) visitor).visitInsn(opcode);
	}

    }

    public static class GenNodeJump extends GenNode {

	public Label label;
	public int opcode;

	public GenNodeJump(final Label label) {
	    this(GOTO, label);
	}

	public GenNodeJump(final int opcode, final Label lbl0) {
	    this.opcode = opcode;
	    label = lbl0;
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitJumpInsn(opcode, label);
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

    public static class GenNodeBinary extends GenNode {
	public Operator operator;
	public EnumInstructionOperand type;

	public GenNodeBinary(final Operator operator, final EnumInstructionOperand type) {
	    this.operator = operator;
	    this.type = type;
	    addToStackRequirement(type.size);
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    int opcode = 0;
	    final EnumOperation operation = operator.operation;
	    switch (type) {
		case BYTE:
		case CHAR:
		case INT:
		case SHORT:
		case BOOL:
		    switch (operation) {
			case ADD:
			    opcode = IADD;
			    break;
			case BIT_AND:
			    opcode = IAND;
			    break;
			case BIT_OR:
			    opcode = IOR;
			    break;
			case BIT_XOR:
			    opcode = IXOR;
			    break;
			case DIVIDE:
			    opcode = IDIV;
			    break;
			case L_SHIFT:
			    opcode = ISHL;
			    break;
			case R_SHIFT:
			    opcode = ISHR;
			    break;
			case R_SHIFT_LOGICAL:
			    opcode = IUSHR;
			    break;
			case MOD:
			    opcode = IREM;
			    break;
			case MULTIPLY:
			    opcode = IMUL;
			    break;
			// case POW: Pow is not defined for integers
			case SUBTRACT:
			    opcode = ISUB;
			    break;
			default:
			    // I curse Java for not having an opcode that simply
			    // checks integer equality...
			    final Label l0 = new Label(),
			    l1 = new Label();
			    if (operation == EnumOperation.EQUAL) opcode = IF_ICMPNE;
			    else if (operation == EnumOperation.GREATER) opcode = IF_ICMPLE;
			    else if (operation == EnumOperation.LESS) opcode = IF_ICMPGE;
			    else if (operation == EnumOperation.NOT_EQUAL) opcode = IF_ICMPEQ;
			    else if (operation == EnumOperation.GREATER_EQUAL) opcode = IF_ICMPLT;
			    else if (operation == EnumOperation.LESS_EQUAL) opcode = IF_ICMPGT;
			    else if (operation == EnumOperation.AND) {
				// Checks if either of the two pushed
				// expressions are 0, and if so, jumps to label
				// 0
				mv.visitJumpInsn(IFEQ, l0);
				mv.visitJumpInsn(IFEQ, l0);
			    } else if (operation == EnumOperation.OR) {
				// Checks if either of the two pushed
				// expressions are 0, and if so, jumps to label
				// 0
				mv.visitJumpInsn(IFNE, l0);
				mv.visitJumpInsn(IFNE, l0);
			    }
			    mv.visitJumpInsn(opcode, l0);
			    mv.visitInsn(ICONST_1);
			    mv.visitJumpInsn(GOTO, l1);
			    mv.visitLabel(l0);
			    mv.visitInsn(ICONST_0);
			    mv.visitLabel(l1);
			    return; // No more to do here
		    }
		    break;
		default:
		    switch (operation) {
			case ADD:
			    if (type == EnumInstructionOperand.DOUBLE) opcode = DADD;
			    else if (type == EnumInstructionOperand.FLOAT) opcode = FADD;
			    else if (type == EnumInstructionOperand.LONG) opcode = LADD;
			    break;
			case SUBTRACT:
			    if (type == EnumInstructionOperand.DOUBLE) opcode = DSUB;
			    else if (type == EnumInstructionOperand.FLOAT) opcode = FSUB;
			    else if (type == EnumInstructionOperand.LONG) opcode = LSUB;
			    break;
			case DIVIDE:
			    if (type == EnumInstructionOperand.DOUBLE) opcode = DDIV;
			    else if (type == EnumInstructionOperand.FLOAT) opcode = FDIV;
			    else if (type == EnumInstructionOperand.LONG) opcode = LDIV;
			    break;
			case MOD:
			    if (type == EnumInstructionOperand.DOUBLE) opcode = DREM;
			    else if (type == EnumInstructionOperand.FLOAT) opcode = FREM;
			    else if (type == EnumInstructionOperand.LONG) opcode = LREM;
			    break;
			case MULTIPLY:
			    if (type == EnumInstructionOperand.DOUBLE) opcode = DMUL;
			    else if (type == EnumInstructionOperand.FLOAT) opcode = FMUL;
			    else if (type == EnumInstructionOperand.LONG) opcode = LMUL;
			    break;
			case POW:
			    // Only doubles should be using this
			    final GenNodeFuncCall powCall = new GenNodeFuncCall("java/lang/Math", "pow", "(DD)D", false, false, true, false);
			    powCall.generate(mv);
			    return; // No more to do here
			case EQUAL:
			case LESS:
			case GREATER:
			case NOT_EQUAL:
			case LESS_EQUAL:
			case GREATER_EQUAL:
			    int compOpcode = DCMPL;
			    if (type == EnumInstructionOperand.FLOAT) compOpcode = FCMPL;
			    else if (type == EnumInstructionOperand.LONG) compOpcode = LCMP;
			    mv.visitInsn(compOpcode);

			    if (operation == EnumOperation.EQUAL) opcode = IFNE;
			    else if (operation == EnumOperation.NOT_EQUAL) opcode = IFEQ;
			    else if (operation == EnumOperation.LESS) opcode = IFGE;
			    else if (operation == EnumOperation.GREATER) opcode = IFLE;
			    else if (operation == EnumOperation.LESS_EQUAL) opcode = IFGT;
			    else if (operation == EnumOperation.GREATER_EQUAL) opcode = IFLT;
			    final Label l0 = new Label(),
				    l1 = new Label();
			    mv.visitJumpInsn(opcode, l0);
			    mv.visitInsn(ICONST_1);
			    mv.visitJumpInsn(GOTO, l1);
			    mv.visitLabel(l0);
			    mv.visitInsn(ICONST_0);
			    mv.visitLabel(l1);
			    return;
		    }
		    break;
	    }
	    mv.visitInsn(opcode);
	}

    }

    public static class GenNodeUnary extends GenNode {
	public EnumInstructionOperand type;
	public Operator operator;
	boolean prefix;

	public GenNodeUnary(final EnumInstructionOperand type, final Operator operator, final boolean prefix) {
	    this.type = type;
	    this.operator = operator;
	    this.prefix = prefix;
	}

	@Override
	public void generate(final Object visitor) {
	    final MethodVisitor mv = (MethodVisitor) visitor;
	    if (!prefix) mv.visitInsn(DUP);
	    // TODO:
	}

    }

    public static class GenNodeOpcode extends GenNode {
	public int opcode;

	public GenNodeOpcode(final int opcode) {
	    this.opcode = opcode;
	    if (opcode == DUP) addToStackRequirement(1);
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitInsn(opcode);
	}
    }

    public static class GenNodeNew extends GenNode {
	public String type;

	public GenNodeNew(final String type) {
	    this.type = type;
	    addToStackRequirement(1);
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitTypeInsn(NEW, type);
	}
    }

    public static class GenNodeTypeOpcode extends GenNode {
	int opcode;
	String type;

	public GenNodeTypeOpcode(final int opcode, final String type) {
	    this.opcode = opcode;
	    this.type = type;
	    addToStackRequirement(1);
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitTypeInsn(opcode, type);
	}
    }

    public static class GenNodeIntOpcode extends GenNode {
	public int opcode;
	public int operand;

	public GenNodeIntOpcode(final int opcode, final int operand) {
	    this.opcode = opcode;
	    this.operand = operand;
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitIntInsn(opcode, operand);
	}
    }

    public static class GenNodeArrayIndexLoad extends GenNode {
	public EnumInstructionOperand type;

	public GenNodeArrayIndexLoad(final EnumInstructionOperand type) {
	    this.type = type;
	    addToStackRequirement(type.size - 1);
	}

	@Override
	public void generate(final Object visitor) {
	    int opcode = 0;
	    switch (type) {
		case ARRAY:
		    
		case REFERENCE:
		    opcode = AALOAD;
		    break;
		case BOOL:
		case BYTE:
		    opcode = BALOAD;
		    break;
		case CHAR:
		    opcode = CALOAD;
		    break;
		case SHORT:
		    opcode = SALOAD;
		    break;
		case INT:
		    opcode = IALOAD;
		    break;
		case LONG:
		    opcode = LALOAD;
		    break;
		case FLOAT:
		    opcode = FALOAD;
		    break;
		case DOUBLE:
		    opcode = DALOAD;
		    break;
	    }
	    ((MethodVisitor) visitor).visitInsn(opcode);
	}
    }

    public static class GenNodeIncrement extends GenNode {

	public int varID, amount;

	public GenNodeIncrement(final int varID, final int amount) {
	    this.varID = varID;
	    this.amount = amount;
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitIincInsn(varID, amount);
	}

    }

    public static class GenNodeMultiDimArray extends GenNode {

	public String type;
	public int dims;

	public GenNodeMultiDimArray(final String type, final int dims) {
	    this.type = type;
	    this.dims = dims;
	}

	@Override
	public void generate(final Object visitor) {
	    ((MethodVisitor) visitor).visitMultiANewArrayInsn(type, dims);
	}

    }

}
