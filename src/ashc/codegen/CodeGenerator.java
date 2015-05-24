package ashc.codegen;

/**
 * Ash
 * @author samtebbs, 20:08:22 - 24 May 2015
 */
public interface CodeGenerator {
    
    public void generateArg();
    public void generateBinary();
    public void generateBool();
    public void generateChar();
    public void generateClassBlock();
    public void generateClassDec();
    public void generateDouble();
    public void generateEnumDec();
    public void generateExprs();
    public void generateFile();
    public void generateFloat();
    public void generateFuncBlock();
    public void generateFuncCall();
    public void generateDec();
    public void generateImport();
    public void generateInteger();
    public void generateInterfaceDec();
    public void generateLong();
    public void generateModifier();
    public void generatePackage();
    public void generateQualifiedName();
    public void generateString();
    public void generateTernary();
    public void generateThis();
    public void generateType();
    public void generateTypeDec();
    public void generateTypes();
    public void generateUnary();
    public void generateVarDecExplicit();
    public void generateVarDecExplicitAssign();
    public void generateVarDecImplicit();
    public void generateVariable();
    
}
