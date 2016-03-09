// Generated from Ash.g by ANTLR 4.5.2
package ash.grammar;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AshParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface AshVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link AshParser#file}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFile(AshParser.FileContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#packageDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPackageDec(AshParser.PackageDecContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#qualifiedName}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQualifiedName(AshParser.QualifiedNameContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#importDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitImportDec(AshParser.ImportDecContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#typeDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTypeDec(AshParser.TypeDecContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#classDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitClassDec(AshParser.ClassDecContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#typeDecParams}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTypeDecParams(AshParser.TypeDecParamsContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#typeDecSupers}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTypeDecSupers(AshParser.TypeDecSupersContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#classBlock}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitClassBlock(AshParser.ClassBlockContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#varDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitVarDec(AshParser.VarDecContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#funcDec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFuncDec(AshParser.FuncDecContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#funcParam}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFuncParam(AshParser.FuncParamContext ctx);

    /**
     * Visit a parse tree produced by {@link AshParser#mods}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMods(AshParser.ModsContext ctx);
}