package wolf.parser;
import wolf.*;
import wolf.node.*;
/**
 *
 * @author Kevin Dittmar
 * @version Feb 23, 2016
 */
public class Parser 
{
    private WolfLexing lexer;
    private Token token;
    
    public Parser()
    {
        
    }
    
    public void parse()
    {
        lexer = new WolfLexing();
        token = nextValidToken();
        Program();
    }
    
    public void parse(String filename)
    {
        lexer = new WolfLexing(filename);
        token = nextValidToken();
        Program();
    }
    
    void ArgList()
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
    
    void Arg()
    {
        System.out.println("Looking at: " + token.getText());
        if (token instanceof TIdentifier)
        {
            eat(TIdentifier.class);
            // It's a function call, not a function input
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
    
    void ArgRest()
    {
        eat(TComma.class);
        Arg();
    }
    
    void Args()
    {
        Arg();
        while (token instanceof TComma)
        {
            ArgRest();
        }
    }
    
    void Branch()
    {
        eat(TTernarySemi.class);
        Func();
        eat(TTernaryQuestionMark.class);
        Func();
        eat(TTernaryColon.class);
        Func();
    }
    
    void Def()
    {
        eat(TDef.class);
        eat(TIdentifier.class);
        Sig();
        eat(TAssign.class);
        Func();
    }
    
    void Escape()
    {
        eat(TStringEscape.class);
        switch(token.getClass().getName())
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
    
    void FoldBody()
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
    
    void Func()
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
    
    void FuncName()
    {
        String[] token_name_parts = token.getClass().getName().split("\\.");
        switch(token_name_parts[token_name_parts.length - 1])
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
            case "TOr":
                eat(TOr.class);
                break;
            case "TAnd":
                eat(TAnd.class);
                break;
            case "TXor":
                break;
            case "TLogicalNot":
                eat(TLogicalNot.class);
                break;
            default:
                error();
        }
    }
    
    void Lambda()
    {
        eat(TLambdaStart.class);
        eat(TLParen.class);
        ArgList();
        eat(TLambdaArrow.class);
        Func();
        eat(TRParen.class);
    }
    
    void List()
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
    
    void Program()
    {
        System.out.println("Looking at: " + token.getText());
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
    
    void Sig()
    {
        eat(TLParen.class);
        if (token instanceof TIdentifier) {
            SigArgs();
        }
        eat(TRParen.class);
    }
    
    void SigArgRest()
    {
        eat(TComma.class);
        eat(TIdentifier.class);
    }
    
    void SigArgs()
    {
        eat(TIdentifier.class);
        while (token instanceof TComma)
        {
            SigArgRest();
        }
    }
    
    void String()
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
    
    void eat(Class klass)
    {
        System.out.println(token.getClass().getName());
        System.out.println(klass.getName());
        if (token.getClass().getName().equals(klass.getName()))
        {
            token = nextValidToken();
        }
        else
        {
            error();
        }
    }
    
    void error()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected token type: ");
        sb.append(token.getClass().getName());
        sb.append("\nError at: ");
        sb.append(token.getPos());
        sb.append("\nError: ");
        sb.append(token.getText());
        throw new IllegalArgumentException(sb.toString());
    }
    
    boolean isFunc()
    {
        return isFuncName() ||
               token instanceof TTernarySemi ||
               token instanceof TFoldl ||
               token instanceof TFoldr;
    }
    
    boolean isFuncName()
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
    
    boolean isArg()
    {
        return token instanceof TIdentifier ||
               token instanceof TIntNumber ||
               token instanceof TFloatNumber ||
               token instanceof TStringStart ||
               token instanceof TStartList ||
               token instanceof TLambdaStart ||
               isFunc();
    }
    
    Token nextValidToken()
    {
        token = lexer.getToken();
        while (token instanceof TSpace || token instanceof TComment)
        {
            token = lexer.getToken();
        }
        return token;
    }
    
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
                new Parser().parse(filename);
            }
        }
    }
}
