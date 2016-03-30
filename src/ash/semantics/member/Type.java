package ash.semantics.member;

import ash.Ash;
import ash.grammar.node.NodeQualifiedName;
import ash.library.Library;
import ash.semantics.*;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.ParameterNode;

import javax.tools.*;
import java.io.*;
import java.util.*;

/**
 * Created by samtebbs on 26/03/2016.
 */
public class Type extends Member {

    public static Type OBJECT;
    public final List<Variable> fields = new LinkedList<>();
    public final List<Function> functions = new LinkedList<>();
    public final EnumType type;
    public static Optional<QualifiedName> currentPackage = Optional.empty();

    private static final Stack<Type> typeStack = new Stack<>();

    /**
     * Stores all found types
     */
    private static final HashMap<QualifiedName, Type> types = new HashMap<>();

    /**
     * Stores aliases for types. For example, when a type is imported, it can be referenced with the short name as it is added to the aliases map
     */
    public static final HashMap<String, QualifiedName> typeAliases = new HashMap<>();
    public Optional<Type> superClass = Optional.empty();
    public final List<Type> superInterfaces = new ArrayList<>();

    public static void loadJavaLang() {
        OBJECT = loadAndGet("java.lang.Object");
    }

    private static Type loadAndGet(String qualifiedName) {
        QualifiedName n = new QualifiedName(qualifiedName);
        loadType(n);
        return getType(n).get();
    }

    public Type(String name, List<Modifier> mods, EnumType type) {
        this(currentPackage.isPresent() ? currentPackage.get().add(name) : new QualifiedName(name), mods, type);
    }

    public Type(QualifiedName name, List<Modifier> mods, EnumType type) {
        super(name, mods);
        this.type = type;
    }

    public static void pushType(Type type) {
        typeStack.push(type);
        types.put(type.name, type);
        typeAliases.put(type.name.getShortName(), type.name);
    }

    public static Optional<Type> popType() {
        return !typeStack.isEmpty() ? Optional.of(typeStack.pop()) : Optional.empty();
    }

    public static Optional<Type> peekType() {
        return !typeStack.isEmpty() ? Optional.of(typeStack.peek()) : Optional.empty();
    }

    public static Optional<Type> getType(QualifiedName name) {
        if(types.containsKey(name)) return Optional.of(types.get(name));
        else {
            if(loadType(name)) return Optional.of(types.get(name));
            else return Optional.empty();
        }
    }

    public static Optional<Type> getType(String name) {
        if(typeAliases.containsKey(name)) return Optional.of(types.get(typeAliases.get(name)));
        else return Optional.empty();
    }

    private static boolean loadType(QualifiedName name) {
        for(String classPath : Ash.classPath) {
            String fullPath = classPath + File.separator + name.toPathString();
            Optional<FileInputStream> classStream;
            try {
                classStream = loadJavaClass(fullPath);
                if (!classStream.isPresent()) classStream = loadAshSource(fullPath);
                if (!classStream.isPresent()) classStream = loadJavaSource(fullPath);
                if(classStream.isPresent()) {
                    types.put(name, loadClassStream(classStream.get()));
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Optional<InputStream> stream = Library.getClassStream(name.toString());
        if(stream.isPresent()) {
            types.put(name, loadClassStream(stream.get()));
            return true;
        }
        return false;
    }

    private static Optional<FileInputStream> loadAshSource(String fullPath) throws IOException {
        File sourceFile = new File(fullPath + ".ash");
        if(sourceFile.exists() && Ash.compile(sourceFile)) return loadJavaClass(fullPath);
        else return Optional.empty();
    }

    private static Optional<FileInputStream> loadJavaClass(String fullPath) throws FileNotFoundException {
        File sourceFile = new File(fullPath + ".class");
        return sourceFile.exists() ? Optional.of(new FileInputStream(sourceFile)) : Optional.empty();
    }

    private static Optional<FileInputStream> loadJavaSource(String fullPath) throws IOException {
        File sourceFile = new File(fullPath + ".java");
        if(sourceFile.exists()) {
            // Compile java source file
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null,
                    null, compilationUnits);
            boolean success = task.call();
            fileManager.close();
            if(success) return loadJavaClass(fullPath);
        }
        return Optional.empty();
    }

    private static Type loadClassStream(InputStream fileInputStream) {
        ClassNode node = null;
        try {
            final ClassReader reader = new ClassReader(fileInputStream);
            node = new ClassNode();
            reader.accept(node, 0);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type type = new Type(QualifiedName.fromPathString(node.name), Modifier.asList(node.access), EnumType.fromInt(node.access));
        if(node.superName != null) {
            Optional<Type> superType = getType(QualifiedName.fromPathString(node.superName));
            if(superType.isPresent()) type.superClass = Optional.of(superType.get());
        }
        node.interfaces.forEach(ifc -> {
            Optional<Type> ifcType = getType(QualifiedName.fromPathString(ifc));
            if(ifcType.isPresent()) type.superInterfaces.add(ifcType.get());
        });
        node.methods.forEach(mth -> {
            TypeInstance returnType = TypeInstance.fromASMType(jdk.internal.org.objectweb.asm.Type.getReturnType(mth.desc));
            Function func = new Function(type.name.add(mth.name), Modifier.asList(mth.access), returnType);
            jdk.internal.org.objectweb.asm.Type[] paramTypes = jdk.internal.org.objectweb.asm.Type.getArgumentTypes(mth.desc);
            int i = 0;
            if(mth.parameters != null) {
                for (ParameterNode param : mth.parameters) {
                    func.params.params.add(new Parameters.Parameter(TypeInstance.fromASMType(paramTypes[i++]), param.name, false));
                }
            }
            type.functions.add(func);
        });
        node.fields.forEach(field -> type.fields.add(new Variable(field.name, Modifier.asList(field.access), TypeInstance.fromASMType(jdk.internal.org.objectweb.asm.Type.getType(field.desc)))));
        return type;
    }

    public static boolean typeExists(QualifiedName qualifiedName) {
        if(types.containsKey(qualifiedName)) return true;
        else return qualifiedName.sections.size() == 1 && typeAliases.containsKey(qualifiedName.sections.get(0));
    }

    public static Type currentType() {
        return typeStack.peek();
    }
}
