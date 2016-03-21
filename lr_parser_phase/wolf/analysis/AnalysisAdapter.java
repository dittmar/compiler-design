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
    public void caseStart(Start node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADefsProgram(ADefsProgram node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANoDefsProgram(ANoDefsProgram node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultiDefDefList(AMultiDefDefList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASingleDefDefList(ASingleDefDefList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADefHeader(ADefHeader node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEmptySig(AEmptySig node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFullSig(AFullSig node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMoreSigArgs(AMoreSigArgs node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADoneSigArgs(ADoneSigArgs node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASigArgRest(ASigArgRest node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncFunc(AFuncFunc node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABranchFunc(ABranchFunc node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdFuncName(AIdFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAHeadFuncName(AHeadFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseATailFuncName(ATailFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAReverseFuncName(AReverseFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPrependFuncName(APrependFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAppendFuncName(AAppendFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMapFuncName(AMapFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALengthFuncName(ALengthFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFoldlFuncName(AFoldlFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFoldrFuncName(AFoldrFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFlattenFuncName(AFlattenFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentityFuncName(AIdentityFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPrintFuncName(APrintFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALambdaFuncName(ALambdaFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANegFuncName(ANegFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANotFuncName(ANotFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPlusFuncName(APlusFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMinusFuncName(AMinusFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultFuncName(AMultFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADivFuncName(ADivFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAModFuncName(AModFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALtFuncName(ALtFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGtFuncName(AGtFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALteFuncName(ALteFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGteFuncName(AGteFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEqualFuncName(AEqualFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANotEqualFuncName(ANotEqualFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAndFuncName(AAndFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOrFuncName(AOrFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAXorFuncName(AXorFuncName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEmptyArgList(AEmptyArgList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFullArgList(AFullArgList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADoneArgs(ADoneArgs node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMoreArgs(AMoreArgs node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArgRest(AArgRest node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFuncArg(AFuncArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAListArg(AListArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFloatArg(AFloatArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIntArg(AIntArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentifierArg(AIdentifierArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStringArg(AStringArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEmptyString(AEmptyString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFullString(AFullString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMoreBodyStringMiddle(AMoreBodyStringMiddle node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADoneBodyStringMiddle(ADoneBodyStringMiddle node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMoreEscapeStringMiddle(AMoreEscapeStringMiddle node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADoneEscapeStringMiddle(ADoneEscapeStringMiddle node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStringEscapeSeq(AStringEscapeSeq node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAEscapeChar(AAEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABEscapeChar(ABEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFEscapeChar(AFEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAREscapeChar(AREscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANEscapeChar(ANEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseATEscapeChar(ATEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVEscapeChar(AVEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASlashEscapeChar(ASlashEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASingleEscapeChar(ASingleEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADoubleEscapeChar(ADoubleEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAQuestionEscapeChar(AQuestionEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOctalEscapeChar(AOctalEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAHexEscapeChar(AHexEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUnicodeEscapeChar(AUnicodeEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADefaultEscapeChar(ADefaultEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABranch(ABranch node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACond(ACond node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIf(AIf node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAElse(AElse node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFullList(AFullList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEmptyList(AEmptyList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStringStart(TStringStart node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStringEscape(TStringEscape node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeAlarm(TEscapeAlarm node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeBackspace(TEscapeBackspace node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeFormfeed(TEscapeFormfeed node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeCarriageReturn(TEscapeCarriageReturn node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeNewline(TEscapeNewline node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeTab(TEscapeTab node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeVerticalTab(TEscapeVerticalTab node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeBackslash(TEscapeBackslash node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeSingleQuote(TEscapeSingleQuote node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeDoubleQuote(TEscapeDoubleQuote node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeQuestionMark(TEscapeQuestionMark node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeOctalChar(TEscapeOctalChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeHexChar(TEscapeHexChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeUnicodeChar(TEscapeUnicodeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEscapeDefault(TEscapeDefault node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStringBody(TStringBody node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStringEnd(TStringEnd node)
    {
        defaultCase(node);
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
    public void caseTFlatten(TFlatten node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLength(TLength node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLambdaStart(TLambdaStart node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLambdaArrow(TLambdaArrow node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIdentity(TIdentity node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPrint(TPrint node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDef(TDef node)
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
    public void caseTSemi(TSemi node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTernaryQuestionMark(TTernaryQuestionMark node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTernaryColon(TTernaryColon node)
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
