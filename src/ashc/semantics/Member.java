package ashc.semantics;

import ashc.grammar.EnumModifier;
import ashc.grammar.Node.IExpression;
import ashc.grammar.Node.NodeExprs;
import ashc.grammar.OperatorDef.EnumOperatorType;
import ashc.load.TypeImporter;
import ashc.util.BitOp;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Ash
 *
 * @author samtebbs, 15:02:05 - 23 May 2015
 */
public class Member {

    public QualifiedName qualifiedName;
    public int modifiers;
    public Type enclosingType;
    public boolean isLocal;

    public Member(final QualifiedName qualifiedName, final int modifiers) {
        this.qualifiedName = qualifiedName;
        this.modifiers = modifiers;
    }

    public boolean isVisible() {
        if (isLocal) return true;
        // Check if the member was declared as public, or no modifiers were
        // given (Members are implicitly public)
        if (BitOp.and(modifiers, EnumModifier.PUBLIC.intVal) || (modifiers == 0)) return true;
        // If the current type is the same type as the member's enclosing type,
        // the member is visible
        if ((enclosingType != null) && enclosingType.equals(Semantics.currentType())) return true;
        // If the member is private, it isn't visible
        if (BitOp.and(modifiers, EnumModifier.PRIVATE.intVal)) return false;
        // If the current type is a sub-type of the member's enclosing type, it
        // is visible
        return Semantics.currentType().hasSuper(enclosingType.qualifiedName);
    }

    public boolean isStatic() {
        return BitOp.and(modifiers, EnumModifier.STATIC.intVal);
    }

    public boolean isPrivate() {
        return BitOp.and(modifiers, EnumModifier.PRIVATE.intVal);
    }

    public enum EnumType {
        CLASS(0), ENUM(Opcodes.ACC_ENUM), INTERFACE(Opcodes.ACC_INTERFACE);
        public int intVal;

        EnumType(final int intVal) {
            this.intVal = intVal;
        }

        public static boolean isEnum(final int modifiers) {
            return BitOp.and(modifiers, ENUM.intVal);
        }

        public static boolean isInterface(final int modifiers) {
            return BitOp.and(modifiers, INTERFACE.intVal);
        }
    }

    public static class Type extends Member {
        public EnumType type;
        public HashMap<String, LinkedList<Function>> functions = new HashMap<String, LinkedList<Function>>();
        public LinkedList<Field> fields = new LinkedList<Field>();
        public LinkedList<String> generics = new LinkedList<String>();
        public HashMap<String, LinkedList<TypeI>> genericsMap = new HashMap<String, LinkedList<TypeI>>();
        public boolean hasNonEmptyConstructor, isGlobalType;
        public Type superClass;
        public LinkedList<Type> interfaces = new LinkedList<>();
        public int interfaceClosures;

        public Type(final QualifiedName qualifiedName, final int modifiers, final EnumType type) {
            super(qualifiedName, modifiers);
            this.type = type;
        }

        public Type(final ClassNode node) {
            this(new QualifiedName(node.name.replace('/', '.')), node.access, EnumType.isEnum(node.access) ? EnumType.ENUM
                    : (EnumType.isInterface(node.access) ? EnumType.INTERFACE : EnumType.CLASS));
            final String sig = node.signature;
            if (sig != null) {
                // Parse generics
                final int openIndex = sig.indexOf('<'), closeIndex = sig.indexOf('>');
                if ((openIndex > -1) && (closeIndex > -1)) {
                    final String[] sigSections = sig.substring(openIndex + 1, closeIndex).split(";");
                    for (final String section : sigSections)
                        if (section.indexOf(':') > -1) generics.add(section.substring(0, section.indexOf(':')));
                }
            }
            final String superCls = node.superName;
            if (superCls != null) {
                final String[] split = superCls.split("/");
                superClass = TypeImporter.loadClass(superCls.replace('/', '.'), split[split.length - 1]);
            }
            for (final Object obj : node.interfaces) {
                final String interfc = obj.toString();
                final String[] split = interfc.split("/");
                interfaces.add(TypeImporter.loadClass(interfc.replace('/', '.'), split[split.length - 1]));
            }
            for (final Object obj : node.methods) {
                final MethodNode mNode = (MethodNode) obj;
                if (mNode.name.equals("<clinit>")) continue;
                addFunction(new Function(mNode, this));
            }
            for (final Object obj : node.fields) {
                final FieldNode fNode = (FieldNode) obj;
                addField(new Field(fNode, this));
            }
        }

        public boolean hasSuper(QualifiedName qualifiedName) {
            return hasSuperInterface(qualifiedName) || (hasSuperClass() && superClass.qualifiedName.equals(qualifiedName));
        }

        public void addField(final Field field) {
            fields.add(field);
        }

        public void addFunction(final Function func) {
            final String name = func.qualifiedName.shortName;
            if (functions.containsKey(name)) functions.get(name).add(func);
            else {
                final LinkedList<Function> funcs = new LinkedList<Function>();
                funcs.add(func);
                functions.put(name, funcs);
            }
            if (func.isConstructor() && !func.parameters.isEmpty()) hasNonEmptyConstructor = true;
        }

        public void addGeneric(final String typeName, final TypeI generic) {
            if (genericsMap.containsKey(typeName)) genericsMap.get(typeName).add(generic);
            else {
                final LinkedList<TypeI> list = new LinkedList<TypeI>();
                list.add(generic);
                genericsMap.put(typeName, list);
            }
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Type) return qualifiedName.shortName.equals(((Type) obj).qualifiedName.shortName);
            else if (obj instanceof String) return qualifiedName.shortName.equals(obj);
            else return false;
        }

        public Field getField(final String id) {
            for (final Field field : fields)
                if (field.qualifiedName.shortName.equals(id)) return field;
            return superClass != null ? superClass.getField(id) : null;
        }

        public TypeI getFuncType(final String id, final NodeExprs args) {
            LinkedList<TypeI> types = new LinkedList<>();
            args.exprs.forEach(expr -> types.add(expr.getExprType()));
            if (functions.containsKey(id)) for (final Function func : functions.get(id))
                if (func.parameters.getTypeIList().equals(types)) return func.returnType;
            return null;
        }

        public boolean hasSuperInterface(final QualifiedName name) {
            for (final Type type : interfaces)
                if (type.qualifiedName.equals(name)) return true;
                else if (type.hasSuperInterface(name)) return true;
            return false;
        }

        public Function getFunc(final String id, final NodeExprs args, EnumOperatorType opType) {
            LinkedList<TypeI> list = new LinkedList<>();
            args.exprs.forEach(expr -> list.add(expr.getExprType()));
            return getFunc(id, list, opType);
        }

        public Function getFunc(final String id, final List<TypeI> parameters, EnumOperatorType opType) {
            if (functions.containsKey(id)) {
                for (final Function func : functions.get(id)) {
                    if (func.opType == opType && func.parameters.getTypeIList().equals(parameters))
                        return func;
                }
            }

            Function func = null;
            for (final Type superType : interfaces)
                if ((func = superType.getFunc(id, parameters, opType)) != null && func.opType == opType) return func;
            return null;
        }

        @Override
        public String toString() {
            return "Type [type=" + type + ", qualifiedName=" + qualifiedName + "]";
        }

        public LinkedList<TypeI> getGenerics(final String string) {
            if (genericsMap.containsKey(string)) return genericsMap.get(string);
            else for (final Type type : interfaces)
                if (type.qualifiedName.shortName.equals(string)) {
                    final LinkedList<TypeI> genericList = new LinkedList<TypeI>();
                    for (int i = 0; i < type.generics.size(); i++)
                        genericList.add(TypeI.getObjectType());
                    return genericList;
                }
            return null;
        }

        public Type getSuperClass() {
            return superClass;
        }

        public boolean hasSuperClass() {
            return superClass != null;
        }

    }

    public static class Function extends Member {

        public Parameters parameters = new Parameters();
        public boolean hasDefExpr = false, isGlobal, hasImplementation;
        public TypeI returnType;
        public LinkedList<String> generics = new LinkedList<String>();
        public IExpression defExpr;
        public Type extType;
        public EnumOperatorType opType;

        public Function(final QualifiedName qualifiedName, final int modifiers, final Type enclosing, EnumOperatorType opType) {
            super(qualifiedName, modifiers);
            enclosingType = enclosing;
            this.opType = opType;
        }

        public Function(final MethodNode mNode, final Type type) {
            this(mNode.name.equals("<init>") ? type.qualifiedName.copy().add(type.qualifiedName.shortName) : type.qualifiedName.copy().add(mNode.name), mNode.access, type, null);
            final boolean isConstructor = mNode.name.equals("<init>");
            final String desc = mNode.desc;
            final int parenIndex = desc.indexOf(')');
            String params = desc.substring(1, parenIndex);
            final String returnType = desc.substring(parenIndex + 1);
            TypeI typeI = null;

            while (params.length() > 0) {
                final char ch = params.charAt(0);
                switch (ch) {
                    case '[':
                        if (typeI == null) typeI = new TypeI("", 1, true);
                        else typeI.addArrDims(1);
                        params = params.substring(1);
                        break;
                    case 'L':
                        final int colonIndex = params.indexOf(';');
                        final String name = params.substring(1, colonIndex);
                        params = params.substring(colonIndex + 1);
                        final int lastSlash = name.lastIndexOf('/');
                        final String shortName = lastSlash > -1 ? name.substring(lastSlash + 1) : name;
                        if (typeI == null) typeI = new TypeI(shortName, 0, true);
                        else typeI.shortName = shortName;
                        typeI.qualifiedName = new QualifiedName(name.replace('/', '.'));
                        parameters.add(typeI);
                        typeI = null;
                        break;
                    default:
                        final EnumPrimitive primitive = EnumPrimitive.getFromBytecodePrimitive(ch);
                        if (typeI == null) typeI = new TypeI(primitive);
                        else typeI = new TypeI(primitive, typeI.arrDims);
                        params = params.substring(1);
                        parameters.add(typeI);
                        typeI = null;
                        break;
                }
            }
            this.returnType = isConstructor ? new TypeI(type.qualifiedName.shortName, 0, false) : TypeI.fromBytecodeName(returnType);

        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Function) {
                final Function func = (Function) obj;
                return qualifiedName.equals(func.qualifiedName) && this.parameters.equals(func.parameters);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Function [parameters=" + parameters + ", returnType=" + returnType + ", qualifiedName=" + qualifiedName + ", modifiers=" + modifiers + "]";
        }

        public boolean isConstructor() {
            return qualifiedName.shortName.equals(enclosingType.qualifiedName.shortName);
        }

        public boolean isGlobal() {
            return isGlobal;
        }

        public boolean isFinal() {
            return BitOp.and(modifiers, EnumModifier.FINAL.intVal);
        }

        public boolean hasEqualSignature(Function superFunc) {
            int modsA = EnumModifier.stripOverridingModifiers(this.modifiers), modsB = EnumModifier.stripOverridingModifiers(superFunc.modifiers);
            return modsA == modsB && this.returnType.equals(superFunc.returnType);
        }

    }

    public static class Variable extends Field {

        public Variable(final String id, final TypeI type, boolean isConstant) {
            super(new QualifiedName(id), 0, type, false, false, isConstant, Semantics.currentType());
            if (Scope.inFuncScope()) {
                isLocal = true;
                localID = ++Scope.getFuncScope().locals;
                if (Scope.getFuncScope().isStatic) localID--;
            } else isLocal = false;
        }

        @Override
        public String toString() {
            return "Variable [id=" + id + ", type=" + type + "]";
        }
    }

    public static class Field extends Member {

        public TypeI type;
        public String id;
        public int localID;
        public boolean isSetProperty, isGetProperty, isConstant;

        public Field(final QualifiedName qualifiedName, final int modifiers, final TypeI type, final boolean isSetProperty, final boolean isGetProperty, final boolean isConstant, final Type enclosingType) {
            super(qualifiedName, modifiers);
            id = qualifiedName.shortName;
            this.type = type;
            this.enclosingType = enclosingType;
            this.isGetProperty = isGetProperty;
            this.isSetProperty = isSetProperty;
            this.isConstant = isConstant;
        }

        public Field(final FieldNode fNode, final Type enclosing) {
            this(new QualifiedName(fNode.name.replace('/', '.')), fNode.access, TypeI.fromBytecodeName(fNode.desc.replace(";", "")), false, false, BitOp.or(fNode.access, EnumModifier.FINAL.intVal), enclosing);
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Field) return ((Field) obj).qualifiedName.equals(qualifiedName);
            return false;
        }

    }

}
