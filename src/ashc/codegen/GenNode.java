package ashc.codegen;

import static org.objectweb.asm.Opcodes.*;

import java.io.*;
import java.util.*;

import org.objectweb.asm.*;

import ashc.semantics.Member.Field;
import ashc.semantics.Member.Function;
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
    
    public static interface IGenNodeExpr  {
	
    }
    
    public static enum EnumInstructionOperand {
	REFERENCE, BOOL, BYTE, CHAR, INT, LONG, FLOAT, DOUBLE, ARRAY, SHORT
    }

    public static class GenNodeType extends GenNode {

	public String name, superclass;
	public String[] interfaces;
	public int modifiers;
	public LinkedList<GenNodeField> fields = new LinkedList<GenNodeField>();
	public LinkedList<GenNodeFunction> functions = new LinkedList<GenNodeFunction>();

	public GenNodeType(final String name, final String superclass, final String[] interfaces, final int modifiers) {
	    this.name = name;
	    this.superclass = superclass;
	    this.interfaces = interfaces;
	    this.modifiers = modifiers;
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
	public LinkedList<IGenNodeStmt> stmts = new LinkedList<IGenNodeStmt>();

	public GenNodeFunction(final String name, final int modifiers, final String type) {
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
	    for(IGenNodeStmt stmt : stmts) ((GenNode) stmt).generate(mv);
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

	public GenNodeVarAssign(final int varID, IGenNodeExpr expr, EnumInstructionOperand operand) {
	    this.varID = varID;
	    this.expr = expr;
	    this.operand = operand;
	}

	@Override
	public void generate(final Object visitor) {
	    MethodVisitor mv = (MethodVisitor)visitor;
	    ((GenNode)expr).generate(mv);
	    int opcode = ASTORE;
	    mv.visitVarInsn(opcode, varID);
	}

    }
    
    public static class GenNodeFieldAssign extends GenNode implements IGenNodeStmt {
	
	public String varName;
	public String enclosingType;
	public String type;
	public IGenNodeExpr expr;

	public GenNodeFieldAssign(String varName, String enclosingType, String type, IGenNodeExpr expr) {
	    this.varName = varName;
	    this.enclosingType = enclosingType;
	    this.type = type;
	    this.expr = expr;
	}

	@Override
	public void generate(Object visitor) {
	    System.out.println(this);
	    MethodVisitor mv = (MethodVisitor) visitor;
	    ((GenNode)expr).generate(mv);
	    mv.visitFieldInsn(PUTFIELD, enclosingType, varName, type);
	}

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
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
	public void generate(Object visitor) {
	    MethodVisitor mv = (MethodVisitor)visitor;
	    mv.visitFieldInsn(GETFIELD, enclosingType, varName, type);
	}
	
    }
    
    public static class GenNodeFieldStore extends GenNode implements IGenNodeStmt {
	
	public String varName, enclosingType, type;

	public GenNodeFieldStore(String varName, String enclosingType, String type) {
	    this.varName = varName;
	    this.enclosingType = enclosingType;
	    this.type = type;
	}

	@Override
	public void generate(Object visitor) {
	    ((MethodVisitor)visitor).visitFieldInsn(PUTFIELD, enclosingType, varName, type);
	}
	
    }
    
    public static class GenNodeVarLoad extends GenNode implements IGenNodeExpr {
	
	public EnumInstructionOperand operand;
	public int varID;

	public GenNodeVarLoad(EnumInstructionOperand operand, int varID) {
	    this.operand = operand;
	    this.varID = varID;
	}

	@Override
	public void generate(Object visitor) {
	    MethodVisitor mv = (MethodVisitor)visitor;
	    int opcode = ALOAD;
	    switch(operand){
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
	public boolean interfaceFunc, publicFunc, staticFunc;

	public GenNodeFuncCall(String enclosingType, String name, String signature, boolean interfaceFunc, boolean publicFunc, boolean staticFunc) {
	    this.enclosingType = enclosingType;
	    this.name = name;
	    this.signature = signature;
	    this.interfaceFunc = interfaceFunc;
	    this.publicFunc = publicFunc;
	    this.staticFunc = staticFunc;
	}

	@Override
	public void generate(Object visitor) {
	    MethodVisitor mv = (MethodVisitor)visitor;
	    int opcode = INVOKESPECIAL;
	    if(interfaceFunc) opcode = INVOKEINTERFACE;
	    else if(staticFunc) opcode = INVOKESTATIC;
	    else if(publicFunc) opcode = INVOKEVIRTUAL;
	    // INVOKESPECIAL for constructors and private methods, INVOKEINTERFACE for methods overriden from interfaces and INVOKEVIRTUAL for others
	    mv.visitMethodInsn(opcode, enclosingType, name, signature, interfaceFunc);
	}
	
    }

}
