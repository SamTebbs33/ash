package ashc.semantics;

import ashc.codegen.GenNode;
import ashc.codegen.GenNode.*;
import ashc.grammar.Node.*;
import ashc.semantics.Member.Type;
import org.objectweb.asm.Opcodes;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

public class TypeI {

    private static TypeI objectType = new TypeI("Object", 0, false), voidType = new TypeI("void", 0, false), stringType = new TypeI("String", 0, false);
    public String shortName, tupleName;
    public int arrDims;
    public boolean optional;
    public LinkedList<TypeI> tupleTypes, genericTypes;
    public QualifiedName qualifiedName;

    public TypeI(final String shortName, final int arrDims, final boolean optional) {
        this.shortName = shortName;
        this.arrDims = arrDims;
        this.optional = optional;
        tupleTypes = new LinkedList<TypeI>();
        genericTypes = new LinkedList<TypeI>();
    }

    public TypeI(final EnumPrimitive primitive) {
        this(primitive.ashName, 0, false);
    }

    public TypeI(final EnumPrimitive primitive, final int arrDims) {
        this(primitive.ashName, arrDims, false);
    }

    public TypeI(final Type type) {
        this(type.qualifiedName.shortName, 0, false);
    }

    public static TypeI from(final EnumInstructionOperand retType) {
        switch (retType) {
            case ARRAY:
                return new TypeI("Object", 1, false);
            case BOOL:
                return new TypeI(EnumPrimitive.BOOL);
            case BYTE:
                return new TypeI(EnumPrimitive.BYTE);
            case CHAR:
                return new TypeI(EnumPrimitive.CHAR);
            case DOUBLE:
                return new TypeI(EnumPrimitive.DOUBLE);
            case FLOAT:
                return new TypeI(EnumPrimitive.FLOAT);
            case INT:
                return new TypeI(EnumPrimitive.INT);
            case LONG:
                return new TypeI(EnumPrimitive.LONG);
            case REFERENCE:
                return TypeI.getObjectType();
            case SHORT:
                return new TypeI(EnumPrimitive.SHORT);
            default:
                return TypeI.getVoidType();
        }
    }

    public static TypeI getVoidType() {
        return voidType;
    }

    public static TypeI getObjectType() {
        return objectType;
    }

    public static TypeI getStringType() {
        return stringType;
    }

    public static TypeI fromBytecodeName(String name) {
        TypeI type = new TypeI("", 0, true);
        if (name.equals("V")) return TypeI.getVoidType();
        else {
            type = new TypeI("", 0, true);
            while (name.charAt(0) == '[') {
                type.addArrDims(1);
                name = name.substring(1);
            }
            if (name.charAt(0) == 'L') {
                if (name.charAt(name.length() - 1) == ';')
                    name = name.substring(1, name.length() - 1); // Remove semi-colon
                else name = name.substring(1);
                final int lastSlash = name.lastIndexOf('/');
                type.shortName = lastSlash > -1 ? name.substring(lastSlash + 1) : name;
                type.qualifiedName = new QualifiedName(name.replace('/', '.'));
            } else {
                final EnumPrimitive p = EnumPrimitive.getFromBytecodePrimitive(name.charAt(0));
                type = new TypeI(p, type.arrDims);
            }
        }
        return type;
    }

    public static TypeI getPrecedentType(final TypeI type1, final TypeI type2) {
        if (type1.equals(type2)) return type1;
        final String name1 = type1.shortName, name2 = type2.shortName;

        if ((name1.equals("String") && (type1.arrDims == 0)) || (name2.equals("String") && (type2.arrDims == 0)))
            return new TypeI("String", 0, false);

        // The values in EnumPrimitive are ordered by precedence
        for (final EnumPrimitive p : EnumPrimitive.values())
            if (p.ashName.equals(name1) || p.ashName.equals(name2)) return new TypeI(p);

        return TypeI.getObjectType().setOptional(type1.optional || type2.optional);
    }

    public static TypeI getPrecedentType(final LinkedList<IExpression> exprs) {
        TypeI result = exprs.getFirst().getExprType();
        for (int i = 1; i < exprs.size(); i++)
            result = TypeI.getPrecedentType(result, exprs.get(i).getExprType());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TypeI) {
            final TypeI type = (TypeI) obj;
            return genericTypes.equals(type.genericTypes) && type.shortName.equals(shortName) && (type.arrDims == arrDims) && (type.optional == optional);
        }
        return false;
    }

    @Override
    public String toString() {
        if (isNull() || isVoid()) return shortName;
        String id = shortName;
        if (tupleName != null) id = tupleName + " : " + id;
        if (isTuple()) {
            id = "(";
            for (int i = 0; i < tupleTypes.size(); i++)
                id += tupleTypes.get(i).toString() + (i < (tupleTypes.size() - 1) ? ", " : "");
            id += ")";
        }
        return String.format("%s%s%s%s", String.join("", Collections.nCopies(arrDims, "[")), id, String.join("", Collections.nCopies(arrDims, "]")), optional ? "?" : "");
    }

    public boolean isVoid() {
        return !isTuple() ? shortName.equals("void") : false;
    }

    public boolean canBeAssignedTo(final TypeI exprType) {
        if (exprType == null) return false;
        if (equals(exprType)) return true;
        // If the expr is null, and this is optional, and it has more than 0
        // array dimensions
        if (exprType.isNull() && optional && (!EnumPrimitive.isPrimitive(shortName) || (arrDims > 0))) return true;
        // If this is a tuple and the expression is a tuple expression
        if (isTuple()) {
            if (tupleTypes.size() == exprType.tupleTypes.size()) {
                for (int i = 0; i < exprType.tupleTypes.size(); i++) {
                    final TypeI tupleType1 = tupleTypes.get(i), tupleType2 = exprType.tupleTypes.get(i);
                    if (!tupleType1.canBeAssignedTo(tupleType2))
                        return false;
                }
                return true;
            }
            return false;
        }
        if (isVoid() || exprType.isVoid()) return false;

        // Ensure this type's generics can be assigned to the other type's
        int i = 0;
        for (final TypeI generic : genericTypes)
            if (!generic.canBeAssignedTo(exprType.genericTypes.get(i++))) return false;

        // Optionals can be assigned to non-optionals, but not the other way
        // around
        if (!optional && exprType.optional) return false;

        // If they are both numeric and the array dimensions are 0
        // if (EnumPrimitive.isNumeric(shortName) && EnumPrimitive.isNumeric(exprType.shortName) && (arrDims == exprType.arrDims)) return true;
        return (exprType.arrDims == arrDims)
                && (shortName.equals(exprType.shortName) || (exprType.isNull() && !EnumPrimitive.isNumeric(shortName)) || Semantics.typeHasSuper(exprType.shortName, shortName));
    }

    public boolean isClassType() {
        return !isTuple() && !isArray() && !isPrimitive();
    }

    public boolean isNull() {
        return !isTuple() ? shortName.equals("null") : false;
    }

    public boolean isTuple() {
        return (tupleTypes != null) && (tupleTypes.size() > 0);
    }

    public boolean isArray() {
        return arrDims > 0;
    }

    public TypeI copy() {
        return new TypeI(shortName, arrDims, optional);
    }

    public TypeI setArrDims(final int i) {
        arrDims = i;
        return this;
    }

    public TypeI setOptional(final boolean b) {
        optional = b;
        return this;
    }

    public boolean isNumeric() {
        return EnumPrimitive.isNumeric(shortName) && (arrDims == 0);
    }

    public boolean isPrimitive() {
        return EnumPrimitive.isPrimitive(shortName) && !isArray();
    }

    public boolean isRange() {
        return (shortName != null) && shortName.equals("Range");
    }

    public String toBytecodeName() {
        StringBuffer name = new StringBuffer();
        if (isArray()) for (int i = 0; i < arrDims; i++)
            name.append("[");
        if (isTuple()) {
            // The tuple class name is "Tuple" followed by the number of
            // fields
            final String tupleClassName = "Tuple" + tupleTypes.size();
            if (!GenNode.generatedTupleClasses.contains(tupleClassName)) {
                final GenNodeType tupleClass = new GenNodeType(tupleClassName, tupleClassName, "Ljava/lang/Object;", null, Opcodes.ACC_PUBLIC, false);
                GenNode.addGenNodeType(tupleClass);
                final GenNodeFunction tupleConstructor = new GenNodeFunction("<init>", Opcodes.ACC_PUBLIC, "V");
                GenNode.addGenNodeFunction(tupleConstructor);
                int tupleTypeNum = 1;
                char tupleFieldName = 'a', tupleFieldType = 'A';
                tupleConstructor.stmts.add(new GenNodeThis());
                tupleConstructor.stmts.add(new GenNodeFuncCall("java/lang/Object", "<init>", "()V", false, false, false, true, 0));
                for (final TypeI tupleType : tupleTypes) {
                    final String tupleFieldNameStr = String.valueOf(tupleFieldName), tupleFieldTypeStr = String.valueOf(tupleFieldType);
                    tupleClass.generics.add(tupleFieldTypeStr);
                    tupleClass.addField(new GenNodeField(Opcodes.ACC_PUBLIC, tupleFieldNameStr, "Ljava/lang/Object;", tupleFieldTypeStr));
                    tupleConstructor.params.add(TypeI.getObjectType());
                    tupleConstructor.stmts.add(new GenNodeThis());
                    tupleConstructor.stmts.add(new GenNodeVar(tupleFieldName + "Arg", "Ljava/lang/Object;", tupleTypeNum, "T" + tupleType.toBytecodeName()
                            + ";"));
                    tupleConstructor.stmts.add(new GenNodeVarLoad(EnumInstructionOperand.REFERENCE, tupleTypeNum, 0));
                    tupleConstructor.stmts.add(new GenNodeFieldStore(tupleFieldNameStr, tupleClassName, "Ljava/lang/Object;", false, 0));
                    tupleTypeNum++;
                    tupleFieldName++;
                    tupleFieldType++;
                }
                tupleConstructor.stmts.add(new GenNodeReturn(0));
                GenNode.exitGenNodeFunction();
                GenNode.exitGenNodeType();
                GenNode.generatedTupleClasses.add(tupleClassName);
            }
            name = new StringBuffer("L" + tupleClassName + ";");
        } else if (isVoid()) name.append("V");
        else if (EnumPrimitive.isPrimitive(shortName)) name.append(EnumPrimitive.getPrimitive(shortName).bytecodeChar);
        else {
            final Optional<ashc.semantics.Member.Type> typeOpt = Semantics.getType(shortName);
            if (typeOpt.isPresent()) name.append("L" + typeOpt.get().qualifiedName.toString().replace('.', '/') + ";");
            else name.append("Ljava/lang/Object;");
        }
        return name.toString();
    }

    public EnumInstructionOperand getInstructionType() {
        if (isArray()) return EnumInstructionOperand.ARRAY;
        else if (isPrimitive()) return EnumPrimitive.getPrimitive(shortName).instructionType;
        else return EnumInstructionOperand.REFERENCE;
    }

    public IExpression getDefaultValue() {
        if (isPrimitive()) switch (EnumPrimitive.getPrimitive(shortName)) {
            case BOOL:
                return new NodeBool(0, 0, false);
            case LONG:
                return new NodeLong(0, 0, 0);
            case DOUBLE:
                return new NodeDouble(0, 0, 0d);
            case FLOAT:
                return new NodeFloat(0, 0, 0f);
            default:
                return new NodeInteger(0, 0, 0);
        }
        else return new NodeNull();
    }

    public boolean isValidArrayAccessor() {
        return isNumeric() && EnumPrimitive.getPrimitive(shortName).validForArrayIndex;
    }

    public TypeI setTupleName(final String valueOf) {
        tupleName = valueOf;
        return this;
    }

    public TypeI addArrDims(final int i) {
        arrDims += i;
        return this;
    }

    public TypeI setQualifiedName(final QualifiedName qualifiedName2) {
        qualifiedName = qualifiedName2;
        return this;
    }

    public EnumPrimitive getPrimitive() {
        return EnumPrimitive.getPrimitive(shortName);
    }

    public static class FunctionTypeI extends TypeI {

        public static int numClosures = 0;
        public TypeI type;
        public LinkedList<TypeI> args;
        public boolean hasDefExpr;
        public IExpression defExpr;
        public int closureID = ++numClosures;

        public FunctionTypeI(TypeI type, LinkedList<TypeI> args, IExpression defExpr) {
            super("func", 0, false);
            this.type = type;
            this.hasDefExpr = defExpr != null;
            this.defExpr = defExpr;
            this.args = args;
        }

        @Override
        public boolean canBeAssignedTo(TypeI type) {
            if (type instanceof FunctionTypeI) {
                FunctionTypeI funcType = (FunctionTypeI) type;
                if (type.canBeAssignedTo(funcType.type)) {
                    int i = 0;
                    if (funcType.args.size() != args.size()) return false;
                    for (TypeI t : funcType.args) if (!args.get(i++).canBeAssignedTo(t)) return false;
                    return true;
                }
            }
            return false;
        }

        public String toClassName() {
            return "$Closure" + closureID;
        }

        @Override
        public String toBytecodeName() {
            return "LClosure" + closureID + ";";
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            int c = 0;
            for (TypeI argType : args) sb.append(argType.toString() + (c < args.size() - 1 ? ", " : ""));
            return "(" + sb.toString() + ") -> " + type.toString();
        }
    }
}
