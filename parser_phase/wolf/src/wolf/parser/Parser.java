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
    Token token = WolfLexing.getToken();
    
    void ArgList()
    {
        eat(TLParen.class);
        if (token instanceof TRParen)
        {
            eat(TRParen.class);
        }
        else if (isArg())
        {
            Arg();
            eat(TRParen.class);
        }
        else
        {
            
        }
    }
    
    void Arg()
    {
        if (token instanceof TIdentifier)
        {
            eat(TIdentifier.class);
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
            eat(TStringStart.class);
            
            eat(TStringEnd.class);
        }
        else if (token instanceof TStartList)
        {
            List();
        }
        else if (isFuncName())
        {
            Func();
        }
        else
        {
            
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
    
    void BoolExpr()
    {
        
    }
    
    void Branch()
    {
        eat(TTernarySemi.class);
        BoolExpr();
        eat(TTernaryQuestionMark.class);
        Func();
        eat(TTernaryColon.class);
        Func();
    }
    
    void Def()
    {
        eat(TDef.class);
        eat(TDef.class);
        Sig();
        eat(TAssign.class);
        Func();
    }
    
    void Func()
    {
        if (token instanceof TTernarySemi)
        {
            Branch();
        }
        else if (isFuncName())
        {
            FuncName();
            ArgList();
        }
        else
        {
            
        }
    }
    
    void FuncName()
    {
        
    }
    
    void List()
    {
        eat(TStartList.class);
        Args();
        eat(TEndList.class);
    }
    
    void Program()
    {
        if (token instanceof TDef)
        {
            Def();
        }
        else if (isFuncName())
        {
            FuncName();
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
    
    void eat(Class klass)
    {
        if (token.getClass().getName().equals(klass.getClass().getName()))
        {
            token = WolfLexing.getToken();
        }
        throw new IllegalArgumentException(
                "Unexpected token type: " + token.getClass().getName());
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
               token instanceof TFoldl ||
               token instanceof TFoldr ||
               token instanceof TFlatten ||
               token instanceof TIdentity ||
               token instanceof TPrint ||
               token instanceof TLambdaStart ||
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
               isFuncName();
    }
}
