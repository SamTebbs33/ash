package ashc.codegen;

import java.io.*;

import ashc.grammar.Node.NodeArg;
import ashc.grammar.Node.NodeBinary;
import ashc.grammar.Node.NodeBool;
import ashc.grammar.Node.NodeChar;
import ashc.grammar.Node.NodeClassBlock;
import ashc.grammar.Node.NodeClassDec;
import ashc.grammar.Node.NodeDouble;
import ashc.grammar.Node.NodeEnumDec;
import ashc.grammar.Node.NodeExprs;
import ashc.grammar.Node.NodeFile;
import ashc.grammar.Node.NodeFloat;
import ashc.grammar.Node.NodeFuncBlock;
import ashc.grammar.Node.NodeFuncCall;
import ashc.grammar.Node.NodeImport;
import ashc.grammar.Node.NodeInteger;
import ashc.grammar.Node.NodeInterfaceDec;
import ashc.grammar.Node.NodeLong;
import ashc.grammar.Node.NodeModifier;
import ashc.grammar.Node.NodePackage;
import ashc.grammar.Node.NodeQualifiedName;
import ashc.grammar.Node.NodeString;
import ashc.grammar.Node.NodeTernary;
import ashc.grammar.Node.NodeThis;
import ashc.grammar.Node.NodeType;
import ashc.grammar.Node.NodeTypeDec;
import ashc.grammar.Node.NodeTypes;
import ashc.grammar.Node.NodeUnary;
import ashc.grammar.Node.NodeVarDecExplicit;
import ashc.grammar.Node.NodeVarDecImplicit;
import ashc.grammar.Node.NodeVariable;

/**
 * Ash
 * 
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
