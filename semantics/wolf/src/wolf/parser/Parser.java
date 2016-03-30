package wolf.parser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import wolf.*;
import wolf.node.*;
/**
 * A recursive descent Parser for the WOL(F) programming language.
 * Each non-helper function of the parser represents a non-terminal in the
 * grammar.  The rules for that non-terminal are in the header documentation
 * for its corresponding function.
 * 
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joe Alacqua
 * @version Mar 1, 2016
 */
public class Parser 
{
    private WolfLexing lexer;
    private Token token;
    private FileWriter writer;
    private List<String> program_parsed;
    private int line_count;
    /**
     * Empty constructor
     */
    public Parser()
    {
        program_parsed = new ArrayList<>();
        line_count = 1;
    }
    
    /**
     * Parse a WOL(F) program from stdin.
     */
    public void parse()
    {
        lexer = new WolfLexing();
        token = nextValidToken();
        try {
            writer = new FileWriter(new File("out.parse"));
        } catch (IOException e) {
            System.err.println("Could not create log file, writing to stdout");
        }
        Program();
    }
    
    /**
     * Parse a WOL(F) program from a file.
     * @param filename is the name of the file to parse as a WOL(F) program.
     */
    public void parse(String filename)
    {
        lexer = new WolfLexing(filename);
        token = nextValidToken();
        try {
            writer = new FileWriter(new File(filename + ".parse"));
        } catch (IOException e) {
            System.err.println("Could not create log file, writing to stdout");
        }
        Program();
        try 
        {
            writer.close();
        } 
        catch (IOException ex)
        {
            // writer already closed
        }
    }
    
    /**
     * Parse an ArgList
     * ArgList = ()
     *         = (Args) 
     */
    private List<String> ArgList()
    {
        List<String> parsed = new ArrayList<>();
        eat(TLParen.class, parsed);
        if (token instanceof TRParen)
        {
            eat(TRParen.class, parsed);
        }
        else if (isArg())
        {
            parsed.addAll(Args());
            eat(TRParen.class, parsed);
        }
        else
        {
            error();
        }
        log("ArgList", parsed);
        return parsed;
    }
    
    /**
     * Parse an Arg.
     * Arg = Function
     = Lambda
     = List
     = float_literal
     = string_literal
     = int_literal
     = id
     */
    private List<String> Arg()
    {
        List<String> parsed = new ArrayList<>();
        // The identifier could be a literal or a function call
        if (token instanceof TIdentifier)
        {
            eat(TIdentifier.class, parsed);
            // If it starts with a left parenthesis, it's a function call,
            // not a literal (e.g. float, string, int)
            if (token instanceof TLParen)
            {
                parsed.addAll(ArgList());
            }
        }
        else if (token instanceof TIntNumber)
        {
            eat(TIntNumber.class, parsed);
        }
        else if (token instanceof TFloatNumber)
        {
            eat(TFloatNumber.class, parsed);
        }
        else if (token instanceof TStringStart)
        {
            parsed.addAll(String());
        }
        else if (token instanceof TStartList)
        {
            parsed.addAll(List());
        }
        else if (token instanceof TLambdaStart)
        {
            parsed.addAll(Lambda());
        }
        else if (isFunction())
        {
            parsed.addAll(Function());
        }
        else
        {
            error();
        }
        log("Arg", parsed);
        return parsed;
    }
    
    /**
     * Parse an ArgRest.
     * ArgRest = , Arg
     */
    private List<String> ArgRest()
    {
        List<String> parsed = new ArrayList<>();
        eat(TComma.class, parsed);
        parsed.addAll(Arg());
        log("ArgRest", parsed);
        return parsed;
    }
    
    /**
     * Parse Args.
     * Args = Arg ArgRest*
     */
    private List<String> Args()
    {
        List<String> parsed = new ArrayList<>();
        parsed.addAll(Arg());
        while (token instanceof TComma)
        {
            parsed.addAll(ArgRest());
        }
        log("Args", parsed);
        return parsed;
    }
    
    private List<String> BinOp()
    {
        List<String> parsed = new ArrayList<>();
        if (isNativeBinOp())
        {
            parsed.addAll(NativeBinOp());
        }
        else if (token instanceof TIdentifier)
        {
            eat(TIdentifier.class, parsed);
        }
        else if (token instanceof TLambdaStart)
        {
            parsed.addAll(Lambda());
        }
        else
        {
            error();
        }
        log("BinOp", parsed);
        return parsed;
    }
    
    /**
     * Parse a Branch.
     * Branch = ; If : Else
     */
    private List<String> Branch()
    {
        List<String> parsed = new ArrayList<>();
        eat(TTernarySemi.class, parsed);
        parsed.addAll(Function());
        eat(TTernaryQuestionMark.class, parsed);
        parsed.addAll(Function());
        eat(TTernaryColon.class, parsed);
        parsed.addAll(Function());
        log("Branch", parsed);
        return parsed;
    }
    
    /**
     * Parse a Def.
     * Def = def id Sig := Function
     */
    private List<String> Def()
    {
        List<String> parsed = new ArrayList<>();
        eat(TDef.class, parsed);
        eat(TIdentifier.class, parsed);
        parsed.addAll(Sig());
        eat(TAssign.class, parsed);
        parsed.addAll(Function());
        log("Def", parsed);
        return parsed;
    }
    
    /**
     * Parse a string escape sequence in the format:
     * \<escape_chars>
     */
    private List<String> Escape()
    {
        List<String> parsed = new ArrayList<>();
        eat(TStringEscape.class, parsed);
        switch(getTokenName())
        {
            case "TEscapeAlarm":
                eat(TEscapeAlarm.class, parsed);
                break;
            case "TEscapeBackslash":
                eat(TEscapeBackslash.class, parsed);
                break;
            case "TEscapeBackspace":
                eat(TEscapeBackspace.class, parsed);
                break;
            case "TEscapeCarriageReturn":
                eat(TEscapeCarriageReturn.class, parsed);
                break;
            case "TEscapeDefault":
                eat(TEscapeDefault.class, parsed);
                break;
            case "TEscapeDoubleQuote":
                eat(TEscapeDoubleQuote.class, parsed);
                break;
            case "TEscapeFormfeed":
                eat(TEscapeFormfeed.class, parsed);
                break;
            case "TEscapeHexChar":
                eat(TEscapeHexChar.class, parsed);
                break;
            case "TEscapeNewline":
                eat(TEscapeNewline.class, parsed);
                break;
            case "TEscapeOctalChar":
                eat(TEscapeOctalChar.class, parsed);
                break;
            case "TEscapeQuestionMark":
                eat(TEscapeQuestionMark.class, parsed);
                break;
            case "TEscapeSingleQuote":
                eat(TEscapeSingleQuote.class, parsed);
                break;
            case "TEscapeTab":
                eat(TEscapeTab.class, parsed);
                break;
            case "TEscapeUnicodeChar":
                eat(TEscapeUnicodeChar.class, parsed);
                break;
            case "TEscapeVerticalTab":
                eat(TEscapeVerticalTab.class, parsed);
                break;
            default:
                error();
        }
        log("Escape sequence", parsed);
        return parsed;
    }
    
    /**
     * Parse a FoldBody.
     * FoldBody = ( FuncName, FoldArg)
     */
    private List<String> FoldBody()
    {
        List<String> parsed = new ArrayList<>();
        eat(TLParen.class, parsed);
        parsed.addAll(BinOp());
        eat(TComma.class, parsed);
        parsed.addAll(ListArgument());
        eat(TRParen.class, parsed);
        log("FoldBody", parsed);
        return parsed;
    }
    
    /**
     * Parse a Function.
     * Function = FuncName ArgList
      = Branch
      = Foldl
      = Foldr
     */
    private List<String> Function()
    {
        List<String> parsed = new ArrayList<>();
        if (token instanceof TIdentifier ||
            token instanceof TLambdaStart)
        {
            parsed.addAll(UserFunc());
        }
        else if (isNativeUnaryOp())
        {
            parsed.addAll(NativeUnaryOp());
            eat(TLParen.class, parsed);
            parsed.addAll(Arg());
            eat(TRParen.class, parsed);
        }
        else if (isNativeBinOp())
        {
            parsed.addAll(NativeBinOp());
            eat(TLParen.class, parsed);
            parsed.addAll(Arg());
            eat(TComma.class, parsed);
            parsed.addAll(Arg());
            eat(TRParen.class, parsed);
        }
        else if (token instanceof TTernarySemi)
        {
            parsed.addAll(Branch());
        }
        else if (token instanceof TFoldl)
        {
            eat(TFoldl.class, parsed);
            parsed.addAll(FoldBody());
        }
        else if (token instanceof TFoldr)
        {
            eat(TFoldr.class, parsed);
            parsed.addAll(FoldBody());
        }
        else if (token instanceof TMap)
        {
            parsed.addAll(Map());
        }
        else
        {
            error();
        }
        log("Function", parsed);
        return parsed;
    }
    
    /**
     * Parse a Lambda.
     * Lambda = \(ArgList -> Function)
     */
    private List<String> Lambda()
    {
        List<String> parsed = new ArrayList<>();
        eat(TLambdaStart.class, parsed);
        eat(TLParen.class, parsed);
        parsed.addAll(Sig());
        eat(TLambdaArrow.class, parsed);
        parsed.addAll(Function());
        eat(TRParen.class, parsed);
        log("Lambda", parsed);
        return parsed;
    }
    
    /**
     * Parse a List.
     * List = [ Args ]
     *      = []
     */
    private List<String> List()
    {
        List<String> parsed = new ArrayList<>();
        eat(TStartList.class, parsed);
        if (isArg())
        {
            parsed.addAll(Args());
            eat(TEndList.class, parsed);
        } 
        else if (token instanceof TEndList)
        {
            eat(TEndList.class, parsed);
        }
        else
        {
            error();
        }
        log("List", parsed);
        return parsed;
    }
    
    private List<String> ListArgument()
    {
        List<String> parsed = new ArrayList<>();
        if (token instanceof TStartList)
        {
            parsed.addAll(List());
        }
        else if (token instanceof TIdentifier)
        {
            eat(TIdentifier.class, parsed);
        }
        else if (isFunction())
        {
            parsed.addAll(Function());
        }
        else
        {
            error();
        }
        log("ListArgument", parsed);
        return parsed;
    }
    
    private List<String> Map()
    {
        List<String> parsed = new ArrayList<>();
        eat(TMap.class, parsed);
        eat(TLParen.class, parsed);
        parsed.addAll(UnaryOp());
        eat(TComma.class, parsed);
        parsed.addAll(ListArgument());
        eat(TRParen.class, parsed);
        log("Map", parsed);
        return parsed;
    }
    
    private List<String> NativeUnaryOp()
    {
        List<String> parsed = new ArrayList<>();
        switch(getTokenName())
        {
            case "TNeg":
                eat(TNeg.class, parsed);
                break;
            case "TLogicalNot":
                eat(TLogicalNot.class, parsed);
                break;
            case "THead":
                eat(THead.class, parsed);
                break;
            case "TTail":
                eat(TTail.class, parsed);
                break;
            case "TReverse":
                eat(TReverse.class, parsed);
                break;
            case "TFlatten":
                eat(TFlatten.class, parsed);
                break;
            case "TIdentity":
                eat(TIdentity.class, parsed);
                break;
            case "TPrint":
                eat(TPrint.class, parsed);
                break;
            case "TLength":
                eat(TLength.class, parsed);
                break;
            case "TMap":
                eat(TMap.class, parsed);
                break;
            default:
                error();
        }
        log("NativeUnaryOp", parsed);
        return parsed;
    }
    private List<String> NativeBinOp()
    {
        List<String> parsed = new ArrayList<>();
        switch(getTokenName())
        {
            case "TPlus":
                eat(TPlus.class, parsed);
                break;
            case "TMinus":
                eat(TMinus.class, parsed);
                break;
            case "TMult":
                eat(TMult.class, parsed);
                break;
            case "TDiv":
                eat(TDiv.class, parsed);
                break;
            case "TMod":
                eat(TMod.class, parsed);
                break;
            case "TLt":
                eat(TLt.class, parsed);
                break;
            case "TGt":
                eat(TGt.class, parsed);
                break;
            case "TLte":
                eat(TLte.class, parsed);
                break;
            case "TGte":
                eat(TGte.class, parsed);
                break;
            case "TEqual":
                eat(TEqual.class, parsed);
                break;
            case "TNotEqual":
                eat(TNotEqual.class, parsed);
                break;
            case "TAnd":
                eat(TAnd.class, parsed);
                break;
            case "TOr":
                eat(TOr.class, parsed);
                break;
            case "TXor":
                eat(TXor.class, parsed);
                break;
            case "TPrepend":
                eat(TPrepend.class, parsed);
                break;
            case "TAppend":
                eat(TAppend.class, parsed);
                break;   
            default:
                error();
        }
        log("NativeBinOp", parsed);
        return parsed;
    }
    
    /**
     * Parse a Program.
     * Program = Def* Function
     */
    private void Program()
    {
        while (token instanceof TDef)
        {
            program_parsed.addAll(Def());
        }
        if (isFunction())
        {
            program_parsed.addAll(Function());
        }
        else
        {
            error();
        }
        log("Program", program_parsed);
    }
    
    /**
     * Parse a Sig.
     * Sig = ()
     *     = (SigArgs)
     */
    private List<String> Sig()
    {
        List<String> parsed = new ArrayList<>();
        eat(TLParen.class, parsed);
        if (token instanceof TIdentifier) {
            parsed.addAll(SigArgs());
        }
        eat(TRParen.class, parsed);
        log("Sig", parsed);
        return parsed;
    }
    
    /**
     * Parse a SigArgRest.
     * SigArgRest = , id
     */
    private List<String> SigArgRest()
    {
        List<String> parsed = new ArrayList<>();
        eat(TComma.class, parsed);
        eat(TIdentifier.class, parsed);
        log("SigArgRest", parsed);
        return parsed;
    }
    
    /**
     * Parse SigArgs.
     * SigArgs = id SigArgRest*
     */
    private List<String> SigArgs()
    {
        List<String> parsed = new ArrayList<>();
        eat(TIdentifier.class, parsed);
        while (token instanceof TComma)
        {
            parsed.addAll(SigArgRest());
        }
        log("SigArgs", parsed);
        return parsed;
    }
    
    /**
     * Parse a string literal, which may include escape sequences.
     */
    private List<String> String()
    {
        List<String> parsed = new ArrayList<>();
        eat(TStringStart.class, parsed);
        while (!(token instanceof TStringEnd))
        {
            if (token instanceof TStringEscape)
            {
                parsed.addAll(Escape());
            }
            else if (token instanceof TStringBody)
            {
                eat(TStringBody.class, parsed);
            }
            else
            {
                error();
            }
        }
        eat(TStringEnd.class, parsed);
        log("String", parsed);
        return parsed;
    }
    
    private List<String> UnaryOp()
    {
        List<String> parsed = new ArrayList<>();
        if (isNativeUnaryOp())
        {
            parsed.addAll(NativeUnaryOp());
        }
        else if (token instanceof TIdentifier)
        {
            eat(TIdentifier.class, parsed);
        }
        else if (token instanceof TLambdaStart)
        {
            parsed.addAll(Lambda());
        }
        log("UnaryOp", parsed);
        return parsed;
    }
    
    private List<String> UserFunc()
    {
        List<String> parsed = new ArrayList<>();
        if (token instanceof TIdentifier)
        {
            eat(TIdentifier.class, parsed);
        }
        else if (token instanceof TLambdaStart)
        {
            parsed.addAll(Lambda());
        }
        else
        {
            error();
        }
        log("UserFunc", parsed);
        return parsed;
    }
    
    // Helper functions
    /**
     * Eats a token based on its class.  If the current token's class doesn't
     * match the given class, then an error is thrown.
     * @param klass is the class of the token to be eaten.
     */
    private void eat(Class klass, List<String> parsed)
    {
        if (token.getClass().getName().equals(klass.getName()))
        {
            parsed.add(token.getText());
            token = nextValidToken();
        }
        else
        {
            error();
        }
    }
    
    /**
     * Throw an IllegalArgumentException because the current token does
     * not match the expected token.  Log the type of token, its position,
     * and the token itself.
     */
    private void error()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected token type: ")
          .append(token.getClass().getName())
          .append("(line ").append(line_count)
          .append(", column").append(token.getPos()).append("): ")
          .append(token.getText());
        throw new IllegalArgumentException(sb.toString());
    }
    
    /**
     * Get the name of a token from the extended class name.  Tokens
     * are named wolf.node.<token_class>.  We want only the token_class for
     * matching purposes.
     * @return the name of the token's class.
     */
    private String getTokenName()
    {
        String[] token_name_parts = token.getClass().getName().split("\\.");
        return token_name_parts[token_name_parts.length - 1];
    }
    
    private boolean isBinOp()
    {
        return isUserFunc() || isNativeBinOp();
    }
    
    private boolean isUnaryOp()
    {
        return isUserFunc() || isNativeUnaryOp();
    }
    
    /**
     * Decide if the current token indicates a Function.
     * @return true if the current token is a token that can start a Function,
     * false otherwise.
     */
    private boolean isFunction()
    {
        return isUserFunc() ||
               isNativeUnaryOp() ||
               isNativeBinOp() ||
               token instanceof TTernarySemi ||
               token instanceof TFoldl ||
               token instanceof TFoldr ||
               token instanceof TMap;
    }
    
    private boolean isNativeBinOp()
    {
        return token instanceof TPrepend ||
               token instanceof TAppend ||
               token instanceof TPlus ||
               token instanceof TMinus ||
               token instanceof TMult ||
               token instanceof TDiv ||
               token instanceof TMod ||
               token instanceof TLt ||
               token instanceof TLte ||
               token instanceof TGt ||
               token instanceof TGte ||
               token instanceof TEqual ||
               token instanceof TNotEqual ||
               token instanceof TOr ||
               token instanceof TAnd ||
               token instanceof TXor;
    }
    
    private boolean isNativeUnaryOp()
    {
        return token instanceof THead ||
               token instanceof TTail ||
               token instanceof TReverse ||
               token instanceof TLength ||
               token instanceof TFlatten ||
               token instanceof TIdentity ||
               token instanceof TPrint ||
               token instanceof TNeg ||
               token instanceof TLogicalNot;
    }
    
    private boolean isUserFunc()
    {
        return token instanceof TIdentifier ||
               token instanceof TLambdaStart;
    }
    
    /**
     * Decide if the current token indicates a Argument.
     * @return true if the current token is a token that can start a Argument,
     * false otherwise.
     */
    private boolean isArg()
    {
        return token instanceof TIdentifier ||
               token instanceof TIntNumber ||
               token instanceof TFloatNumber ||
               token instanceof TStringStart ||
               token instanceof TStartList ||
               token instanceof TLambdaStart ||
               isFunction();
    }
    
    private void log(String func_name, List<String> parsed)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(func_name).append(" parsed successfully: ");
        for (String parsed_token_strings : parsed)
        {
            sb.append(parsed_token_strings).append(" ");
        }
        if (writer != null)
        {
            try 
            {
                writer.append(sb.toString());
            } 
            catch (IOException e) 
            {
                System.out.println(sb.toString());
            }
        }
    }
    
    /**
     * Get the next valid token, skipping comments and whitespace.
     * @return the next non-ignored token.
     */
    private Token nextValidToken()
    {
        token = lexer.getToken();
        while (token instanceof TSpace ||
               token instanceof TNewline || 
               token instanceof TComment)
        {
            if (token instanceof TNewline)
            {
                line_count++;
            }
            token = lexer.getToken();
        }
        return token;
    }
    
    /**
     * Driver to test parser.
     * @param args the names of all files to parse.
     */
    public static void main(String args[])
    {
        if (args.length == 0)
        {
            new Parser().parse();
        }
        else
        {
            for (String filename : args)
            {
                System.out.println("Parsing " + filename);
                // Wrap parse in try catch so that it will continue parsing
                // the rest of the files.
                try
                {
                    new Parser().parse(filename);
                }
                catch(IllegalArgumentException iae)
                {
                    // Make it easier to catch error output.
                    System.out.println(iae.getMessage());
                }
            }
        }
    }
}
