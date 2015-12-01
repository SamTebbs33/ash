package ashc.main;

import java.io.*;
import java.util.*;

import ashc.codegen.*;
import ashc.grammar.*;
import ashc.grammar.Node.NodeDefFile;
import ashc.grammar.Node.NodeFile;
import ashc.grammar.Parser.GrammarException;

/**
 * AshCompiler
 *
 * @author samtebbs, 15:14:41 - 23 May 2015
 */
public class AshCompiler {

    private final Parser parser;
    private final Lexer lexer;
    private NodeFile fileNode;
    private NodeDefFile defFileNode;
    public String relFilePath, parentPath;
    public int errors;
    public String fileName;
    private boolean defFile = false;

    public static Stack<AshCompiler> compilers = new Stack<AshCompiler>();

    public AshCompiler(final String relFilePath) throws IOException {
        final File file = new File(relFilePath);
        fileName = file.getName();
        if (fileName.indexOf('.') > 0) fileName = fileName.substring(0, fileName.indexOf('.'));
        parentPath = file.getParentFile() != null ? file.getParentFile().getPath() : "";
        this.relFilePath = relFilePath;
        lexer = new Lexer(new BufferedReader(new FileReader(file)));
        parser = new Parser(lexer);
        compilers.push(this);
    }

    public void parseSourceFile() {
        try {
            defFile = false;
            fileNode = parser.parseFile();
        } catch (final GrammarException e) {
            parser.handleException(e);
        }
    }

    public void preAnalyse() {
        if (defFile) {
            defFileNode.preAnalyse();
        } else {
            fileNode.preAnalyse();
        }
    }

    public void analyse() {
        if (!defFile) fileNode.analyse(null);
        else defFileNode.analyse(null);
    }

    public void generate() {
        if (!defFile) {
            fileNode.generate();
            GenNode.generate();
        } else {
            defFileNode.generate();
            GenNode.generate();
        }
    }

    public static AshCompiler get() {
        return compilers.peek();
    }

    public void parseDefFile() {
        try {
            defFile = true;
            defFileNode = parser.parseDefFile();
        } catch (final GrammarException e) {
            parser.handleException(e);
        }
        parser.clear();
    }

}
