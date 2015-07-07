package ashc.codegen;

import java.io.*;
import java.util.*;

import org.objectweb.asm.*;

import ashc.semantics.Member.Field;
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

	public String varName;

	public GenNodeVarAssign(final String varName) {
	    this.varName = varName;
	}

	@Override
	public void generate(final Object visitor) {}

    }

}
