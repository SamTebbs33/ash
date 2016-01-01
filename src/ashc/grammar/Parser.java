package ashc.grammar;

import java.util.*;

import ashc.grammar.Lexer.*;
import ashc.grammar.Node.*;
import ashc.grammar.OperatorDef.EnumOperatorType;
import ashc.util.*;

/**
 * Ash
 *
 * @author samtebbs, 22:23:42 - 20 May 2015
 */
public class Parser {

    private final LinkedList<Token> tokens = new LinkedList<Token>();
    private final Lexer lexer;
    private int pointer = 0, pointerTemp = 0, line = 1, column = 1;
    public boolean silenceErrors = false;
    public int columnOffset, lineOffset;

    public static class GrammarException extends Exception {

        public GrammarException(final String arg0, int line, int col) {
            super("Error [" + line + ":" + col + "] " + arg0);
        }

        public void print(final int lineOffset, final int columnOffset, final Lexer lexer) {
            System.err.println(getMessage());
        }

    }

    public Parser(final Lexer lexer) {
        this.lexer = lexer;
        while (true)
            try {
                final Token token = lexer.getNextToken();
                if (token == null) break; // we have passed EOF
                tokens.add(token);
            } catch (final InvalidTokenException e) {
                System.err.printf("Error:%d:%d Invalid token (%s)%n", e.line, e.column, e.data);
                ashc.error.AshError.numErrors++;
            }
    }

    public void clear() {
        tokens.clear();
        pointer = 0;
        line = 1;
        column = 1;
    }

    public void handleException(final GrammarException e) {
        e.print(lineOffset, columnOffset, lexer);
    }

    /**
     * Return the next token, or null if we have passed the EOF token
     *
     * @return {@link Token}
     * @throws GrammarException
     */
    private Token getNext() throws GrammarException {
        if (pointer < tokens.size()) {
            final Token t = tokens.get(pointer++);
            line = t.line;
            column = t.columnStart;
            return t;
        }
        return null;
    }

    private Token current() throws GrammarException {
        Token t = getNext();
        rewind();
        return t;
    }

    /**
     * Move back one token
     */
    private void rewind() {
        pointer--;
    }

    /**
     * Move back by **a** tokens
     *
     * @param a
     */
    private void rewind(final int a) {
        pointer -= a;
    }

    /**
     * Expect the next token to have the same type as the argument and return it, else throw an error
     *
     * @param t - The token type expected
     * @return {@link Token}
     * @throws GrammarException
     */
    private Token expect(final TokenType t) throws GrammarException {
        final Token token = getNext();
        if ((token.type != t) && !silenceErrors) throw new UnexpectedTokenException(token, t);
        return token;
    }

    private Token expect(final String data, final TokenMatcher... matchers) throws GrammarException {
        final Token next = getNext();
        if (next.data.equals(data)) return next;
        for (final TokenMatcher matcher : matchers)
            if (matcher.matches(next)) return next;
        throw new UnexpectedTokenException(next, matchers, data);
    }

    private Token expect(final TokenMatcher... matchers) throws GrammarException {
        final Token token = getNext();
        for (final TokenMatcher matcher : matchers)
            if (matcher.matches(token)) return token;
        throw new UnexpectedTokenException(token, matchers);
    }

    private Token expectStr(final String...tokenData) throws GrammarException {
        final Token next = getNext();
        for(String str : tokenData) if(str.equals(next.data)) return next;
        if (!silenceErrors) throw new UnexpectedTokenException(next, tokenData);
        return next;
    }

    /**
     * Expect the next token to have the same type as any one of the arguments and return it, else throw an error
     *
     * @param t - The token types expected
     * @return {@link Token}
     * @throws GrammarException
     */
    private Token expect(final TokenType... t) throws GrammarException {
        final Token token = getNext();
        boolean found = false;
        for (final TokenType type : t)
            if (token.type == type) {
                found = true;
                break;
            }
        if (!found && !silenceErrors) throw new UnexpectedTokenException(token, t);
        return token;
    }

    private void savePointer() {
        pointerTemp = pointer;
    }

    private void restorePointer() {
        pointer = pointerTemp;
    }

    /**
     * Parse an optional list of import declarations
     *
     * @return {@link LinkedList}
     * @throws GrammarException
     */
    private LinkedList<NodeImport> parseImports() throws GrammarException {
        final LinkedList<NodeImport> imports = new LinkedList<Node.NodeImport>();
        while (getNext().type == TokenType.IMPORT) {
            final NodeQualifiedName name = parseQualifiedName();
            final NodeQualifiedName parent = name.copy().remove();
            imports.add(new NodeImport(line, column, name));

            while (getNext().type == TokenType.COMMA) {
                final String cls = expect(TokenType.ID).data;
                imports.add(new NodeImport(line, column, parent.copy().add(cls)));
            }
            rewind();

        }
        rewind();
        return imports;
    }

    /**
     * Parses and returns a qualified name node
     *
     * @return {@link NodeQualifiedName}
     * @throws GrammarException
     */
    private NodeQualifiedName parseQualifiedName() throws GrammarException {
        Token id = expect(TokenType.ID);
        final NodeQualifiedName name = new NodeQualifiedName(id.line, id.columnStart);
        name.add(id.data);
        while (getNext().type == TokenType.DOT) {
            id = expect(TokenType.ID);
            name.add(id.data);
        }
        rewind();
        return name;
    }

    /**
     * Parses and returns an optional package declaration
     *
     * @return {@link NodePackage}
     * @throws GrammarException
     */
    private NodePackage parsePackage() throws GrammarException {
        if (getNext().type == TokenType.PACKAGE) {
            final NodeQualifiedName name = parseQualifiedName();
            if (name != null) return new NodePackage(name.line, name.column, name);
        }
        rewind();
        return null;
    }

    /**
     * Returns true if the argument is a modifier token type
     *
     * @param type
     * @return boolean
     */
    private boolean isModifier(final TokenType type) {
        return (type == TokenType.PUBLIC) || (type == TokenType.PRIVATE) || (type == TokenType.PROTECTED) || (type == TokenType.FINAL)
                || (type == TokenType.REQUIRED) || (type == TokenType.NATIVE) || (type == TokenType.OVERRIDE) || (type == TokenType.STANDARD)
                || (type == TokenType.STATIC);
    }

    private LinkedList<NodeModifier> parseMods() throws GrammarException {
        final LinkedList<NodeModifier> mods = new LinkedList<NodeModifier>();
        Token token = getNext();
        TokenType type = token.type;
        while (isModifier(type)) {
            mods.add(new NodeModifier(line, column, token.data));
            token = getNext();
            type = token.type;
        }
        rewind();
        return mods;
    }

    /**
     * class id args? (: types)? { block }
     */
    private NodeClassDec parseClassDec(final LinkedList<NodeModifier> mods) throws GrammarException {
        final Token id = expect(TokenType.ID);
        final NodeTypes generics = null;
        NodeArgs args = null;
        NodeExprs superArgs = null;
        NodeTypes types = null;

        if (getNext().type == TokenType.PARENL) {
            rewind();
            args = parseArgs(TokenType.PARENL, TokenType.PARENR);
        } else rewind();

        if (getNext().type == TokenType.COLON) {
            final Tuple<NodeTypes, NodeExprs> supers = parseSuperTypes(true);
            types = supers.a;
            superArgs = supers.b;
        } else rewind();

        final NodeClassBlock block = parseClassBlock(true);
        return new NodeClassDec(id.line, id.columnStart, mods, types, args, id, block, generics, superArgs);
    }

    private NodeClassBlock parseClassBlock(boolean funcNeedsBody) throws GrammarException {
        return parseClassBlock(funcNeedsBody, new LinkedList<NodeModifier>());
    }

    private NodeClassBlock parseClassBlock(boolean funcsNeedBody, LinkedList<NodeModifier> modifiers) throws GrammarException {
        final NodeClassBlock block = new NodeClassBlock();
        if (getNext().type == TokenType.BRACEL) while (getNext().type != TokenType.BRACER) {
            rewind();
            LinkedList<NodeModifier> mods = (LinkedList) modifiers.clone();
            LinkedList<NodeModifier> mods2 = parseMods();
            mods.addAll(mods2);
            final Token token = expect(TokenType.BRACEL, TokenType.FUNC, TokenType.OPTYPE, TokenType.MUT, TokenType.CONST, TokenType.VAR, TokenType.BRACER);
            switch (token.type) {
                case BRACEL:
                    // If there were no modifiers then a constructor block is parsed, else a modifier block is parsed
                    if(mods2.size() == 0) block.addConstructBlock(parseFuncBlock(true, false));
                    else {
                        rewind();
                        NodeClassBlock modifierBlock = parseClassBlock(funcsNeedBody, mods);
                        for(NodeVarDec varDec : modifierBlock.varDecs) block.varDecs.add(varDec);
                        for(NodeFuncDec funcDec : modifierBlock.funcDecs) block.funcDecs.add(funcDec);
                    }
                    break;
                /*case INIT:
                    block.add(parseFuncBlock(true, false));
                    break;*/
                case VAR:
                case CONST:
                    NodeVarDec dec = parseVarDec(mods, token, true);
                    block.add(dec);
                    while (dec.subDec != null) {
                        block.add(dec.subDec);
                        dec = dec.subDec;
                    }
                    continue;
                case FUNC:
                    rewind();
                case OPTYPE:
                    block.add(parseFuncDec(false, funcsNeedBody, mods, token.type == TokenType.OPTYPE ? EnumOperatorType.get(token.data) : null));
                    continue;
                case MUT:
                    rewind();
                    block.add(parseMutFuncDec(true, mods));
                    continue;
            }
        }
        else rewind();
        return block;
    }

    private NodeFuncDec parseMutFuncDec(final boolean needsBody, final LinkedList<NodeModifier> mods) throws GrammarException {
        expect(TokenType.MUT);
        final Token id = expect(TokenType.ID, TokenType.OP);
        final NodeArgs args = parseArgs(TokenType.PARENL, TokenType.PARENR);
        NodeType throwsType = null;
        NodeFuncBlock block = null;
        boolean hasBody = true;
        if (expect(TokenType.ARROW, TokenType.BRACEL, TokenType.LAMBDAARROW).type == TokenType.ARROW)
            throwsType = parseType();
        else rewind();

        if (needsBody)
            block = parseFuncBlock(true, false);
        else {
            // Parse an optional function block, used for interface bodies
            savePointer();
            silenceErrors = true;
            block = parseFuncBlock(true, false);
            silenceErrors = false;
            if (block == null) {
                // If there wasn't a function block, then we have to restore the token pointer
                hasBody = false;
                restorePointer();
                block = new NodeFuncBlock();
            }
        }
        return new NodeFuncDec(id.line, id.columnStart, mods, id.data, args, throwsType, block, new NodeTypes(), true, hasBody);
    }

    /**
     * func id(args?) (-> type)?
     */
    private NodeFuncDec parseFuncDec(final boolean allowExtensionFunc, final boolean needsBody, final LinkedList<NodeModifier> mods, EnumOperatorType opType) throws GrammarException {
        Token id;
        Token extensionType = null;
        boolean hasBody = true;
        expect(TokenType.FUNC);
        id = expect(TokenType.ID, TokenType.OP, TokenType.ARRAYDIMENSION);
        if (allowExtensionFunc) if (getNext().type == TokenType.DOT) {
            extensionType = id;
            id = expect(TokenType.ID, TokenType.OP, TokenType.ARRAYDIMENSION);
        } else rewind();
        final NodeTypes types = null;// parseGenericsDecs();
        final NodeArgs args = parseArgs(TokenType.PARENL, TokenType.PARENR);
        NodeType type = null, throwsType = null;
        NodeFuncBlock block = new NodeFuncBlock();
        if (needsBody) {
            expect("=", TokenType.ARROW, TokenType.LAMBDAARROW, TokenType.BRACEL);
            rewind();
        }

        if (getNext().type == TokenType.LAMBDAARROW) type = parseType();
        else {
            type = new NodeType(id.line, id.columnStart, "void");
            rewind();
        }

        if (getNext().type == TokenType.ARROW) throwsType = parseType();
        else rewind();

        if (needsBody) block = parseFuncBlock(true, !type.id.equals("void"));
        else {
            Token next = getNext();
            rewind();
            if (next.data.equals("=") || next.type == TokenType.BRACEL) {
                block = parseFuncBlock(true, !type.id.equals("void"));
            } else {
                hasBody = false;
                block = new NodeFuncBlock();
            }
        }
        return new NodeFuncDec(id.line, id.columnStart, mods, id.data, args, type, throwsType, block, types, extensionType, hasBody, opType);
    }

    public NodeTypes parseGenericsDecs() throws GrammarException {
        if (getNext().data.equals("<")) {
            final NodeTypes types = parseSuperTypes(false).a;
            expectStr(">");
            return types;
        } else rewind();
        return new NodeTypes();
    }

    private NodeFuncBlock parseFuncBlock(final boolean allowSingleLine, final boolean singleLineExpression) throws GrammarException {
        return parseFuncBlock(allowSingleLine, singleLineExpression, null);
    }


    private NodeFuncBlock parseFuncBlock(final boolean allowSingleLine, final boolean singleLineExpression, final TokenMatcher singleLineToken) throws GrammarException {
        final NodeFuncBlock block = new NodeFuncBlock();
        Token token = null;
        if (allowSingleLine)
            token = (singleLineToken == null) ? expect("=", TokenType.BRACEL) : expect(singleLineToken, TokenType.BRACEL);
        else token = expect(TokenType.BRACEL);
        if (token.type == TokenType.BRACEL) while (getNext().type != TokenType.BRACER) {
            rewind();
            block.add(parseFuncStmt());
        }
        else if (singleLineExpression) block.singleLineExpr = parseExpression();
        else block.singleLineStmt = parseFuncStmt();
        return block;
    }

    private IFuncStmt parseFuncStmt() throws GrammarException {
        final Token token = expect(TokenTypeGroup.FUNC_CALL, TokenTypeGroup.VAR_DEC, TokenTypeGroup.CONTROL_STMT, TokenType.RETURN, TokenType.BREAK, TokenType.CONTINUE);
        switch (token.type) {
            case BREAK:
                return new NodeBreak(token.line, token.columnStart);
            case CONTINUE:
                return new NodeContinue(token.line, token.columnStart);
            case RETURN:
                silenceErrors = true;
                savePointer();
                final IExpression expr = parseExpression();
                silenceErrors = false;
                if (expr == null) restorePointer();
                return new NodeReturn(token.line, token.columnStart, expr);
            case ID:
            case SELF:
            case THIS:
            case SUPER:
                rewind();
                final IFuncStmt stmt = parsePrefix();
                if (stmt instanceof NodeVariable) {
                    final Token op = expect("=", TokenType.COMPOUNDASSIGNOP, TokenType.OP);
                    if ((op.type == TokenType.OP) && !op.data.equals("=")) {
                        if (!OperatorDef.operatorDefExists(op.data, EnumOperatorType.PREFIX))
                            throw new GrammarException("1. "
                                    + " operator: " + op.data, op.line, op.columnStart);
                        OperatorDef opDef = OperatorDef.getOperatorDef(op.data, EnumOperatorType.PREFIX);
                        return new NodeUnary(op.line, op.columnStart, (NodeVariable) stmt, opDef, false);
                    }
                    return new NodeVarAssign(op.line, op.columnStart, (NodeVariable) stmt, op.data, parseExpression());
                } else return stmt;
            case IF:
                return parseIfStmt(false);
            case WHILE:
                return parseWhileStmt();
            case FOR:
                return parseForStmt();
            case MATCH:
                return parseMatchStmt(false);
            case VAR:
            case CONST:
                return parseVarDec(null, token, false);
        }
        return null;
    }

    private NodeMatch parseMatchStmt(boolean exprMode) throws GrammarException {
        final IExpression expr = parseExpression();
        expect(TokenType.BRACEL);
        final NodeMatch match = new NodeMatch(((Node) expr).line, ((Node) expr).column, expr);
        match.inExprMode = exprMode;
        while (getNext().type != TokenType.BRACER) {
            rewind();
            final NodeMatchCase matchCase = parseMatchCase(exprMode);
            match.add(matchCase);
            if (matchCase.isDefaultCase) {
                expect(TokenType.BRACER);
                break;
            }
        }
        return match;
    }

    private NodeMatchCase parseMatchCase(boolean exprMode) throws GrammarException {
        Token next;
        if ((next = getNext()).type == TokenType.UNDERSCORE)
            return new NodeMatchCase(next.line, next.columnStart, null, parseFuncBlock(true, exprMode, TokenType.LAMBDAARROW));
        else rewind();
        final IExpression expr = parseExpression();
        final NodeMatchCase matchCase = new NodeMatchCase(((Node) expr).line, ((Node) expr).column, expr, null);
        while (getNext().type == TokenType.COMMA)
            matchCase.exprs.add(parseExpression());
        rewind();
        matchCase.block = parseFuncBlock(true, exprMode, TokenType.LAMBDAARROW);
        matchCase.block.inFunction = false;
        return matchCase;
    }

    private IFuncStmt parseForStmt() throws GrammarException {
        int parens = 0;
        while (getNext().type == TokenType.PARENL)
            parens++;
        rewind();
        final Token next = expect(TokenType.ID, TokenType.VAR, TokenType.COMMA);
        if (next.type == TokenType.ID) {
            expect(TokenType.IN);
            final IExpression expr = parseExpression();
            while (parens-- > 0)
                expect(TokenType.PARENR);
            return new NodeForIn(line, column, next.data, expr, parseConstructBlock());
        } else {
            rewind();
            IFuncStmt initStmt = null, endStmt = null;
            IExpression condition = null;
            if (next.type != TokenType.COMMA) {
                initStmt = parseFuncStmt();
                expect(TokenType.COMMA);
            }
            if (getNext().type != TokenType.COMMA) {
                rewind();
                condition = parseExpression();
                expect(TokenType.COMMA);
            }
            endStmt = parseFuncStmt();
            while (parens-- > 0)
                expect(TokenType.PARENR);
            return new NodeForNormal(line, column, initStmt, condition, endStmt, parseConstructBlock());
        }
    }

    private IFuncStmt parseWhileStmt() throws GrammarException {
        return new NodeWhile(line, column, parseExpression(), parseConstructBlock());
    }

    private NodeIf parseIfStmt(final boolean isElseIf) throws GrammarException {
        final NodeIf ifStmt = new NodeIf(line, column, parseExpression(), parseConstructBlock(), false, isElseIf);
        if (getNext().type == TokenType.ELSE) {
            if (getNext().type == TokenType.IF) ifStmt.elseStmt = parseIfStmt(true);
            else {
                rewind();
                ifStmt.elseStmt = new NodeIf(line, column, null, parseConstructBlock(), true, false);
            }
        } else rewind();
        return ifStmt;
    }

    private NodeFuncBlock parseConstructBlock() throws GrammarException {
        if (getNext().type == TokenType.BRACEL) {
            rewind();
            return parseFuncBlock(false, false);
        } else {
            rewind();
            final NodeFuncBlock block = new NodeFuncBlock();
            block.add(parseFuncStmt());
            return block;
        }
    }

    // A prefix is just another name for a func call or variable
    private NodePrefix parsePrefix() throws GrammarException {
        NodePrefix prefix = null;
        do {
            final Token id = expect(TokenType.ID, TokenType.SELF, TokenType.SUPER, TokenType.THIS);
            savePointer();
            final NodeTypes generics = null;
            if (getNext().type == TokenType.PARENL) {
                rewind();
                final NodeExprs exprs = parseCallArgs(TokenType.PARENL, TokenType.PARENR);
                boolean unwrapped = false;
                if (getNext().data.equals("!")) unwrapped = true;
                else rewind();
                prefix = new NodeFuncCall(id.line, id.columnStart, id.data, exprs, prefix, unwrapped, id.type == TokenType.THIS, id.type == TokenType.SUPER, generics);
            } else {
                // Variable nodes cannot have generics
                if (generics != null) {
                    restorePointer();
                    throw new UnexpectedTokenException(getNext());
                } else rewind();
                boolean unwrapped = false;
                if (getNext().data.equals("!")) unwrapped = true;
                else rewind();
                prefix = new NodeVariable(id.line, id.columnStart, id.data, prefix, unwrapped);
            }

        } while (getNext().type == TokenType.DOT);
        rewind();
        return prefix;
    }

    private NodeExprs parseCallArgs(final TokenType start, final TokenType end) throws GrammarException {
        final NodeExprs exprs = new NodeExprs();
        expect(start);
        Token next = getNext();
        if (next.type == end) return exprs;
        else rewind();

        do {
            final IExpression expr = parseExpression();
            exprs.add(expr);
            next = getNext();
        } while (next.type == TokenType.COMMA);
        rewind();
        expect(end);
        return exprs;
    }

    private IExpression parsePrimaryExpression() throws GrammarException {
        final Token next = expect(Lexer.TokenTypeGroup.EXPRESSION_STARTER);
        IExpression expr = null;
        switch (next.type) {
            case MATCH:
                expr = parseMatchStmt(true);
                break;
            case FUNC:
            case INTERFACE:
                expr = parseClosure(next);
                break;
            case NULL:
                expr = new NodeNull();
                break;
            case ID:
                rewind();
                expr = parsePrefix();
                break;
            case THIS:
                expr = new NodeThis(next.line, next.columnStart);
                break;
            case SELF:
                expr = new NodeSelf(next.line, next.columnStart);
                break;
            case OP:
                if (!OperatorDef.operatorDefExists(next.data, EnumOperatorType.PREFIX)) {
                    throw new GrammarException("Undefined prefix operator: " + next.data, next.line, next.columnStart);
                }
                final OperatorDef operator = OperatorDef.getOperatorDef(next.data, EnumOperatorType.PREFIX);
                expr = new NodeUnary(next.line, next.columnStart, parsePrimaryExpression(), operator, true);
                break;
            case BRACEL:
                final NodeList listExpr = new NodeList(next.line, next.columnStart);
                boolean isHashMap = false;
                // Parse an optional expression
                silenceErrors = true;
                IExpression listElement = parseExpression();
                silenceErrors = false;
                // If there was no expression, then return an empty list
                if (listElement == null) break;
                // If the next is a colon, then we need to parse a hash map literal
                if (getNext().type == TokenType.COLON) {
                    isHashMap = true;
                    listExpr.addMapVal(parseExpression());
                } else rewind();
                // Add the list element as a value
                listExpr.addListVal(listElement);
                // List elements are separated by commas
                while (expect(TokenType.COMMA, TokenType.BRACER).type == TokenType.COMMA) {
                    listElement = parseExpression();
                    if (isHashMap) {
                        // If this is a hash map literal, we have to parse a colon and map value
                        expect(TokenType.COLON);
                        listExpr.addMapVal(parseExpression());
                    }
                }
                expr = listExpr;
                break;
            case BRACKETL:
                rewind();
                expr = new NodeArray(next.line, next.columnStart);
                ((NodeArray) expr).exprs = parseCallArgs(TokenType.BRACKETL, TokenType.BRACKETR).exprs;
                break;
            case NEW:
                NodeArraySize arraySize = null;
                final Token next2 = expect(TokenType.BRACKETL, TokenType.BRACEL);
                if (next2.type == TokenType.BRACKETL) {
                    // Parse an array type and size initialiser
                    arraySize = new NodeArraySize(next.line, next.columnStart, parseType());
                    expect(TokenType.COMMA);
                    do
                        arraySize.arrDims.add(parseExpression());
                    while (expect(TokenType.BRACKETR, TokenType.COMMA).type == TokenType.COMMA);
                } else {
                    // Parse a list type and/or size initialiser. Sizes are not compulsory
                    arraySize = new NodeListSize(next.line, next.columnStart, parseType());
                    if (expect(TokenType.COMMA, TokenType.BRACER).type == TokenType.COMMA) {
                        arraySize.arrDims.add(parseExpression());
                        expect(TokenType.BRACER);
                    }
                }
                expr = arraySize;
                break;
            case PARENL:
                expr = parseExpression();
                if (expect(TokenType.PARENR, TokenType.COMMA).type == TokenType.COMMA) {
                    final NodeTupleExpr tuple = new NodeTupleExpr(next.line, next.columnStart);
                    tuple.exprs.add(expr);
                    do
                        tuple.exprs.add(parseExpression());
                    while (expect(TokenType.PARENR, TokenType.COMMA).type == TokenType.COMMA);
                    expr = tuple;
                }
                break;
            case OCTINT:
                expr = new NodeInteger(next.line, next.columnStart, Integer.parseInt(next.data, 8));
                break;
            case HEXINT:
                expr = new NodeInteger(next.line, next.columnStart, Integer.parseInt(next.data, 16));
                break;
            case BININT:
                expr = new NodeInteger(next.line, next.columnStart, Integer.parseInt(next.data, 2));
                break;
            case INT:
                expr = new NodeInteger(next.line, next.columnStart, Integer.parseInt(next.data, 10));
                break;
            case LONG:
                expr = new NodeLong(next.line, next.columnStart, Long.parseLong(next.data.substring(0, next.data.length() - 1)));
                break;
            case FLOAT:
                expr = new NodeFloat(next.line, next.columnStart, Float.parseFloat(next.data));
                break;
            case DOUBLE:
                expr = new NodeDouble(next.line, next.columnStart, Double.parseDouble(next.data));
                break;
            case STRING:
                expr = new NodeString(next.line, next.columnStart, next.data.substring(1, next.data.length() - 1));
                break;
            case CHAR:
                expr = new NodeChar(next.line, next.columnStart, next.data.charAt(1));
                break;
            case BOOL:
                expr = new NodeBool(next.line, next.columnStart, next.data.equals("true"));
                break;
        }
        return expr;
    }

    private NodeClosure parseClosure(Token closureKeyword) throws GrammarException {
        NodeClosure closure = ((closureKeyword.type == TokenType.FUNC) ? new NodeFuncClosure(closureKeyword.line, closureKeyword.columnStart) : new NodeInterfaceClosure(closureKeyword.line, closureKeyword.columnStart));
        closure.args = parseArgs(TokenType.PARENL, TokenType.PARENR);
        if (getNext().type == TokenType.LAMBDAARROW) {
            closure.type = parseType();
        } else rewind();
        closure.body = parseFuncBlock(true, closure.type != null);
        return closure;
    }

    private LinkedList<NodeTupleExprArg> parseTupleExpr() throws GrammarException {
        expect(TokenType.BRACKETL);
        boolean named = false, unnamed = false;
        final LinkedList<NodeTupleExprArg> args = new LinkedList<NodeTupleExprArg>();
        do {
            final NodeTupleExprArg arg = parseTupleExprArg(named, unnamed);
            if (arg.name != null) named = true;
            else {
                arg.name = (char) ('a' + args.size()) + "";
                unnamed = true;
            }
            args.add(arg);

        } while (expect(TokenType.BRACKETR, TokenType.COMMA).type == TokenType.COMMA);
        return args;
    }

    private NodeTupleExprArg parseTupleExprArg(final boolean named, final boolean unnamed) throws GrammarException {
        final Token next = getNext();
        final NodeTupleExprArg arg = new NodeTupleExprArg(next.line, next.columnStart);
        if (next.type == TokenType.ID) {
            if (getNext().type == TokenType.COLON) arg.name = next.data;
            else rewind(2);
        } else rewind();
        arg.expr = parseExpression();
        return arg;
    }

    public IExpression parseExpression() throws GrammarException {
        final IExpression expr = parsePrimaryExpression();
        final Token next = getNext();
        switch (next.type) {
            case DOUBLEDOT:
                boolean exclusiveEnd = false;
                if (getNext().data.equals("<")) exclusiveEnd = true;
                else rewind();
                return new NodeRange(next.line, next.columnStart, expr, parseExpression(), exclusiveEnd);
            case AS:
                return new NodeAs(next.line, next.columnStart, expr, parseSuperType());
            case IS:
                return new NodeIs(next.line, next.columnStart, expr, parseSuperType());
            // Postfix unary expression
            case OP:
                if (next.data.equals("!")) return new NodeUnwrapOptional(next.line, next.columnStart, expr);
                else if (next.data.equals("?")) {
                    final IExpression exprTrue = parseExpression();
                    expect(TokenType.COLON);
                    return new NodeTernary(expr, exprTrue, parseExpression());
                }
                OperatorDef postfixOp = OperatorDef.getOperatorDef(next.data, EnumOperatorType.POSTFIX), binaryOp = OperatorDef.getOperatorDef(next.data, EnumOperatorType.BINARY);
                if (postfixOp == null && binaryOp == null)
                    throw new GrammarException("Undefined postfix or binary operator: " + next.data, next.line, next.columnStart);
                if (postfixOp != null) return new NodeUnary(next.line, next.columnStart, expr, postfixOp, false);
                else return new NodeBinary(next.line, next.columnStart, expr, binaryOp, parseExpression());
            case BRACKETL:
                NodeArrayAccess arrayExpr = new NodeArrayAccess(((Node) expr).line, ((Node) expr).column, expr, parseExpression());
                expect(TokenType.BRACKETR);
                while (getNext().type == TokenType.BRACKETL) {
                    arrayExpr = new NodeArrayAccess(arrayExpr.line, arrayExpr.column, arrayExpr, parseExpression());
                    expect(TokenType.BRACKETR);
                }
                rewind();
                return arrayExpr;
        }
        rewind();
        return expr;
    }

    private NodeVarDec parseVarDec(final LinkedList<NodeModifier> mods, final Token keyword, final boolean allowMultipleDecs) throws GrammarException {
        final Token id = expect(TokenType.ID);
        Token next = expect("=", TokenType.COLON);
        NodeVarDec varDec;
        final TokenType type = next.type;
        // If a type is given, parse it as an explicit var declaration,
        // otherwise parse it as an implicit var declaration
        if (type == TokenType.COLON) {
            final NodeType nodeType = parseType();
            varDec = new NodeVarDecExplicit(id.line, id.columnStart, mods, keyword.data, id.data, nodeType, null);
            if (getNext().data.equals("="))
                varDec = new NodeVarDecExplicit(id.line, id.columnStart, mods, keyword.data, id.data, nodeType, parseExpression());
            else rewind();
        } else varDec = new NodeVarDecImplicit(id.line, id.columnStart, mods, keyword.data, id.data, parseExpression());

        // Parse a property block
        if (getNext().type == TokenType.BRACEL) {
            NodeFuncBlock getBlock = null, setBlock = null;
            // A right brace ends the property block
            while (getNext().type != TokenType.BRACER) {
                rewind();
                // If neither a set block or get block have been defined yet,
                // parse either
                if ((setBlock == null) && (getBlock == null)) {
                    next = expectStr("get", "set");
                    if (next.data.equals("set")) setBlock = parseFuncBlock(true, true);
                    else getBlock = parseFuncBlock(true, true);
                }
                next = getNext();
                if (next.data.equals("get")) {
                    // If a get block has already been defined, throw errors
                    if (getBlock != null) {
                        // If a set block has not been defined, then say we
                        // expected a set block instead of another get block
                        if (setBlock == null) throw new UnexpectedTokenException(next, TokenType.BRACER);
                        else throw new UnexpectedTokenException(next, TokenType.BRACER);
                    } else getBlock = parseFuncBlock(true, true);
                } else if (next.data.equals("set")) {
                    // If a set block has already been defined, throw errors
                    if (setBlock != null) {
                        // If a get block has not been defined, then say we
                        // expected a get block instead of another set block
                        if (getBlock == null) throw new UnexpectedTokenException(next, TokenType.BRACER);
                        else throw new UnexpectedTokenException(next, TokenType.BRACER);
                    } else setBlock = parseFuncBlock(true, true);
                } else rewind();
            }
            varDec.getBlock = getBlock;
            varDec.setBlock = setBlock;

            // The get and set block will become functions in the bytecode, so
            // tell the blocks that they are indeed in functions
            if (varDec.getBlock != null) varDec.getBlock.inFunction = true;
            if (varDec.setBlock != null) varDec.setBlock.inFunction = true;
        } else rewind();

        if (allowMultipleDecs) // Var decs can be chained using commas, and they share the same keyword and modifiers
            if (getNext().type == TokenType.COMMA) varDec.subDec = parseVarDec(mods, keyword, true);
            else rewind();

        return varDec;
    }

    private NodeTypes parseTypes(final boolean necessary) throws GrammarException {
        final NodeTypes types = new NodeTypes();
        savePointer();
        final NodeType type = parseType(necessary);
        if (type == null) {
            restorePointer();
            return null;
        }
        types.add(type);
        while (getNext().type == TokenType.COMMA)
            types.add(parseType());
        rewind();
        return types;
    }

    private NodeType parseType() throws GrammarException {
        return parseType(true);
    }

    private NodeTypes parseTypes() throws GrammarException {
        return parseTypes(true);
    }

    private Tuple<NodeTypes, NodeExprs> parseSuperTypes(final boolean allowArgs) throws GrammarException {
        final NodeTypes types = new NodeTypes();
        NodeExprs superArgs = null;
        types.add(parseSuperType());
        // Parse arguments to the super-class constructor
        if (getNext().type == TokenType.PARENL) {
            rewind();
            superArgs = parseCallArgs(TokenType.PARENL, TokenType.PARENR);
        } else rewind();
        while (getNext().type == TokenType.COMMA)
            types.add(parseSuperType());
        rewind();
        return new Tuple<NodeTypes, NodeExprs>(types, superArgs);
    }

    private NodeArgs parseArgs(TokenMatcher start, TokenMatcher end) throws GrammarException {
        if (start != null) expect(start);
        final NodeArgs args = new NodeArgs();
        NodeArg arg;
        if ((arg = parseArg()) != null) {
            args.add(arg);
            while (getNext().type == TokenType.COMMA)
                args.add(parseArg());
            rewind();
        }
        if (end != null) expect(end);
        return args;
    }

    private NodeArg parseArg() throws GrammarException {
        final Token id = getNext();
        if (id.type == TokenType.ID) {
            expect(TokenType.COLON);
            final NodeType type = parseType();
            IExpression defExpr = null;
            if (expect("=", TokenType.COMMA, TokenType.PARENR).data.equals("=")) defExpr = parseExpression();
            else rewind();
            return new NodeArg(id.line, id.columnStart, id.data, type, defExpr);
        } else rewind();
        return null;
    }

    private NodeType parseType(final boolean necessary) throws GrammarException {
        final NodeType type = new NodeType();
        // Parse opening brackets
        while (getNext().type == TokenType.BRACKETL) type.arrDims++;
        rewind();
        TokenType tokenType = getNext().type;
        if(tokenType == TokenType.FUNC) {
            // Parse a function type
            expect(TokenType.PARENL);
            if(getNext().type != TokenType.PARENR){
                rewind();
                do{
                    type.funcTypeArgs.add(parseType());
                }while (getNext().type == TokenType.COMMA);
                rewind();
                expect(TokenType.PARENR);
            }
            if(getNext().type == TokenType.LAMBDAARROW) type.funcTypeReturn = parseType();
            else rewind();
        }else if (tokenType == TokenType.PARENL) {
            type.id = "tuple";
            // Parse a tuple
            // Tuple types must have more then one type to avoid clashes between tuple expressions and bracketed expressions
            type.tupleTypes.add(parseType());
            expect(TokenType.COMMA);
            do
                type.tupleTypes.add(parseType());
            while (expect(TokenType.COMMA, TokenType.PARENR).type == TokenType.COMMA);
        } else {
            rewind();
            try {
                type.id = expect(TokenType.ID, TokenType.PRIMITIVE).data;
            } catch (final UnexpectedTokenException e) {
                if (!necessary) return null;
                else throw e;
            }
            type.generics = null;
        }

        // Parse closing brackets
        int i = 0;
        while (i++ < type.arrDims) expect(TokenType.BRACKETR);
        if (getNext().data.equals("?")) type.optional = true;
        else rewind();
        return type;
    }

    private NodeType parseSuperType() throws GrammarException {
        final Token id = expect(TokenType.ID);
        final NodeType type = new NodeType(id.line, id.columnStart, id.data);
        type.generics = null;
        return type;
    }

    private NodeEnumDec parseEnumDec(final LinkedList<NodeModifier> mods) throws GrammarException {
        final Token id = expect(TokenType.ID);
        NodeArgs args = null;

        if (getNext().type == TokenType.PARENL) {
            rewind();
            args = parseArgs(TokenType.PARENL, TokenType.PARENR);
        } else rewind();

        final NodeEnumBlock block = parseEnumBlock();
        return new NodeEnumDec(id.line, id.columnStart, mods, id, args, block);
    }

    private NodeEnumBlock parseEnumBlock() throws GrammarException {
        final LinkedList<NodeEnumInstance> instances = new LinkedList<Node.NodeEnumInstance>();
        instances.add(parseEnumInstance());
        while (getNext().type == TokenType.COMMA)
            instances.add(parseEnumInstance());
        rewind();
        return new NodeEnumBlock(line, column, instances, parseClassBlock(true));
    }

    private NodeInterfaceDec parseInterfaceDec(final LinkedList<NodeModifier> mods) throws GrammarException {
        final Token id = expect(TokenType.ID);
        NodeArgs args = null;
        NodeTypes types = null;

        if (getNext().type == TokenType.PARENL) {
            rewind();
            args = parseArgs(TokenType.PARENL, TokenType.PARENR);
        } else rewind();

        if (getNext().type == TokenType.COLON) types = parseSuperTypes(false).a;
        else rewind();
        final NodeClassBlock block = parseClassBlock(false);
        return new NodeInterfaceDec(id.line, id.columnStart, mods, types, args, id, block);
    }

    private NodeTypeDec parseTypeDec() throws GrammarException {
        final LinkedList<NodeModifier> mods = parseMods();
        final Token token = expect(TokenType.CLASS, TokenType.ENUM, TokenType.INTERFACE);
        switch (token.type) {
            case CLASS:
                final NodeClassDec clsDec = parseClassDec(mods);
                return clsDec;
            case ENUM:
                final NodeEnumDec enumDec = parseEnumDec(mods);
                return enumDec;
            case INTERFACE:
                final NodeInterfaceDec interfaceDec = parseInterfaceDec(mods);
                return interfaceDec;
        }
        return null;
    }

    private LinkedList<NodeTypeDec> parseTypeDecs() throws GrammarException {
        final LinkedList<NodeTypeDec> typeDecs = new LinkedList<Node.NodeTypeDec>();
        NodeTypeDec typeDec = parseTypeDec();
        typeDecs.add(typeDec);
        while (true) {
            // Having more than 1 type declaration is optional, so look out for
            // EOF
            if (getNext().type == TokenType.EOF) break;
            rewind();
            typeDec = parseTypeDec();
            typeDecs.add(typeDec);
        }
        return typeDecs;
    }

    public NodeFile parseFile() throws GrammarException {
        final NodePackage pkg = parsePackage();
        final LinkedList<NodeInclude> includes = parseIncludes();
        final LinkedList<NodeImport> imports = parseImports();
        final LinkedList<NodeAlias> aliases = parseAliases();
        final LinkedList<NodeTypeDec> typeDecs = parseTypeDecs();
        return new NodeFile(pkg, includes, imports, aliases, typeDecs);
    }

    public LinkedList<NodeAlias> parseAliases() throws GrammarException {
        final LinkedList<NodeAlias> result = new LinkedList<NodeAlias>();
        Token next;
        while ((next = getNext()).type == TokenType.ALIAS) {
            final String alias = expect(TokenType.ID).data;
            expect("=");
            result.add(new NodeAlias(next.line, next.columnStart, alias, parseSuperType()));
        }
        rewind();
        return result;
    }

    private NodeEnumInstance parseEnumInstance() throws GrammarException {
        final Token id = expect(TokenType.ID);
        if (getNext().type == TokenType.PARENL) {
            rewind();
            return new NodeEnumInstance(id.line, id.columnStart, id, parseCallArgs(TokenType.PARENL, TokenType.PARENR));
        }
        rewind();
        return new NodeEnumInstance(id.line, id.columnStart, id, null);
    }

    public NodeDefFile parseDefFile() throws GrammarException {
        final NodeDefFile defFile = new NodeDefFile();
        defFile.pkg = parsePackage();
        defFile.includes = parseIncludes();
        defFile.imports = parseImports();
        defFile.operatorDefs = parseOperatorDefs();
        defFile.funcs = parseDefFileFuncDecs();
        return defFile;
    }

    private LinkedList<NodeFuncDec> parseDefFileFuncDecs() throws GrammarException {
        final LinkedList<NodeFuncDec> decs = new LinkedList<Node.NodeFuncDec>();
        while (true) {
            Token next = getNext();
            EnumOperatorType opType = null;
            if (next.type == TokenType.FUNC) rewind();
            else if (next.type == TokenType.OPTYPE) opType = EnumOperatorType.get(next.data);
            else {
                rewind();
                break;
            }
            decs.add(parseFuncDec(true, true, new LinkedList<>(), opType));
        }
        return decs;
    }

    private LinkedList<NodeOperatorDef> parseOperatorDefs() throws GrammarException {
        final LinkedList<NodeOperatorDef> operatorDefs = new LinkedList<>();
        while ((getNext()).type == TokenType.OPERATOR) {
            operatorDefs.add(parseOperatorDef());
        }
        rewind();
        return operatorDefs;
    }

    private NodeOperatorDef parseOperatorDef() throws GrammarException {
        final Token id = expect(TokenType.OP);
        expect("=");
        expect("type");
        expect(TokenType.COLON);
        final String type = expect(TokenType.OPTYPE).data;
        expect(TokenType.COMMA);
        expect("name");
        expect(TokenType.COLON);
        final String name = expect(TokenType.ID).data;
        String assoc = null;
        int precedence = 0;
        // Only binary operators have an associativity and precedence
        if (type.equals("binary")) {
            expect(TokenType.COMMA);
            expect("assoc");
            expect(TokenType.COLON);
            assoc = expect(TokenType.ID).data;
            expect(TokenType.COMMA);
            expect("prec");
            expect(TokenType.COLON);
            precedence = Integer.parseInt(expect(TokenType.INT).data);
        }
        return new NodeOperatorDef(id.line, id.columnStart, id.data, name, type, assoc, precedence);
    }

    private LinkedList<NodeInclude> parseIncludes() throws GrammarException {
        final LinkedList<NodeInclude> includes = new LinkedList<>();
        Token next;
        while ((next = getNext()).type == TokenType.INCLUDE)
            includes.add(new NodeInclude(next.line, next.columnStart, parseQualifiedName()));
        rewind();
        return includes;
    }

}
