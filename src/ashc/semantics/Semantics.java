package ashc.semantics;

import ashc.grammar.EnumModifier;
import ashc.grammar.Node;
import ashc.grammar.Node.IExpression;
import ashc.grammar.Node.NodeExprs;
import ashc.grammar.Node.NodeVariable;
import ashc.grammar.OperatorDef;
import ashc.grammar.OperatorDef.EnumOperatorType;
import ashc.grammar.OperatorDef.OperatorDefNative;
import ashc.grammar.OperatorDef.OperatorDefNative.NativeOpInfo;
import ashc.load.TypeImporter;
import ashc.semantics.Member.*;
import ashc.util.BitOp;
import ashc.util.Tuple;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Stack;

import static ashc.error.AshError.EnumError.*;
import static ashc.error.AshError.semanticError;
import static ashc.error.AshError.semanticWarning;

/**
 * Ash
 *
 * @author samtebbs, 15:09:18 - 23 May 2015
 */
public class Semantics {

    public static HashMap<QualifiedName, Type> types = new HashMap<QualifiedName, Type>();
    public static HashMap<String, QualifiedName> typeNameMap = new HashMap<String, QualifiedName>();
    public static HashMap<String, Type> aliases = new HashMap<String, Type>();
    public static Stack<Type> typeStack = new Stack<Type>();
    public static LinkedList<Type> globalTypes = new LinkedList<>();
    public static boolean inGlobal = false;

    public static int getNumGenericsForType(final String typeID) {
        if (EnumPrimitive.isPrimitive(typeID)) return 0;
        final Optional<Type> typeOpt = getType(typeID);
        return typeOpt.isPresent() ? typeOpt.get().generics.size() : 0;
    }

    public static boolean typeExists(final String typeName) {
        if (typeStack.size() > 0) for (final String generic : typeStack.peek().generics)
            if (generic.equals(typeName)) return true;
        return typeName.equals("void") || EnumPrimitive.isPrimitive(typeName) || typeNameMap.containsKey(typeName) || aliases.containsKey(typeName);
    }

    public static boolean typeHasSuper(final String type, final String superType) {
        final Optional<Type> t = getType(type), superT = getType(superType);
        if (t.isPresent() && superT.isPresent()) return t.get().hasSuper(superT.get().qualifiedName);
        return false;
    }

    public static void addType(final Type type, final boolean enterType) {
        types.put(type.qualifiedName, type);
        if (enterType) enterType(type);
        bindName(type.qualifiedName);
    }

    public static void exitType() {
        typeStack.pop();
        Scope.getNamespace().pop();
        inGlobal = typeStack.isEmpty() ? false : typeStack.peek().isGlobalType;
    }

    public static void bindName(final String shortName, final QualifiedName qualifiedName) {
        typeNameMap.put(shortName, qualifiedName);
    }

    public static void bindName(final QualifiedName qualifiedName) {
        bindName(qualifiedName.shortName, qualifiedName);
    }

    public static boolean bindingExists(final String shortName) {
        return typeNameMap.containsKey(shortName);
    }

    public static Optional<Type> getType(final String id, final QualifiedName name) {
        final Optional<Type> type = getType(id);
        if (type.isPresent()) return type;
        if (name != null) TypeImporter.loadClass(name.toString(), name.shortName);
        return getType(id);
    }

    public static Optional<Type> getType(final String id) {
        return typeNameMap.containsKey(id) ? Optional.of(types.get(typeNameMap.get(id))) : aliases.containsKey(id) ? Optional.of(aliases.get(id))
                : Optional.empty();
    }

    public static boolean funcExists(final Function func) {
        final Optional<Type> type = getType(func.qualifiedName.shortName);
        // Check if it is a constructor for an existing type
        if (type.isPresent())
            return type.get().getFunc(func.qualifiedName.shortName, func.parameters.getTypeIList(), func.opType) != null;
        return typeStack.peek().getFunc(func.qualifiedName.shortName, func.parameters.getTypeIList(), func.opType) != null;
    }

    public static void addFunc(final Function func) {
        typeStack.peek().addFunction(func);
    }

    public static boolean fieldExists(final Field field) {
        for (final Field f : typeStack.peek().fields)
            if (f.equals(field)) return true;
        return false;
    }

    public static void addField(final Field field) {
        typeStack.peek().fields.add(field);
    }

    public static Field getVar(final String id, final TypeI type) {
        if (type.isTuple()) {
            for (final TypeI tupleType : type.tupleTypes)
                if ((tupleType.tupleName != null) && tupleType.tupleName.equals(id))
                    return new Variable(id, tupleType, false);
        } else {
            final Optional<Type> t = getType(type.shortName);
            if (t.isPresent()) return t.get().getField(id);
        }
        return null;
    }

    public static Variable getVar(final String id, final Scope scope) {
        if (scope != null) {
            for (final Variable var : scope.vars)
                if (var.id.equals(id)) return var;
            if (scope.parent != null) return getVar(id, scope.parent);
        }
        return null;
    }

    public static Field getVar(final String id) {
        // Look in scope
        Variable var = null;
        if ((var = getVar(id, Scope.getScope())) != null) return var;
        // Else, look in the current type
        return getVar(id, new TypeI(typeStack.peek().qualifiedName.shortName, 0, false));
    }

    public static TypeI getFuncType(final String id, final TypeI type, final NodeExprs args) {
        if (type.isArray() && id.equals("toString()")) return new TypeI("String", 0, false);
        else {
            final Optional<Type> t = getType(type.shortName);
            if (t.isPresent()) return t.get().getFuncType(id, args);
        }
        return null;
    }

    public static TypeI getFuncType(final String id, final NodeExprs args) {
        return getFuncType(id, new TypeI(typeStack.peek().qualifiedName.shortName, 0, false), args);
    }

    public static Function getFunc(final String id, final TypeI type, final NodeExprs args, EnumOperatorType opType) {
        final Optional<Type> t = type.qualifiedName != null ? getType(type.shortName, type.qualifiedName) : getType(type.shortName);
        if (t.isPresent()) return t.get().getFunc(id, args, opType);
        return null;
    }

    public static Function getFunc(final String id, final TypeI type, Parameters args, EnumOperatorType opType) {
        final Optional<Type> t = type.qualifiedName != null ? getType(type.shortName, type.qualifiedName) : getType(type.shortName);
        if (t.isPresent()) return t.get().getFunc(id, args.getTypeIList(), opType);
        return null;
    }

    public static Function getFunc(String id, final NodeExprs args, EnumOperatorType opType) {
        final Optional<Type> type = getType(id);
        // Check if it is a constructor for an existing type
        if (type.isPresent()) {
            id = type.get().qualifiedName.shortName;
            return type.get().getFunc(id, args, opType);
        }
        for (final Type gType : globalTypes) {
            final Function func = gType.getFunc(id, args, opType);
            if (func != null) return func;
        }
        return getFunc(id, new TypeI(typeStack.peek().qualifiedName.shortName, 0, false), args, opType);
    }

    public static Function getFunc(String id, Parameters args, EnumOperatorType opType) {
        final Optional<Type> type = getType(id);
        // Check if it is a constructor for an existing type
        if (type.isPresent()) {
            id = type.get().qualifiedName.shortName;
            return type.get().getFunc(id, args.getTypeIList(), opType);
        }
        for (final Type gType : globalTypes) {
            final Function func = gType.getFunc(id, args.getTypeIList(), opType);
            if (func != null) return func;
        }
        return getFunc(id, new TypeI(typeStack.peek().qualifiedName.shortName, 0, false), args, opType);
    }

    public static void enterType(final Type type) {
        inGlobal = type.isGlobalType;
        typeStack.push(type);
    }

    public static boolean varExists(final String id) {
        final Scope scope = Scope.getScope();
        if ((scope != null) && scope.hasVar(id)) return true;
        for (final Field field : typeStack.peek().fields)
            if (field.qualifiedName.shortName.equals(id)) return true;
        return false;
    }

    public static void addVar(final Variable variable) {
        if (Scope.inScope()) Scope.getScope().vars.add(variable);
    }

    public static Type currentType() {
        return typeStack.peek();
    }

    public static TypeI getGenericType(final String shortName, final TypeI type) {
        final Optional<Type> typeOpt = getType(type.shortName);
        if (typeOpt.isPresent()) {
            final Type enclosingType = typeOpt.get();
            final int i = 0;
            for (final String generic : enclosingType.generics)
                if (generic.equals(shortName)) if (i < type.genericTypes.size()) return type.genericTypes.get(i);
                else return new TypeI("Object", 0, false);
        }
        return null;
    }

    public static TypeI filterNullType(final TypeI exprType) {
        return exprType.isNull() ? TypeI.getObjectType().copy().setOptional(true) : exprType;
    }

    public static TypeI checkUnwrappedOptional(TypeI exprType, final Node node, final IExpression expr) {
        if (expr instanceof NodeVariable) {
            final Field field = ((NodeVariable) expr).var;
            if (field != null) if (!Scope.getScope().hasNullCheck(field))
                semanticWarning(node.line, node.column, UNCHECKED_UNWRAP, field.qualifiedName.shortName);
        }
        if (exprType != null) {
            exprType = exprType.copy();
            if (!exprType.optional) semanticError(node, node.line, node.column, UNWRAPPED_VALUE_NOT_OPTIONAL, exprType);
            exprType.optional = false;
        } else node.errored = true;
        return exprType;
    }

    public static void addAlias(final String alias, final Type type) {
        aliases.put(alias, type);
    }

    public static Tuple<TypeI, Function> getOperationType(final TypeI type1, final TypeI type2, final OperatorDef operator) {
        if (operator instanceof OperatorDefNative) {
            final OperatorDefNative op = (OperatorDefNative) operator;
            for (final NativeOpInfo info : op.opInfo)
                if ((info.type1 == type1.getInstructionType()) && (info.type2 == type2.getInstructionType()))
                    return new Tuple<TypeI, Function>(TypeI.from(info.retType), null);
        }

        if (type1.isArray() || type1.isTuple() || type1.isVoid() || type1.isNull()) return null;
        else if (type2.isArray() || type2.isTuple() || type2.isVoid() || type2.isNull()) return null;

        // Check for operator overloads, start with type 1
        Parameters parameters = new Parameters();
        Type type;

        // Check if there is a global overload for the types.
        // Global overloads are ordered, which means that the operands must appear in the same order as the overloading func's params
        parameters.add(type1);
        parameters.add(type2);
        Function func = Semantics.getFunc(operator.id, parameters, operator.type);
        if (func != null) return new Tuple<TypeI, Function>(func.returnType, func);

        if (!type1.isPrimitive()) {
            parameters.clear();
            parameters.add(type2);
            type = Semantics.getType(type1.shortName).get();
            func = type.getFunc(operator.id, parameters.getTypeIList(), operator.type);
            if (func != null) return new Tuple<TypeI, Function>(func.returnType, func);
        }
        if (!type2.isPrimitive()) {
            // Check type 2 for operator overloads
            parameters.clear();
            parameters.add(type1);
            type = Semantics.getType(type2.shortName).get();
            func = type.getFunc(operator.id, parameters.getTypeIList(), operator.type);
            if (func != null) return new Tuple<TypeI, Function>(func.returnType, func);
        }
        return null;
    }

    public static Operation getOperationType(final TypeI type, final OperatorDef operator) {
        if (operator instanceof OperatorDefNative) return new Operation(null, type);

        // Search for an operator overload declared in the type
        if (!type.isPrimitive()) {
            Function func = getFunc(operator.id, type, new Parameters(), operator.type);
            if (func != null) return new Operation(func, func.returnType);
        }

        // Search for a global operator overload
        Parameters params = new Parameters();
        params.add(type);
        Function func = getFunc(operator.id, params, operator.type);
        if (func != null) return new Operation(func, func.returnType);
        else return null;
    }

    public static void enterDefFile(final String defFileName) {
        final Type type = new Type(new QualifiedName(defFileName), EnumModifier.PUBLIC.intVal, EnumType.CLASS);
        type.isGlobalType = true;
        globalTypes.add(type);
        enterType(type);
    }

    public static void exitDefFile() {
        exitType();
    }

    public static Type getGlobalType() {
        return currentType().isGlobalType ? currentType() : null;
    }

    public static void checkOverride(Function func, Function superFunc, Node node) {
        boolean hasOverrideMod = BitOp.and(func.modifiers, EnumModifier.OVERRIDE.intVal);
        if (superFunc != null) {
            if (superFunc.isPrivate()) semanticError(node, node.line, node.column, CANNOT_OVERRIDE_PRIVATE_FUNC);
            if (superFunc.isFinal()) semanticError(node, node.line, node.column, CANNOT_OVERRIDE_FINAL_FUNC);
            if (!hasOverrideMod && !func.isConstructor())
                semanticError(node, node.line, node.column, OVERRIDE_KEYWORD_REQUIRED);
            if (!func.hasEqualSignature(superFunc))
                semanticError(node, node.line, node.column, FUNC_SIGNATURES_DO_NOT_MATCH);
        } else if (hasOverrideMod) semanticError(node, node.line, node.column, OVERRIDEN_FUNC_DOES_NOT_EXIST);
    }

    public static class Operation {
        public Function overloadFunc;
        public TypeI type;

        public Operation(final Function overloadFunc, final TypeI type) {
            this.overloadFunc = overloadFunc;
            this.type = type;
        }
    }

}
