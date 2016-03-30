package ash.grammar;

import ash.grammar.antlr.AshParser;
import ash.grammar.antlr.AshVisitor;
import ash.grammar.node.*;
import ash.grammar.node.NodeString;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class AshParserVisitor implements AshVisitor {

    @Override
    public Node visit(ParseTree parseTree) {
        return null;
    }

    @Override
    public Node visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public String visitTerminal(TerminalNode terminalNode) {
        return terminalNode.getText();
    }

    @Override
    public Node visitErrorNode(ErrorNode errorNode) {
        return null;
    }

    public String visitToken(Token token) {
        return token.getText();
    }

    public <T extends ParseTree, R> List<R> visit(List<T> contexts, Function<T, R> converter) {
        return contexts.stream().map(converter).collect(Collectors.toList());
    }

    public <T extends ParseTree, R> Optional<R> visitOrNull(T ctx, Function<T, R> func) {
        return ctx == null ? Optional.empty() : Optional.of(func.apply(ctx));
    }

    public <T extends ParseTree, R> List<R> visitOrEmpty(T ctx, Function<T, List<R>> func) {
        return ctx == null ? new LinkedList<>() : func.apply(ctx);
    }

    public <T extends ParseTree, R> List<R> visitOrEmpty(List<T> ctx, Function<T, R> func) {
        return ctx == null ? new LinkedList<>() : visit(ctx, func);
    }

    public static <T> T get(ParserRuleContext[] contexts, Function<ParserRuleContext, T>... functions) {
        for(int i = 0; i < contexts.length; i++) if(contexts[i] != null) return functions[i].apply(contexts[i]);
        return null;
    }

    public static <T> T[] array(T... ts) {
        return ts;
    }

    /**
     * Visit a parse tree produced by {@link AshParser#file}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeFile visitFile(AshParser.FileContext ctx) {
        return new NodeFile(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#packageDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodePackage visitPackageDec(AshParser.PackageDecContext ctx) {
        return new NodePackage(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#qualifiedName}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeQualifiedName visitQualifiedName(AshParser.QualifiedNameContext ctx) {
        return new NodeQualifiedName(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#importDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeImport visitImportDec(AshParser.ImportDecContext ctx) {
        return new NodeImport(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#type}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeType visitType(AshParser.TypeContext ctx) {
        return new NodeType(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#classDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeClassDec visitClassDec(AshParser.ClassDecContext ctx) {
        return new NodeClassDec(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#typeDecParams}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public List<NodeFuncParam> visitTypeDecParams(AshParser.TypeDecParamsContext ctx) {
        return visit(ctx.funcParam(), this::visitFuncParam);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#typeDecSupers}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public List<NodeQualifiedName> visitTypeDecSupers(AshParser.TypeDecSupersContext ctx) {
        return visit(ctx.qualifiedName(), this::visitQualifiedName);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#classBlock}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeClassBlock visitClassBlock(AshParser.ClassBlockContext ctx) {
        return new NodeClassBlock(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#varDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeVarDec visitVarDec(AshParser.VarDecContext ctx) {
        return new NodeVarDec(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#funcDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeFuncDec visitFuncDec(AshParser.FuncDecContext ctx) {
        return new NodeFuncDec(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#funcBlock}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeFuncBlock visitFuncBlock(AshParser.FuncBlockContext ctx) {
        return new NodeFuncBlock(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#bracedBlock}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeBlock visitBracedBlock(AshParser.BracedBlockContext ctx) {
        return new NodeBlock(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#funcParam}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeFuncParam visitFuncParam(AshParser.FuncParamContext ctx) {
        return new NodeFuncParam(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#mods}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public List<String> visitMods(AshParser.ModsContext ctx) {
        return visit(ctx.MODIFIER(), this::visitTerminal);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Expression visitExpr(AshParser.ExprContext ctx) {

        if(ctx.BIN_INT() != null) return new NodeInt(ctx, ctx.BIN_INT().getText().substring(2), 2, this);
        else if(ctx.HEX_INT() != null) return new NodeInt(ctx, ctx.HEX_INT().getText().substring(2), 16, this);
        else if(ctx.OCT_INT() != null) return new NodeInt(ctx, ctx.OCT_INT().getText().substring(2), 8, this);
        else if(ctx.INT() != null) return new NodeInt(ctx, ctx.INT().getText(), 10, this);
        else if(ctx.STRING() != null) {
            String text = ctx.STRING().getText();
            return new NodeString(ctx, text.substring(1, text.length() - 1), this);
        } else if(ctx.FLOAT() != null) return new NodeFloat(ctx, Float.parseFloat(ctx.FLOAT().getText()), this);
        else if(ctx.DOUBLE() != null) return new NodeDouble(ctx, Double.parseDouble(ctx.DOUBLE().getText()), this);
        else if(ctx.CHAR() != null) return new NodeChar(ctx, ctx.CHAR().getText(), this);
        else if(ctx.BOOL() != null) return new NodeBool(ctx, ctx.BOOL().getText().equals("true"), this);
        else if(ctx.bracketed != null) return visitExpr(ctx.bracketed);
        else if(ctx.binaryOp != null) return new NodeBinary(ctx, this);
        else if(ctx.prefixOp != null) return new NodeUnary(ctx, this);
        else if(ctx.funcCall() != null) return visitFuncCall(ctx.funcCall());
        else if(ctx.var() != null) return visitVar(ctx.var());
        else return new NodeTernary(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public Statement visitStmt(AshParser.StmtContext ctx) {
        if(ctx.varAssignment() != null) return visitVarAssignment(ctx.varAssignment());
        else if(ctx.funcCall() != null) return visitFuncCall(ctx.funcCall());
        else if(ctx.ifStmt() != null) return visitIfStmt(ctx.ifStmt());
        else if(ctx.returnStmt() != null) return visitReturnStmt(ctx.returnStmt());
        else if(ctx.bracedBlock() != null) return visitBracedBlock(ctx.bracedBlock());
        return null; // TODO
    }

    /**
     * Visit a parse tree produced by {@link AshParser#ifStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeIf visitIfStmt(AshParser.IfStmtContext ctx) {
        return new NodeIf(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#elseIfStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeIf visitElseIfStmt(AshParser.ElseIfStmtContext ctx) {
        return new NodeIf(ctx.ifStmt(), this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#elseStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeElse visitElseStmt(AshParser.ElseStmtContext ctx) {
        return new NodeElse(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#returnStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeReturn visitReturnStmt(AshParser.ReturnStmtContext ctx) {
        return new NodeReturn(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#suffix}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodePrefix visitSuffix(AshParser.SuffixContext ctx) {
        return get(array(ctx.funcCall(), ctx.var()), c -> visitFuncCall((AshParser.FuncCallContext) c), c -> visitVar((AshParser.VarContext) c));
    }

    /**
     * Visit a parse tree produced by {@link AshParser#var}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeVariable visitVar(AshParser.VarContext ctx) {
        return new NodeVariable(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#varAssignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeVarAssignment visitVarAssignment(AshParser.VarAssignmentContext ctx) {
        return new NodeVarAssignment(ctx, this);
    }

    /**
     * Visit a parse tree produced by {@link AshParser#funcCall}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeFuncCall visitFuncCall(AshParser.FuncCallContext ctx) {
        return new NodeFuncCall(ctx, this);
    }

}
