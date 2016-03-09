package ash.grammar;

import ash.grammar.node.FileLocation;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.io.PrintStream;
import java.util.BitSet;

/**
 * Created by samtebbs on 09/03/2016.
 */
public class AshParserErrorListener implements ANTLRErrorListener {

    private void report(AshErrorType errorType, FileLocation location, String msg) {
        errorType.out.printf("%s [%d:%d-%d] %s%n", errorType.str, location.line, location.column,
                location.getEndColumn(), msg);
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s, RecognitionException e) {
        report(AshErrorType.SYNTAX, new FileLocation(((Token) o)), s);
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet) {

    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitSet, ATNConfigSet atnConfigSet) {

    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atnConfigSet) {

    }

    public enum AshErrorType {
        SYNTAX("Syntax error", System.err), WARNING("Warning", System.out);

        String str;
        PrintStream out;

        AshErrorType(String str, PrintStream out) {
            this.str = str;
            this.out = out;
        }

    }
}
