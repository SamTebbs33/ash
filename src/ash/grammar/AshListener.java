// Generated from Ash.g by ANTLR 4.5.2
package ash.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AshParser}.
 */
public interface AshListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AshParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(AshParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link AshParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(AshParser.FileContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#packageDec}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPackageDec(AshParser.PackageDecContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#packageDec}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPackageDec(AshParser.PackageDecContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#qualifiedName}.
	 *
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(AshParser.QualifiedNameContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#qualifiedName}.
	 *
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(AshParser.QualifiedNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#importDec}.
	 *
	 * @param ctx the parse tree
	 */
	void enterImportDec(AshParser.ImportDecContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#importDec}.
	 *
	 * @param ctx the parse tree
	 */
	void exitImportDec(AshParser.ImportDecContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#typeDec}.
	 *
	 * @param ctx the parse tree
	 */
	void enterTypeDec(AshParser.TypeDecContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#typeDec}.
	 *
	 * @param ctx the parse tree
	 */
	void exitTypeDec(AshParser.TypeDecContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#classDec}.
	 *
	 * @param ctx the parse tree
	 */
	void enterClassDec(AshParser.ClassDecContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#classDec}.
	 *
	 * @param ctx the parse tree
	 */
	void exitClassDec(AshParser.ClassDecContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#typeDecParams}.
	 *
	 * @param ctx the parse tree
	 */
	void enterTypeDecParams(AshParser.TypeDecParamsContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#typeDecParams}.
	 *
	 * @param ctx the parse tree
	 */
	void exitTypeDecParams(AshParser.TypeDecParamsContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#typeDecSupers}.
	 *
	 * @param ctx the parse tree
	 */
	void enterTypeDecSupers(AshParser.TypeDecSupersContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#typeDecSupers}.
	 *
	 * @param ctx the parse tree
	 */
	void exitTypeDecSupers(AshParser.TypeDecSupersContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#classBlock}.
	 *
	 * @param ctx the parse tree
	 */
	void enterClassBlock(AshParser.ClassBlockContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#classBlock}.
	 *
	 * @param ctx the parse tree
	 */
	void exitClassBlock(AshParser.ClassBlockContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#varDec}.
	 *
	 * @param ctx the parse tree
	 */
	void enterVarDec(AshParser.VarDecContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#varDec}.
	 *
	 * @param ctx the parse tree
	 */
	void exitVarDec(AshParser.VarDecContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#funcDec}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFuncDec(AshParser.FuncDecContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#funcDec}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFuncDec(AshParser.FuncDecContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#funcParam}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFuncParam(AshParser.FuncParamContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#funcParam}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFuncParam(AshParser.FuncParamContext ctx);

	/**
	 * Enter a parse tree produced by {@link AshParser#mods}.
	 *
	 * @param ctx the parse tree
	 */
	void enterMods(AshParser.ModsContext ctx);

	/**
	 * Exit a parse tree produced by {@link AshParser#mods}.
	 *
	 * @param ctx the parse tree
	 */
	void exitMods(AshParser.ModsContext ctx);
}