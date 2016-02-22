/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.analysis;

import java.util.*;
import wolf.node.*;

public class AnalysisAdapter implements Analysis
{
    private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    @Override
    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    @Override
    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    @Override
    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    @Override
    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }

    @Override
    public void caseTHead(THead node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTail(TTail node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTReverse(TReverse node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAppend(TAppend node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPrepend(TPrepend node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFoldl(TFoldl node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFoldr(TFoldr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMap(TMap node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLambda(TLambda node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLength(TLength node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPrint(TPrint node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTrue(TTrue node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFalse(TFalse node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDef(TDef node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTReturn(TReturn node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTComment(TComment node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSpace(TSpace node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAssign(TAssign node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEqual(TEqual node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLt(TLt node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTGt(TGt node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNotEqual(TNotEqual node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLte(TLte node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTGte(TGte node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAnd(TAnd node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTOr(TOr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTXor(TXor node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLogicalNot(TLogicalNot node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPlus(TPlus node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMinus(TMinus node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMult(TMult node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDiv(TDiv node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMod(TMod node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNeg(TNeg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLParen(TLParen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRParen(TRParen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStartList(TStartList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEndList(TEndList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTComma(TComma node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTColon(TColon node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSemi(TSemi node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTString(TString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIdentifier(TIdentifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIntNumber(TIntNumber node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFloatNumber(TFloatNumber node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMisc(TMisc node)
    {
        defaultCase(node);
    }

    @Override
    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    @Override
    public void caseInvalidToken(InvalidToken node)
    {
        defaultCase(node);
    }

    public void defaultCase(@SuppressWarnings("unused") Node node)
    {
        // do nothing
    }
}
