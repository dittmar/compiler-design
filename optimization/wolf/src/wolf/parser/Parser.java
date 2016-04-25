package wolf.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import wolf.*;
import wolf.enums.*;
import wolf.interfaces.*;
import wolf.node.*;

/**
 * A recursive descent Parser for the WOL(F) programming language. Each
 * non-helper function of the parser represents a non-terminal in the grammar.
 * The rules for that non-terminal are in the header documentation for its
 * corresponding function.
 *
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joe Alacqua
 * @version Mar 1, 2016
 */
public class Parser {

    private WolfLexing lexer;
    private Token token;
    private FileWriter writer;
    private int line_count;
    private Program ast;
    private final List<String> parsed;

    /**
     * Initialize line count.
     */
    public Parser() {
        parsed = new ArrayList<>();
        line_count = 1;
    }

    /**
     * Parse a WOL(F) program from stdin.
     */
    public void parse() {
        lexer = new WolfLexing();
        token = nextValidToken();
        try {
            writer = new FileWriter(new File("out.parse"));
        } catch (IOException e) {
            System.err.println("Could not create log file, writing to stdout");
        }
        ast = Program();
        BuildSymbolTable bst = new BuildSymbolTable();
        bst.visit(ast);
        /*SemanticTypeCheck stc = new SemanticTypeCheck(bst.getTables(),
            bst.getProgramTable(),
            bst.getLambdaTableList()
        );
        stc.visit(ast);*/
    }

    /**
     * Parse a WOL(F) program from a file.
     *
     * @param filename is the name of the file to parse as a WOL(F) program.
     */
    public void parse(String filename) {
        lexer = new WolfLexing(filename);
        token = nextValidToken();
        try {
            writer = new FileWriter(new File(filename + ".parse"));
        } catch (IOException e) {
            System.err.println("Could not create log file, writing to stdout");
        }
        ast = Program();
        try {
            BuildSymbolTable bst = new BuildSymbolTable();
            bst.visit(ast);

            SemanticTypeCheck stc = new SemanticTypeCheck(bst.getTables(),
                bst.getProgramTable(),
                bst.getLambdaTables()
            );

            stc.visit(ast);
/*
            Optimizer optimizer = new Optimizer();
            Equal equal = new Equal();
            Program op_ast = ast;
            while(true) {
                ast = op_ast;
                op_ast = optimizer.visit(op_ast);
                if(!equal.visit(op_ast,ast)) { // change (optimization) occured
                    continue;
                }
                else {  // no change, fully optimized.
                    break;
                }
            }
            System.out.println("Optimized!");
            System.out.println(op_ast);
*/           
            WolfCompiler compiler = new WolfCompiler(stc, filename);
            compiler.compile(ast);

        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }
        
        try {
            writer.close();
        } catch (IOException ex) {
            // writer already closed
        }
    }

    /**
     * Parse an ArgList
     *
     * @return abstract syntax tree for ArgList
     */
    private Args ArgList() {
        List<Arg> arg_list = new ArrayList<>();
        eat(TLParen.class);
        if (token instanceof TRParen) {
            eat(TRParen.class);
        } else if (isArg()) {
            arg_list = Args();
            eat(TRParen.class);
        } else {
            error();
        }
        log("ArgList");
        return new ArgsList(arg_list);
    }

    /**
     * Parse an Arg.
     *
     * @return abstract syntax tree for Arg
     */
    private Arg Arg() {
      Arg arg = null;
        // The identifier could be a literal or a function call
        if (token instanceof TIdentifier) {
            Identifier identifier = new Identifier(
                (TIdentifier) eat(TIdentifier.class)
            );
            // If it starts with a left parenthesis, it's a function call,
            // not a literal (e.g. float, string, int)
            if (token instanceof TLParen) {
                Args arg_list = ArgList();
                arg = new UserFunc(identifier, arg_list);
            } else {
                arg = identifier;
            }
        } else if (token instanceof TIntNumber) {
            arg = new IntLiteral((TIntNumber) eat(TIntNumber.class));
        } else if (token instanceof TFloatNumber) {
            arg = new FloatLiteral((TFloatNumber) eat(TFloatNumber.class));
        } else if (token instanceof TStringStart) {
            arg = String();
        } else if (token instanceof TLambdaStart) {
            arg = new UserFunc(Lambda(), ArgList());
        } else if (isFunction()) {
            arg = Function();
        } else if (token instanceof TStartList) {
          arg = List();
        } else if (token instanceof TInputStart) {
          arg = InputArg();
        }else {
          error();
        }
      log("Arg");
      return arg;
    }

    /**
     * Parse an ArgRest.
     *
     * @return abstract syntax tree for ArgRest
     */
    private Arg ArgRest() {
        eat(TComma.class);
        Arg arg = Arg();
        log("ArgRest");
        return arg;
    }

    /**
     * Parse Args.
     *
     * @return abstract syntax tree for Args
     */
    private List<Arg> Args() {
        List<Arg> arg_list = new ArrayList<>();
        arg_list.add(Arg());
        while (token instanceof TComma) {
            arg_list.add(ArgRest());
        }
        log("Args");
        return arg_list;
    }

    /**
     * Parse a BinOp.
     *
     * @return abstract syntax tree for BinOp
     */
    private BinOp BinOp() {
        if (isNativeBinOp()) {
            return NativeBinOp();
        } else if (token instanceof TIdentifier) {
            return new Identifier((TIdentifier) eat(TIdentifier.class));
        } else if (token instanceof TLambdaStart) {
            return Lambda();
        } else {
            error();
        }
        return null;
    }

    /**
     * Parse a Branch.
     *
     * @return abstract syntax tree for Branch
     */
    private Branch Branch() {
        eat(TTernarySemi.class);
        WolfFunction cond = Function();
        eat(TTernaryQuestionMark.class);
        WolfFunction true_func = Function();
        eat(TTernaryColon.class);
        WolfFunction false_func = Function();
        log("Branch");
        return new Branch(cond, true_func, false_func);
    }

    /**
     * Parse a Def.
     *
     * @return abstract syntax tree for Def
     */
    private Def Def() {
        eat(TDef.class);
        Type type = Type();
        Identifier identifier = new Identifier(
            (TIdentifier) eat(TIdentifier.class)
        );
        Sig sig = Sig();
        eat(TAssign.class);
        WolfFunction function = Function();
        log("Def");
        return new Def(type, identifier, sig, function);
    }

    /**
     * Parse a string escape sequence in the format: \<escape_chars>
     *
     * @return abstract syntax tree for Escape
     */
    private EscapeChar Escape() {
        eat(TStringEscape.class);
        switch (getTokenName()) {
            case "TEscapeAlarm":
                eat(TEscapeAlarm.class);
                return EscapeChar.ESCAPE_ALARM;
            case "TEscapeBackslash":
                eat(TEscapeBackslash.class);
                return EscapeChar.ESCAPE_BACKSLASH;
            case "TEscapeBackspace":
                eat(TEscapeBackspace.class);
                return EscapeChar.ESCAPE_BACKSPACE;
            case "TEscapeCarriageReturn":
                eat(TEscapeCarriageReturn.class);
                return EscapeChar.ESCAPE_CARRIAGE_RETURN;
            case "TEscapeDefault":
                eat(TEscapeDefault.class);
                return EscapeChar.ESCAPE_DEFAULT;
            case "TEscapeDoubleQuote":
                eat(TEscapeDoubleQuote.class);
                return EscapeChar.ESCAPE_DOUBLE_QUOTE;
            case "TEscapeFormfeed":
                eat(TEscapeFormfeed.class);
                return EscapeChar.ESCAPE_FORMFEED;
            case "TEscapeHexChar":
                eat(TEscapeHexChar.class);
                return EscapeChar.ESCAPE_HEX_CHAR;
            case "TEscapeNewline":
                eat(TEscapeNewline.class);
                return EscapeChar.ESCAPE_NEWLINE;
            case "TEscapeOctalChar":
                eat(TEscapeOctalChar.class);
                return EscapeChar.ESCAPE_OCTAL_CHAR;
            case "TEscapeQuestionMark":
                eat(TEscapeQuestionMark.class);
                return EscapeChar.ESCAPE_QUESTION_MARK;
            case "TEscapeSingleQuote":
                eat(TEscapeSingleQuote.class);
                return EscapeChar.ESCAPE_SINGLE_QUOTE;
            case "TEscapeTab":
                eat(TEscapeTab.class);
                return EscapeChar.ESCAPE_TAB;
            case "TEscapeUnicodeChar":
                eat(TEscapeUnicodeChar.class);
                return EscapeChar.ESCAPE_UNICODE_CHAR;
            case "TEscapeVerticalTab":
                eat(TEscapeVerticalTab.class);
                return EscapeChar.ESCAPE_VERTICAL_TAB;
            default:
                error();
        }
        log("Escape sequence");
        return null;
    }

    /**
     * Parse a FoldBody.
     *
     * @return abstract syntax tree for FoldBody
     */
    private FoldBody FoldBody() {
        eat(TLParen.class);
        BinOp bin_op = BinOp();
        eat(TComma.class);
        ListArgument list_argument = ListArgument();
        eat(TRParen.class);
        log("FoldBody");
        return new FoldBody(bin_op, list_argument);
    }

    /**
     * Parse a Function.
     *
     * @return abstract syntax tree for a WolfFunction
     */
    private WolfFunction Function() {
        if (isUserFunc()) {
            return new UserFunc(UserFunc(), ArgList());
        } else if (isNativeUnaryOp()) {
            NativeUnaryOp native_unary_op = NativeUnaryOp();
            eat(TLParen.class);
            Arg arg = Arg();
            eat(TRParen.class);
            log("NativeUnary");
            return new NativeUnary(native_unary_op, arg);
        } else if (isNativeBinOp()) {
            NativeBinOp native_bin_op = NativeBinOp();
            eat(TLParen.class);
            Arg arg_left = Arg();
            eat(TComma.class);
            Arg arg_right = Arg();
            eat(TRParen.class);
            log("NativeBinary");
            return new NativeBinary(native_bin_op, arg_left, arg_right);
        } else if (isNativeListBinaryOp()) {
            NativeListBinaryOp native_list_binary_op = NativeListBinaryOp();
            eat(TLParen.class);
            Arg arg = Arg();
            eat(TComma.class);
            ListArgument list_argument = ListArgument();
            eat(TRParen.class);
            log("NativeListBinary");
            return new NativeListBinary(
                native_list_binary_op, arg, list_argument
            );
        } else if (isNativeListUnaryOp()) {
            NativeListUnaryOp native_list_unary_op = NativeListUnaryOp();
            eat(TLParen.class);
            ListArgument list_argument = ListArgument();
            eat(TRParen.class);
            log("NativeListUnary");
            return new NativeListUnary(native_list_unary_op, list_argument);
        } else if (token instanceof TTernarySemi) {
            return Branch();
        } else if (token instanceof TFoldl) {
            eat(TFoldl.class);
            log("Foldl");
            return new Fold(FoldSymbol.FOLD_LEFT, FoldBody());
        } else if (token instanceof TFoldr) {
            eat(TFoldr.class);
            log("Foldr");
            return new Fold(FoldSymbol.FOLD_RIGHT, FoldBody());
        } else if (token instanceof TMap) {
            return Map();
        } else {
            error();
        }
        return null;
    }

    /**
     * Parse an InputArg.
     * @return the abstract syntax tree representation of the InputArg
     */
    private InputArg InputArg() {
        Type type = null;
        TInputArgNumber arg_number = null;
        eat(TInputStart.class);
        if (isInputType()) {
            type = InputType();
        } else {
            error();
        }
        if (token instanceof TInputArgNumber) {
            arg_number = (TInputArgNumber) eat(TInputArgNumber.class);
        } else {
            error();
        }
        eat(TInputEnd.class);
        log("InputArg");
        return new InputArg(type, arg_number);
    }
    
    /**
     * Parse an InputType.
     * @return the abstract syntax tree representation of the InputArg's type
     */
    private Type InputType() {
        switch(getTokenName()) {
            case "TInputInt":
                eat(TInputInt.class);
                return new Type(FlatType.INTEGER);
            case "TInputFloat":
                eat(TInputFloat.class);
                return new Type(FlatType.FLOAT);
            case "TInputString":
                eat(TInputString.class);
                return new Type(FlatType.STRING);
            default:
                error();
        }
        return null;
    }
    /**
     * Parse a Lambda.
     *
     * @return abstract syntax tree for Lambda
     */
    private WolfLambda Lambda() {
        eat(TLambdaStart.class);
        eat(TLParen.class);
        Sig sig = Sig();
        eat(TLambdaArrow.class);
        WolfFunction function = Function();
        eat(TRParen.class);
        log("Lambda");
        return new WolfLambda(sig, function);
    }

    /**
     * Parse a List.
     *
     * @return abstract syntax tree for List
     */
    private WolfList List() {
        eat(TStartList.class);
        List<ListElement> list_elements = ListElements();
        eat(TEndList.class);
        log("List");
        return new WolfList(list_elements);
    }

    /**
     * Parse a ListArgument
     *
     * @return abstract syntax tree for ListArgument
     */
    private ListArgument ListArgument() {
        if (token instanceof TStartList) {
            return List();
        } else if (token instanceof TIdentifier) {
            Identifier identifier = new Identifier(
                (TIdentifier) eat(TIdentifier.class)
            );
            // If it starts with a left parenthesis, it's a function call,
            // not a literal (e.g. float, string, int)
            if (token instanceof TLParen) {
                Args arg_list = ArgList();
                return new UserFunc(identifier, arg_list);
            } else {
                return identifier;
            }
        } else if (isFunction()) {
            return Function();
        } else {
            error();
        }
        return null;
    }

    /**
     * Parse a ListElement.
     *
     * @return abstract syntax tree for ListElement
     */
    private ListElement ListElement() {
        ListElement list_element = null;
        // The identifier could be a literal or a function call
        if (token instanceof TIdentifier) {
            Identifier identifier = new Identifier(
                (TIdentifier) eat(TIdentifier.class)
            );
            // If it starts with a left parenthesis, it's a function call,
            // not a literal (e.g. float, string, int)
            if (token instanceof TLParen) {
                Args arg_list = ArgList();
                list_element = new UserFunc(identifier, arg_list);
            } else {
                list_element = identifier;
            }
        } else if (token instanceof TIntNumber) {
            list_element = new IntLiteral((TIntNumber) eat(TIntNumber.class));
        } else if (token instanceof TFloatNumber) {
            list_element = new FloatLiteral((TFloatNumber) eat(TFloatNumber.class));
        } else if (token instanceof TStringStart) {
            list_element = String();
        } else if (token instanceof TLambdaStart) {
            list_element = new UserFunc(Lambda(), ArgList());
        } else if (isFunction()) {
            list_element = Function();
        } else if (token instanceof TInputStart) {
            list_element = InputArg();
        } else {
            error();
        }
        log("ListElement");
        return list_element;
    }

    /**
     * Parse a ListElementRest.
     *
     * @return abstract syntax tree for ListElementRest
     */
    private ListElement ListElementRest() {
        eat(TComma.class);
        ListElement list_element = ListElement();
        log("ListElement");
        return list_element;
    }

    /**
     * Parse ListElements.
     *
     * @return abstract syntax tree for Args
     */
    private List<ListElement> ListElements() {
        List<ListElement> list_elements = new ArrayList<>();
        list_elements.add(ListElement());
        while (token instanceof TComma) {
            list_elements.add(ListElementRest());
        }
        log("Args");
        return list_elements;
    }
    /**
     * Parse a Map
     *
     * @return abstract syntax tree for Map
     */
    private WolfMap Map() {
        eat(TMap.class);
        eat(TLParen.class);
        UnaryOp unary_op = UnaryOp();
        eat(TComma.class);
        ListArgument list_argument = ListArgument();
        eat(TRParen.class);
        log("Map");
        return new WolfMap(unary_op, list_argument);
    }

    /**
     * Parse a NativeBinOp
     *
     * @return abstract syntax tree for NativeBinOp
     */
    private NativeBinOp NativeBinOp() {
        switch (getTokenName()) {
            case "TPlus":
                eat(TPlus.class);
                return NativeBinOp.PLUS;
            case "TMinus":
                eat(TMinus.class);
                return NativeBinOp.MINUS;
            case "TMult":
                eat(TMult.class);
                return NativeBinOp.MULT;
            case "TDiv":
                eat(TDiv.class);
                return NativeBinOp.DIV;
            case "TMod":
                eat(TMod.class);
                return NativeBinOp.MOD;
            case "TLt":
                eat(TLt.class);
                return NativeBinOp.LT;
            case "TGt":
                eat(TGt.class);
                return NativeBinOp.GT;
            case "TLte":
                eat(TLte.class);
                return NativeBinOp.LTE;
            case "TGte":
                eat(TGte.class);
                return NativeBinOp.GTE;
            case "TEqual":
                eat(TEqual.class);
                return NativeBinOp.EQUAL;
            case "TNotEqual":
                eat(TNotEqual.class);
                return NativeBinOp.NOT_EQUAL;
            case "TAnd":
                eat(TAnd.class);
                return NativeBinOp.AND;
            case "TOr":
                eat(TOr.class);
                return NativeBinOp.OR;
            case "TXor":
                eat(TXor.class);
                return NativeBinOp.XOR;
            default:
                error();
        }
        return null;
    }

    /**
     * Parse a NativeListBinaryOp
     *
     * @return abstract syntax tree for NativeListBinaryOp
     */
    private NativeListBinaryOp NativeListBinaryOp() {
        switch (getTokenName()) {
            case "TPrepend":
                eat(TPrepend.class);
                return NativeListBinaryOp.PREPEND;
            case "TAppend":
                eat(TAppend.class);
                return NativeListBinaryOp.APPEND;
            default:
                error();
        }
        return null;
    }

    /**
     * Parse a NativeListUnaryOp
     *
     * @return abstract syntax tree for NativeListUnaryOp
     */
    private NativeListUnaryOp NativeListUnaryOp() {
        switch (getTokenName()) {
            case "THead":
                eat(THead.class);
                return NativeListUnaryOp.HEAD;
            case "TTail":
                eat(TTail.class);
                return NativeListUnaryOp.TAIL;
            case "TReverse":
                eat(TReverse.class);
                return NativeListUnaryOp.REVERSE;
            case "TLast":
                eat(TLast.class);
                return NativeListUnaryOp.LAST;
            case "TLength":
                eat(TLength.class);
                return NativeListUnaryOp.LENGTH;
            default:
                error();
        }
        return null;
    }

    /**
     * Parse a NativeUnaryOp.
     *
     * @return abstract syntax tree for NativeUnaryOp
     */
    private NativeUnaryOp NativeUnaryOp() {
        switch (getTokenName()) {
            case "TNeg":
                eat(TNeg.class);
                return NativeUnaryOp.NEG;
            case "TLogicalNot":
                eat(TLogicalNot.class);
                return NativeUnaryOp.LOGICAL_NOT;
            case "TIdentity":
                eat(TIdentity.class);
                return NativeUnaryOp.IDENTITY;
            case "TPrint":
                eat(TPrint.class);
                return NativeUnaryOp.PRINT;
            default:
                error();
                return null;
        }
    }

    /**
     * Parse a Program. This is the starting rule for the WOL(F) grammar.
     * @return abstract syntax tree for the whole Program
     */
    private Program Program() {
        List<Def> def_list = new ArrayList<>();
        WolfFunction function = null;
        while (token instanceof TDef) {
            def_list.add(Def());
        }
        if (isFunction()) {
            function = Function();
        } else {
            error();
        }
        log("Program");
        return new Program(def_list, function);
    }

    /**
     * Parse a Sig.
     *
     * @return abstract syntax tree for Sig
     */
    private Sig Sig() {
        Sig sig = null;
        eat(TLParen.class);
        if (isType()) {
            sig = new Sig(SigArgs());
        }
        eat(TRParen.class);
        log("Sig");
        return sig;
    }

    /**
     * Parse a SigArgRest.
     *
     * @return abstract syntax tree for SigArgRest.
     */
    private SigArg SigArgRest() {
        eat(TComma.class);
        Type type = Type();
        Identifier id = new Identifier((TIdentifier) eat(TIdentifier.class));
        log("SigArgRest");
        return new SigArg(type, id);
    }

    /**
     * Parse SigArg
     * @return abstract syntax tree for SigArg
     */
    private SigArg SigArg() {
        Type type = Type();
        return new SigArg(
            type,
            new Identifier((TIdentifier)eat(TIdentifier.class))
        );
    }
    
    /**
     * Parse SigArgs.
     *
     * @return abstract syntax tree for SigArgs, a List of SigArg
     */
    private List<SigArg> SigArgs() {
        List<SigArg> sig_args = new ArrayList<>();
        sig_args.add(SigArg());
        while (token instanceof TComma) {
            sig_args.add(SigArgRest());
        }
        log("SigArgs");
        return sig_args;
    }

    /**
     * Parse a string literal, which may include escape sequences.
     *
     * @return abstract syntax tree for WolfString
     */
    private WolfString String() {
        List<StringMiddle> string = new ArrayList<>();
        eat(TStringStart.class);
        while (!(token instanceof TStringEnd)) {
            if (token instanceof TStringEscape) {
                string.add(Escape());
            } else if (token instanceof TStringBody) {
                string.add(
                    new StringBody((TStringBody) eat(TStringBody.class))
                );
            } else {
                error();
            }
        }
        eat(TStringEnd.class);
        log("String");
        return new WolfString(string);
    }

    /**
     * Parse a Type
     * @return a Type
     */
    private Type Type() {
        boolean is_list = false;
        if (token instanceof TListType) {
            eat(TListType.class);
            is_list = true;
        }
        switch(getTokenName()) {
            case "TIntType":
                eat(TIntType.class);
                return new Type(FlatType.INTEGER, is_list);
            case "TFloatType":
                eat(TFloatType.class);
                return new Type(FlatType.FLOAT, is_list);
            case "TStringType":
                eat(TStringType.class);
                return new Type(FlatType.STRING, is_list);
            default:
                error();
        }
        return null;
    }
            
    /**
     * Parse a UnaryOp
     *
     * @return abstract syntax tree for UnaryOp
     */
    private UnaryOp UnaryOp() {
        if (isNativeUnaryOp()) {
            return NativeUnaryOp();
        } else if (token instanceof TIdentifier) {
            return new Identifier((TIdentifier) eat(TIdentifier.class));
        } else if (token instanceof TLambdaStart) {
            return Lambda();
        }
        return null;
    }

    /**
     * Parse a UserFunc.
     *
     * @return abstract syntax tree for UserFunc
     */
    private UserFuncName UserFunc() {
        UserFuncName user_func_name = null;
        if (token instanceof TIdentifier) {
            user_func_name = new Identifier(
                (TIdentifier) eat(TIdentifier.class)
            );
        } else if (token instanceof TLambdaStart) {
            user_func_name = Lambda();
        } else {
            error();
        }
        log("UserFunc");
        return user_func_name;
    }

    // Helper functions
    /**
     * Eats a token based on its class. If the current token's class doesn't
     * match the given class, then an error is thrown.
     *
     * @param klass is the class of the token to be eaten.
     * @return the parsed token
     */
    private Token eat(Class klass) {
        Token parsed_token = token;
        if (token.getClass().getName().equals(klass.getName())) {
            parsed.add(token.getText());
            token = nextValidToken();
        } else {
            error();
        }
        return parsed_token;
    }

    /**
     * Throw an IllegalArgumentException because the current token does not
     * match the expected token. Log the type of token, its position, and the
     * token itself.
     */
    private void error() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Unexpected token type: ")
                .append(token.getClass().getName())
                .append(" around (line ").append(line_count)
                .append(", column ").append(token.getPos()).append("): ")
                .append(token.getText());
        if (!parsed.isEmpty()) {
            sb.append("\nContext: ");
            for (String token_string : parsed) {
                sb.append(token_string).append(" ");
            }
        }
        try {
            writer.append(sb.toString().trim());
            writer.close();
        } catch (IOException e) {
            // Do nothing; thrown error will send the message to output
        }
        throw new IllegalArgumentException(sb.toString().trim());
    }

    /**
     * Get the name of a token from the extended class name. Tokens are named
     * wolf.node.<token_class>. We want only the token_class for matching
     * purposes.
     *
     * @return the name of the token's class.
     */
    private String getTokenName() {
        String[] token_name_parts = token.getClass().getName().split("\\.");
        return token_name_parts[token_name_parts.length - 1];
    }

    /**
     * Decide if the current token indicates a Function.
     *
     * @return true if the current token is a token that can start a Function,
     * false otherwise.
     */
    private boolean isFunction() {
        return isUserFunc()
                || isNativeUnaryOp()
                || isNativeBinOp()
                || isNativeListBinaryOp()
                || isNativeListUnaryOp()
                || token instanceof TTernarySemi
                || token instanceof TFoldl
                || token instanceof TFoldr
                || token instanceof TMap;
    }

    /**
     * Decide if the current token indicates a NativeBinOp.
     *
     * @return true if the current token is a token that can start a
     * NativeBinOp, false otherwise.
     */
    private boolean isNativeBinOp() {
        return token instanceof TPlus
                || token instanceof TMinus
                || token instanceof TMult
                || token instanceof TDiv
                || token instanceof TMod
                || token instanceof TLt
                || token instanceof TLte
                || token instanceof TGt
                || token instanceof TGte
                || token instanceof TEqual
                || token instanceof TNotEqual
                || token instanceof TOr
                || token instanceof TAnd
                || token instanceof TXor;
    }

    /**
     * @return true if the token corresponds to a binary function that operates
     * on lists, false otherwise.
     */
    private boolean isNativeListBinaryOp() {
        return token instanceof TPrepend || token instanceof TAppend;
    }

    /**
     * @return true if the token corresponds to a unary function that operates
     * on lists, false otherwise.
     */
    private boolean isNativeListUnaryOp() {
        return token instanceof TLength
                || token instanceof THead
                || token instanceof TTail
                || token instanceof TReverse
                || token instanceof TLast;
    }

    /**
     * Decide if the current token indicates a NativeUnaryOp.
     *
     * @return true if the current token is a token that can start a
     * NativeUnaryOp, false otherwise.
     */
    private boolean isNativeUnaryOp() {
        return token instanceof TIdentity
                || token instanceof TPrint
                || token instanceof TNeg
                || token instanceof TLogicalNot;
    }

    /**
     * Decide if the current token indicates a UserFunc.
     *
     * @return true if the current token is a token that can start a UserFunc,
     * false otherwise.
     */
    private boolean isUserFunc() {
        return token instanceof TIdentifier
                || token instanceof TLambdaStart;
    }

    /**
     * Decide if the current token indicates an Arg.
     *
     * @return true if the current token is a token that can start an Arg, false
     * otherwise.
     */
    private boolean isArg() {
        return token instanceof TIdentifier
                || token instanceof TIntNumber
                || token instanceof TFloatNumber
                || token instanceof TStringStart
                || token instanceof TStartList
                || token instanceof TLambdaStart
                || isFunction()
                || token instanceof TInputStart;
    }

    /**
     * Decide if the current token indicates a ListElement.
     *
     * @return true if the current token is a token that can start a
     * ListElement, false otherwise.
     */
    private boolean isListElement() {
      return token instanceof TIdentifier
                || token instanceof TIntNumber
                || token instanceof TFloatNumber
                || token instanceof TStringStart
                || token instanceof TLambdaStart
                || isFunction()
                || token instanceof TInputStart;
    }

    /**
     * @return true if the token corresponds to a type, false otherwise.
     */
    private boolean isType() {
        return token instanceof TIntType
                || token instanceof TFloatType
                || token instanceof TStringType
                || token instanceof TListType;
    }
    
        /**
     * @return true if the token corresponds to a type, false otherwise.
     */
    private boolean isInputType() {
        return token instanceof TInputInt
                || token instanceof TInputFloat
                || token instanceof TInputString;
    }
    
    /**
     * Log information about the last nonterminal parsed
     *
     * @param nonterminal_name the name of the nonterminal parsed
     */
    private void log(String nonterminal_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(nonterminal_name).append(" parsed successfully: ");
        for (String parsed_token_strings : parsed) {
            sb.append(parsed_token_strings).append(" ");
        }
        sb.append("\n");
        if (writer != null) {
            try {
                writer.append(sb.toString());
            } catch (IOException e) {
                System.out.println(sb.toString());
            }
        }
    }

    /**
     * Get the next valid token, skipping comments and whitespace.
     *
     * @return the next non-ignored token.
     */
    private Token nextValidToken() {
        token = lexer.getToken();
        while (token instanceof TSpace ||
               token instanceof TNewline ||
               token instanceof TComment) {
            /* If a comment spans multiple lines, it will hide newlines.
             * Add the number of newlines in the comment to the count.
             */
            if (token instanceof TComment) {
                line_count += token.getText().split("\n").length - 1;
            } // Add the number of newlines in the program to the count.
            else if (token instanceof TNewline) {
                line_count++;
            }
            token = lexer.getToken();
        }
        line_count += token.getText().split("\n").length - 1;
        return token;
    }

    /**
     * Driver to test parser.
     *
     * @param args the names of all files to parse.
     */
    public static void main(String args[]) {
        if (args.length == 0) {
            new Parser().parse();
        } else {
            for (String filename : args) {
                System.out.println("Parsing " + filename);
                // Wrap parse in try catch so that it will continue parsing
                // the rest of the files.
                try {
                    new Parser().parse(filename);
                } catch (IllegalArgumentException iae) {
                    // Make it easier to catch error output.
                    System.out.println(iae.getMessage());
                }
            }
        }
    }
}
