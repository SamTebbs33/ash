package ashc.codegen;

import java.io.*;

import ashc.grammar.Node.*;

/**
 * Ash
 * @author samtebbs, 20:08:22 - 24 May 2015
 */
public interface CodeGenerator {
    
    public void start() throws FileNotFoundException;
    
    public void end();

    public void generateArg(NodeArg arg);

    public void generateBinary(NodeBinary expr);

    public void generateBool(NodeBool expr);

    public void generateChar(NodeChar expr);

    public void generateClassBlock(NodeClassBlock block);

    public void generateClassDec(NodeClassDec dec);

    public void generateDouble(NodeDouble expr);

    public void generateEnumDec(NodeEnumDec dec);

    public void generateExprs(NodeExprs exprs);

    public void generateFile(NodeFile file);

    public void generateFloat(NodeFloat expr);

    public void generateFuncBlock(NodeFuncBlock block);

    public void generateFuncCall(NodeFuncCall call);

    public void generateDec();

    public void generateImport(NodeImport stmt);

    public void generateInteger(NodeInteger expr);

    public void generateInterfaceDec(NodeInterfaceDec dec);

    public void generateLong(NodeLong expr);

    public void generateModifier(NodeModifier mod);

    public void generatePackage(NodePackage pkg);

    public void generateQualifiedName(NodeQualifiedName name);

    public void generateString(NodeString str);

    public void generateTernary(NodeTernary expr);

    public void generateThis(NodeThis expr);

    public void generateType(NodeType type);

    public void generateTypeDec(NodeTypeDec dec);

    public void generateTypes(NodeTypes types);

    public void generateUnary(NodeUnary expr);

    public void generateVarDecExplicit(NodeVarDecExplicit dec);

    public void generateVarDecImplicit(NodeVarDecImplicit dec);

    public void generateVariable(NodeVariable var);

}
