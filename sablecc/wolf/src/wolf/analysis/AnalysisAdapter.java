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
    public void caseAUserDefsProgram(AUserDefsProgram node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANoDefsProgram(ANoDefsProgram node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultipleDefsDefList(AMultipleDefsDefList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOneDefDefList(AOneDefDefList node)
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
    public void caseAArgumentsSig(AArgumentsSig node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMoreIdsSigArgs(AMoreIdsSigArgs node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALastIdSigArgs(ALastIdSigArgs node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASigArgRest(ASigArgRest node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUserDefinedFunction(AUserDefinedFunction node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUnaryNativeFunction(AUnaryNativeFunction node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABinNativeFunction(ABinNativeFunction node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIfElseFunction(AIfElseFunction node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFoldlFunction(AFoldlFunction node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFoldrFunction(AFoldrFunction node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMapFunction(AMapFunction node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALambda(ALambda node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFoldBody(AFoldBody node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALiteralListArgument(ALiteralListArgument node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUserDefinedListArgument(AUserDefinedListArgument node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAReturnedListListArgument(AReturnedListListArgument node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANativeBinOp(ANativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUserDefinedBinOp(AUserDefinedBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALambdaBinOp(ALambdaBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPlusNativeBinOp(APlusNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMinusNativeBinOp(AMinusNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultNativeBinOp(AMultNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADivNativeBinOp(ADivNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAModNativeBinOp(AModNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALessNativeBinOp(ALessNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGreaterNativeBinOp(AGreaterNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALessEqualNativeBinOp(ALessEqualNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGreaterEqualNativeBinOp(AGreaterEqualNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEqualNativeBinOp(AEqualNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANotEqualNativeBinOp(ANotEqualNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAndNativeBinOp(AAndNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOrNativeBinOp(AOrNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAXorNativeBinOp(AXorNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAppendNativeBinOp(AAppendNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPrependNativeBinOp(APrependNativeBinOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANativeUnaryOp(ANativeUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUserDefinedUnaryOp(AUserDefinedUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALambdaUnaryOp(ALambdaUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArithmeticNotNativeUnaryOp(AArithmeticNotNativeUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALogicalNotNativeUnaryOp(ALogicalNotNativeUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAHeadNativeUnaryOp(AHeadNativeUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseATailNativeUnaryOp(ATailNativeUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAReverseNativeUnaryOp(AReverseNativeUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFlattenNativeUnaryOp(AFlattenNativeUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentityNativeUnaryOp(AIdentityNativeUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPrintNativeUnaryOp(APrintNativeUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALengthNativeUnaryOp(ALengthNativeUnaryOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUserDefinedUserFunc(AUserDefinedUserFunc node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALambdaUserFunc(ALambdaUserFunc node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANoArgsArgList(ANoArgsArgList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAHasArgsArgList(AHasArgsArgList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMoreArgsArgs(AMoreArgsArgs node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALastArgArgs(ALastArgArgs node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArgRest(AArgRest node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFunctionArg(AFunctionArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAListArg(AListArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIntArg(AIntArg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFloatArg(AFloatArg node)
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
    public void caseAEmptyStringString(AEmptyStringString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStringString(AStringString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMoreBodyStringMiddle(AMoreBodyStringMiddle node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALastBodyStringMiddle(ALastBodyStringMiddle node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMoreEscapeStringMiddle(AMoreEscapeStringMiddle node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALastEscapeStringMiddle(ALastEscapeStringMiddle node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStringEscapeSeq(AStringEscapeSeq node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAlarmEscapeChar(AAlarmEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABackspaceEscapeChar(ABackspaceEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFormfeedEscapeChar(AFormfeedEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACrEscapeChar(ACrEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANewlineEscapeChar(ANewlineEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseATabEscapeChar(ATabEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVerticalTabEscapeChar(AVerticalTabEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABackslashEscapeChar(ABackslashEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASingleQuoteEscapeChar(ASingleQuoteEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADoubleQuoteEscapeChar(ADoubleQuoteEscapeChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAQuestionMarkEscapeChar(AQuestionMarkEscapeChar node)
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
    public void caseAEmptyListList(AEmptyListList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAListList(AListList node)
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
