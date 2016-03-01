package wolf.parser;
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
    
    /**
     * Empty constructor
     */
    public Parser()
    {
        
    }
    
    /**
     * Parse a WOL(F) program from stdin.
     */
    public void parse()
    {
        lexer = new WolfLexing();
        token = nextValidToken();
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
        Program();
    }
    
    /**
     * Parse an ArgList
     * ArgList = ()
     *         = (Args) 
     */
    private void ArgList()
    {
        eat(TLParen.class);
        if (token instanceof TRParen)
        {
            eat(TRParen.class);
        }
        else if (isArg())
        {
            Args();
            eat(TRParen.class);
        }
        else
        {
            error();
        }
    }
    
    /**
     * Parse an Arg.
     * Arg = Func
     *     = Lambda
     *     = List
     *     = float_literal
     *     = string_literal
     *     = int_literal
     *     = id
     */
    private void Arg()
    {
        // The identifier could be a literal or a function call
        if (token instanceof TIdentifier)
        {
            eat(TIdentifier.class);
            // If it starts with a left parenthesis, it's a function call,
            // not a literal (e.g. float, string, int)
            if (token instanceof TLParen)
            {
                ArgList();
            }
        }
        else if (token instanceof TIntNumber)
        {
            eat(TIntNumber.class);
        }
        else if (token instanceof TFloatNumber)
        {
            eat(TFloatNumber.class);
        }
        else if (token instanceof TStringStart)
        {
            String();
        }
        else if (token instanceof TStartList)
        {
            List();
        }
        else if (token instanceof TLambdaStart)
        {
            Lambda();
        }
        else if (isFunc())
        {
            Func();
        }
        else
        {
            error();
        }
    }
    
    /**
     * Parse an ArgRest.
     * ArgRest = , Arg
     */
    private void ArgRest()
    {
        eat(TComma.class);
        Arg();
    }
    
    /**
     * Parse Args.
     * Args = Arg ArgRest*
     */
    private void Args()
    {
        Arg();
        while (token instanceof TComma)
        {
            ArgRest();
        }
    }
    
    /**
     * Parse a Branch.
     * Branch = ; If : Else
     */
    private void Branch()
    {
        eat(TTernarySemi.class);
        Func();
        eat(TTernaryQuestionMark.class);
        Func();
        eat(TTernaryColon.class);
        Func();
    }
    
    /**
     * Parse a Def.
     * Def = def id Sig := Func
     */
    private void Def()
    {
        eat(TDef.class);
        eat(TIdentifier.class);
        Sig();
        eat(TAssign.class);
        Func();
    }
    
    /**
     * Parse a string escape sequence in the format:
     * \<escape_chars>
     */
    private void Escape()
    {
        eat(TStringEscape.class);
        switch(getTokenName())
        {
            case "TEscapeAlarm":
                eat(TEscapeAlarm.class);
                break;
            case "TEscapeBackslash":
                eat(TEscapeBackslash.class);
                break;
            case "TEscapeBackspace":
                eat(TEscapeBackspace.class);
                break;
            case "TEscapeCarriageReturn":
                eat(TEscapeCarriageReturn.class);
                break;
            case "TEscapeDefault":
                eat(TEscapeDefault.class);
                break;
            case "TEscapeDoubleQuote":
                eat(TEscapeDoubleQuote.class);
                break;
            case "TEscapeFormfeed":
                eat(TEscapeFormfeed.class);
                break;
            case "TEscapeHexChar":
                eat(TEscapeHexChar.class);
                break;
            case "TEscapeNewline":
                eat(TEscapeNewline.class);
                break;
            case "TEscapeOctalChar":
                eat(TEscapeOctalChar.class);
                break;
            case "TEscapeQuestionMark":
                eat(TEscapeQuestionMark.class);
                break;
            case "TEscapeSingleQuote":
                eat(TEscapeSingleQuote.class);
                break;
            case "TEscapeTab":
                eat(TEscapeTab.class);
                break;
            case "TEscapeUnicodeChar":
                eat(TEscapeUnicodeChar.class);
                break;
            case "TEscapeVerticalTab":
                eat(TEscapeVerticalTab.class);
                break;
            default:
                error();
        }
    }
    
    /**
     * Parse a FoldBody.
     * FoldBody = ( FuncName, FoldArg)
     */
    private void FoldBody()
    {
        eat(TLParen.class);
        FuncName();
        eat(TComma.class);
        if (token instanceof TStartList)
        {
            List();
        }
        else if (token instanceof TIdentifier)
        {
            eat(TIdentifier.class);
        }
        else if (isFunc())
        {
            Func();
        }
        else
        {
            error();
        }
        eat(TRParen.class);
    }
    
    /**
     * Parse a Func.
     * Func = FuncName ArgList
     *      = Branch
     *      = Foldl
     *      = Foldr
     */
    private void Func()
    {
        if (token instanceof TTernarySemi)
        {
            Branch();
        }
        else if (token instanceof TFoldl)
        {
            eat(TFoldl.class);
            FoldBody();
        }
        else if (token instanceof TFoldr)
        {
            eat(TFoldr.class);
            FoldBody();
        }
        else if (isFuncName())
        {
            FuncName();
            ArgList();
        }
        else
        {
            error();
        }
    }
    
    /**
     * Parse a FuncName.
     * FuncName = id    user-defined function identifier
     *          = h     head - get the first element of a list
     *          = t     tail - get a list excluding first element of the list
     *          = r     reverse - reverse the input list and return it
     *          = ^     prepend - add an element to the start of the given list
     *          = $     append - add an element to the end of the given list
     *          = .     map - apply unary op to list elements to make new list
     *          = #     length - get size of list
     *          = _     flatten - turn list with nested lists into a flat list
     *          = @     identity - return the argument provided
     *          = print print - print the argument to stdout and return it
     *          = ~     unary operator for arithmetic negation; Ex: ~(5) = -5
     *          = !     unary operator for logical negation
     *          = op    binary operators like +, >=, =, |, and x
     */
    private void FuncName()
    {
        switch(getTokenName())
        {
            case "TIdentifier":
                eat(TIdentifier.class);
                break;
            case "THead":
                eat(THead.class);
                break;
            case "TTail":
                eat(TTail.class);
                break;
            case "TReverse":
                eat(TReverse.class);
                break;
            case "TPrepend":
                eat(TPrepend.class);
                break;
            case "TAppend":
                eat(TAppend.class);
                break;
            case "TMap":
                eat(TMap.class);
                break;
            case "TLength":
                eat(TLength.class);
                break;
            case "TFlatten":
                eat(TFlatten.class);
                break;
            case "TIdentity":
                eat(TIdentity.class);
                break;
            case "TPrint":
                eat(TPrint.class);
                break;
            case "TNeg":
                eat(TNeg.class);
                break;
            case "TEqual":
                eat(TEqual.class);
                break;
            case "TNotEqual":
                eat(TNotEqual.class);
                break;
            case "TGt":
                eat(TGt.class);
                break;
            case "TGte":
                eat(TGte.class);
                break;
            case "TLt":
                eat(TLt.class);
                break;
            case "TLte":
                eat(TLte.class);
                break;
            case "TPlus":
                eat(TPlus.class);
                break;
            case "TMinus":
                eat(TMinus.class);
                break;
            case "TMult":
                eat(TMult.class);
                break;
            case "TDiv":
                eat(TDiv.class);
                break;
            case "TMod":
                eat(TMod.class);
                break;
            case "TOr":
                eat(TOr.class);
                break;
            case "TAnd":
                eat(TAnd.class);
                break;
            case "TXor":
                eat(TXor.class);
                break;
            case "TLogicalNot":
                eat(TLogicalNot.class);
                break;
            default:
                error();
        }
    }
    
    /**
     * Parse a Lambda.
     * Lambda = \(ArgList -> Func)
     */
    private void Lambda()
    {
        eat(TLambdaStart.class);
        eat(TLParen.class);
        ArgList();
        eat(TLambdaArrow.class);
        Func();
        eat(TRParen.class);
    }
    
    /**
     * Parse a List.
     * List = [ Args ]
     *      = []
     */
    private void List()
    {
        eat(TStartList.class);
        if (isArg())
        {
            Args();
            eat(TEndList.class);
        } 
        else if (token instanceof TEndList)
        {
            eat(TEndList.class);
        }
        else
        {
            error();
        }        
    }
    
    /**
     * Parse a Program.
     * Program = Def* Func
     */
    private void Program()
    {
        while (token instanceof TDef)
        {
            Def();
        }
        if (isFunc())
        {
            Func();
        }
        else
        {
            error();
        }
    }
    
    /**
     * Parse a Sig.
     * Sig = ()
     *     = (SigArgs)
     */
    private void Sig()
    {
        eat(TLParen.class);
        if (token instanceof TIdentifier) {
            SigArgs();
        }
        eat(TRParen.class);
    }
    
    /**
     * Parse a SigArgRest.
     * SigArgRest = , id
     */
    private void SigArgRest()
    {
        eat(TComma.class);
        eat(TIdentifier.class);
    }
    
    /**
     * Parse SigArgs.
     * SigArgs = id SigArgRest*
     */
    private void SigArgs()
    {
        eat(TIdentifier.class);
        while (token instanceof TComma)
        {
            SigArgRest();
        }
    }
    
    /**
     * Parse a string literal, which may include escape sequences.
     */
    private void String()
    {
        eat(TStringStart.class);
        while (!(token instanceof TStringEnd))
        {
            if (token instanceof TStringEscape)
            {
                Escape();
            }
            else if (token instanceof TStringBody)
            {
                eat(TStringBody.class);
            }
            else
            {
                error();
            }
        }
        eat(TStringEnd.class);
    }
    
    // Helper functions
    /**
     * Eats a token based on its class.  If the current token's class doesn't
     * match the given class, then an error is thrown.
     * @param klass is the class of the token to be eaten.
     */
    private void eat(Class klass)
    {
        if (token.getClass().getName().equals(klass.getName()))
        {
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
        sb.append("Unexpected token type: ");
        sb.append(token.getClass().getName());
        sb.append("\nError at column: ");
        sb.append(token.getPos());
        sb.append("\nInvalid token: ");
        sb.append(token.getText());
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
    
    /**
     * Decide if the current token indicates a Function.
     * @return true if the current token is a token that can start a Function,
     * false otherwise.
     */
    private boolean isFunc()
    {
        return isFuncName() ||
               token instanceof TTernarySemi ||
               token instanceof TFoldl ||
               token instanceof TFoldr;
    }
    
    /**
     * Decide if the current token indicates a Function Name.
     * @return true if the current token is a token that can start a Function
     * Name, false otherwise.
     */
    private boolean isFuncName()
    {
        return token instanceof TIdentifier ||
               token instanceof THead ||
               token instanceof TTail ||
               token instanceof TReverse ||
               token instanceof TPrepend ||
               token instanceof TAppend ||
               token instanceof TMap ||
               token instanceof TLength ||
               token instanceof TFlatten ||
               token instanceof TIdentity ||
               token instanceof TPrint ||
               token instanceof TNeg ||
               token instanceof TLogicalNot ||
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
               isFunc();
    }
    
    /**
     * Get the next valid token, skipping comments and whitespace.
     * @return the next non-ignored token.
     */
    private Token nextValidToken()
    {
        token = lexer.getToken();
        while (token instanceof TSpace || token instanceof TComment)
        {
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
                    System.err.println(iae.getMessage());
                }
            }
        }
    }
}
