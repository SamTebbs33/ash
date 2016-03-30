package ash.grammar.node;

import ash.grammar.AshParserVisitor;
import ash.grammar.antlr.AshParser;
import ash.semantics.TypeInstance;

import java.util.HashMap;

/**
 * Created by samtebbs on 16/03/2016.
 */
public class NodeChar extends Node<AshParser.ExprContext> implements Expression {

    private final char ch;
    HashMap<String, Character> escapeSequences = new HashMap<String, Character>(){{
        put("\\n", '\n');
        put("\\t", '\t');
        put("\\b", '\b');
        put("\\r", '\r');
        put("\\f", '\f');
        put("\\\"", '\"');
        put("\\\'", '\'');
        put("\\\\", '\\');
    }};

    public NodeChar(AshParser.ExprContext context, String str, AshParserVisitor visitor) {
        super(context, visitor);
        this.ch = str.length() >= 2 ? escapeSequences.get(str.substring(1, str.length()-1)) : str.charAt(1);
    }

    @Override
    public void analyse() {

    }

    @Override
    public String toString() {
        return "NodeChar{" +
                "ch=" + ch +
                ", escapeSequences=" + escapeSequences +
                "} " + super.toString();
    }

    @Override
    public TypeInstance getType() {
        return TypeInstance.CHAR;
    }
}
