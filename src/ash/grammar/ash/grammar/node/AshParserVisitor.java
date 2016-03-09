package ash.grammar.ash.grammar.node;

import ash.grammar.AshParser;
import ash.grammar.AshVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
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
     * Visit a parse tree produced by {@link AshParser#typeDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public NodeTypeDec visitTypeDec(AshParser.TypeDecContext ctx) {
        return new NodeTypeDec(ctx, this);
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

    public <T, R> List<R> visit(List<T> contexts, Function<T, R> func) {
        return contexts.stream().map(func).collect(Collectors.toList());
    }
}
