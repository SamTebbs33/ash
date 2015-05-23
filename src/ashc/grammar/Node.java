package ashc.grammar;

import java.util.LinkedList;

import ashc.grammar.Lexer.Token;

/**
 * Ash
 *
 * @author samtebbs, 22:26:10 - 20 May 2015
 */
public abstract class Node {

    public int line, column;

    public Node(final int line, final int column) {
	this.line = line;
	this.column = column;
    }

    public Node() {

    }

    public static interface IFuncStmt {

    }

    public static interface IExpression {

    }

    public static class NodeFile extends Node {

	public NodeFile() {
	    super();
	}

    }

    public static class NodePackage extends Node {

	NodeQualifiedName qualifiedName;

	public NodePackage(final int line, final int column, final NodeQualifiedName qualifiedName) {
	    super(line, column);
	    this.qualifiedName = qualifiedName;
	}

    }

    public static class NodeImport extends Node {

	NodeQualifiedName qualifiedName;

	public NodeImport(final int line, final int column, final NodeQualifiedName qualifiedName) {
	    super(line, column);
	    this.qualifiedName = qualifiedName;
	}

    }

    public static class NodeTypeDec extends Node {

	public LinkedList<NodeModifier> mods;

	public NodeTypeDec(final int line, final int column) {
	    super(line, column);
	}

	public NodeTypeDec(final int line, final int column, final LinkedList<NodeModifier> mods) {
	    super(line, column);
	    this.mods = mods;
	}

    }

    public static class NodeModifier extends Node {
	public String mod;

	public NodeModifier(final int line, final int column, final String mod) {
	    this.mod = mod;
	}
    }

    public static class NodeClassDec extends NodeTypeDec {

	public NodeClassDec(final int line, final int column) {
	    super(line, column);
	}

	public NodeClassDec(final int line, final int column, final LinkedList<NodeModifier> mods, final Token id, final NodeArgs args, final NodeTypes types, final NodeClassBlock block) {
	    super(line, column, mods);
	}

    }

    public static class NodeEnumDec extends NodeTypeDec {

	public NodeEnumDec(final int line, final int column) {
	    super(line, column);
	}

    }

    public static class NodeInterfaceDec extends NodeTypeDec {

	public NodeInterfaceDec(final int line, final int column) {
	    super(line, column);
	}

    }

    public static class NodeArg extends Node {
	public String id;
	public NodeType type;

	public NodeArg(final int line, final int column, final String id, final NodeType type) {
	    super(line, column);
	    this.id = id;
	    this.type = type;
	}
    }

    public static class NodeArgs extends Node {
	LinkedList<NodeArg> args = new LinkedList<NodeArg>();

	public void add(final NodeArg arg) {
	    args.add(arg);
	}
    }

    public static class NodeTypes extends Node {
	LinkedList<NodeType> types = new LinkedList<Node.NodeType>();

	public void add(final NodeType type) {
	    types.add(type);
	}
    }

    public static class NodeClassBlock extends Node {
	public LinkedList<NodeFuncDec> funcDecs = new LinkedList<NodeFuncDec>();
	public LinkedList<NodeVarDec> varDecs = new LinkedList<NodeVarDec>();

	public void add(final NodeVarDec parseVarDec) {
	    varDecs.add(parseVarDec);
	}

	public void add(final NodeFuncDec funcDec) {
	    funcDecs.add(funcDec);
	}

    }

    public static class NodeType extends Node {

	String id;
	public int arrDims;

	public NodeType(final String data) {
	    id = data;
	}

    }

    public static class NodeQualifiedName extends Node {

	LinkedList<String> paths = new LinkedList<String>();

	public NodeQualifiedName(final int line, final int column) {
	    super(line, column);
	}

	public void add(final String data) {
	    paths.add(data);
	}

    }

    public static class NodeFuncDec extends Node {

	public LinkedList<NodeModifier> mods;
	public String id;
	public NodeArgs args;
	public NodeType type, throwsType;
	public NodeFuncBlock block;

	public NodeFuncDec(final int line, final int column, final LinkedList<NodeModifier> mods, final String id, final NodeArgs args, final NodeType type, final NodeType throwsType, final NodeFuncBlock block) {
	    super(line, column);
	    this.mods = mods;
	    this.id = id;
	    this.args = args;
	    this.type = type;
	    this.throwsType = throwsType;
	    this.block = block;
	}

    }

    public static class NodeVarDec extends Node implements IFuncStmt {

    }

    public static class NodeFuncBlock extends Node {

	LinkedList<IFuncStmt> stmts = new LinkedList<IFuncStmt>();

	public void add(final IFuncStmt funcStmt) {
	    stmts.add(funcStmt);
	}

    }

    public static class NodePrefix extends Node implements IFuncStmt, IExpression {

	public NodePrefix(final int line, final int column) {
	    super(line, column);
	}

    }

    public static class NodeExprs extends Node {
	public LinkedList<IExpression> exprs = new LinkedList<IExpression>();

	public void add(final IExpression expr) {
	    exprs.add(expr);
	}
    }

    public static class NodeFuncCall extends NodePrefix {
	public String id;
	public NodeExprs args;
	public NodePrefix prefix;

	public NodeFuncCall(final int line, final int column, final String id, final NodeExprs args, final NodePrefix prefix) {
	    super(line, column);
	    this.id = id;
	    this.args = args;
	    this.prefix = prefix;
	}

	@Override
	public String toString() {
	    return "NodeFuncCall [id=" + id + ", args=" + args + ", prefix=" + prefix + "]";
	}

    }

    public static class NodeVariable extends NodePrefix {

	public String id;
	public NodePrefix prefix;

	public NodeVariable(final int line, final int column, final String id, final NodePrefix prefix) {
	    super(line, column);
	    this.id = id;
	    this.prefix = prefix;
	}

	@Override
	public String toString() {
	    return "NodeVariable [id=" + id + ", prefix=" + prefix + "]";
	}

    }

    public static class NodeInteger extends Node implements IExpression {
	public int val;

	public NodeInteger(final int val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeInteger [val=" + val + "]";
	}

    }

    public static class NodeFloat extends Node implements IExpression {
	public float val;

	public NodeFloat(final float val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeFloat [val=" + val + "]";
	}

    }

    public static class NodeDouble extends Node implements IExpression {
	public double val;

	public NodeDouble(final double val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeDouble [val=" + val + "]";
	}

    }

    public static class NodeString extends Node implements IExpression {
	public String val;

	public NodeString(final String val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeString [val=" + val + "]";
	}

    }

    public static class NodeBool extends Node implements IExpression {
	public boolean val;

	public NodeBool(final boolean val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeBool [val=" + val + "]";
	}

    }

    public static class NodeChar extends Node implements IExpression {
	public char val;

	public NodeChar(final char val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "NodeChar [val=" + val + "]";
	}

    }

    public static class NodeTernary extends Node implements IExpression {
	public IExpression expr, exprTrue, exprFalse;

	public NodeTernary(final IExpression expr, final IExpression exprTrue, final IExpression exprFalse) {
	    this.expr = expr;
	    this.exprTrue = exprTrue;
	    this.exprFalse = exprFalse;
	}

	@Override
	public String toString() {
	    return "NodeTernary [expr=" + expr + ", exprTrue=" + exprTrue + ", exprFalse=" + exprFalse + "]";
	}

    }

    public static class NodeBinary extends Node implements IExpression {
	public IExpression expr1, expr2;
	public String operator;

	public NodeBinary(final IExpression expr1, final String operator, final IExpression expr2) {
	    this.expr1 = expr1;
	    this.expr2 = expr2;
	    this.operator = operator;
	}

	@Override
	public String toString() {
	    return "NodeBinary [expr1=" + expr1 + ", expr2=" + expr2 + ", operator=" + operator + "]";
	}
    }

    public static class NodeUnary extends Node implements IExpression {
	public IExpression expr;
	public String operator;
	public boolean prefix;

	public NodeUnary(final int line, final int column, final IExpression expr, final String operator, final boolean prefix) {
	    super(line, column);
	    this.expr = expr;
	    this.operator = operator;
	    this.prefix = prefix;
	}

	@Override
	public String toString() {
	    return "NodeUnary [expr=" + expr + ", operator=" + operator + ", prefix=" + prefix + "]";
	}

    }
    
    public static class NodeThis extends Node implements IExpression {
	public NodeThis(int line, int column) {
	    super(line, column);
	}
    }

}
