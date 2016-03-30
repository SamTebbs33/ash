// Generated from Ash.g by ANTLR 4.5.3
package ash.grammar.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AshParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AshVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AshParser#typeDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDef(AshParser.TypeDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(AshParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#packageDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageDec(AshParser.PackageDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(AshParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#importDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDec(AshParser.ImportDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#aliasedImport}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAliasedImport(AshParser.AliasedImportContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#multiImport}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiImport(AshParser.MultiImportContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(AshParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#classDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDec(AshParser.ClassDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(AshParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#typeDecSupers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDecSupers(AshParser.TypeDecSupersContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#classBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBlock(AshParser.ClassBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#varDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDec(AshParser.VarDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#funcDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDec(AshParser.FuncDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#funcBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncBlock(AshParser.FuncBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#bracedBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracedBlock(AshParser.BracedBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#funcParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncParam(AshParser.FuncParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#mods}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMods(AshParser.ModsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(AshParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(AshParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(AshParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#elseIfStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseIfStmt(AshParser.ElseIfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#elseStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseStmt(AshParser.ElseStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(AshParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#suffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuffix(AshParser.SuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(AshParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#varAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarAssignment(AshParser.VarAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link AshParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(AshParser.FuncCallContext ctx);
}