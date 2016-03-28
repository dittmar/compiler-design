/* This file was generated by SableCC (http://www.sablecc.org/). */

package wolf.analysis;

import wolf.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseAUserDefsProgram(AUserDefsProgram node);
    void caseANoDefsProgram(ANoDefsProgram node);
    void caseAMultipleDefsDefList(AMultipleDefsDefList node);
    void caseAOneDefDefList(AOneDefDefList node);
    void caseADefHeader(ADefHeader node);
    void caseAEmptySig(AEmptySig node);
    void caseAArgumentsSig(AArgumentsSig node);
    void caseAMoreIdsSigArgs(AMoreIdsSigArgs node);
    void caseALastIdSigArgs(ALastIdSigArgs node);
    void caseASigArgRest(ASigArgRest node);
    void caseAUserDefinedFunction(AUserDefinedFunction node);
    void caseAUnaryNativeFunction(AUnaryNativeFunction node);
    void caseABinNativeFunction(ABinNativeFunction node);
    void caseAIfElseFunction(AIfElseFunction node);
    void caseAFoldlFunction(AFoldlFunction node);
    void caseAFoldrFunction(AFoldrFunction node);
    void caseAMapFunction(AMapFunction node);
    void caseALambda(ALambda node);
    void caseAFoldBody(AFoldBody node);
    void caseALiteralListArgument(ALiteralListArgument node);
    void caseAUserDefinedListArgument(AUserDefinedListArgument node);
    void caseAReturnedListListArgument(AReturnedListListArgument node);
    void caseANativeBinOp(ANativeBinOp node);
    void caseAUserDefinedBinOp(AUserDefinedBinOp node);
    void caseALambdaBinOp(ALambdaBinOp node);
    void caseAPlusNativeBinOp(APlusNativeBinOp node);
    void caseAMinusNativeBinOp(AMinusNativeBinOp node);
    void caseAMultNativeBinOp(AMultNativeBinOp node);
    void caseADivNativeBinOp(ADivNativeBinOp node);
    void caseAModNativeBinOp(AModNativeBinOp node);
    void caseALessNativeBinOp(ALessNativeBinOp node);
    void caseAGreaterNativeBinOp(AGreaterNativeBinOp node);
    void caseALessEqualNativeBinOp(ALessEqualNativeBinOp node);
    void caseAGreaterEqualNativeBinOp(AGreaterEqualNativeBinOp node);
    void caseAEqualNativeBinOp(AEqualNativeBinOp node);
    void caseANotEqualNativeBinOp(ANotEqualNativeBinOp node);
    void caseAAndNativeBinOp(AAndNativeBinOp node);
    void caseAOrNativeBinOp(AOrNativeBinOp node);
    void caseAXorNativeBinOp(AXorNativeBinOp node);
    void caseAAppendNativeBinOp(AAppendNativeBinOp node);
    void caseAPrependNativeBinOp(APrependNativeBinOp node);
    void caseANativeUnaryOp(ANativeUnaryOp node);
    void caseAUserDefinedUnaryOp(AUserDefinedUnaryOp node);
    void caseALambdaUnaryOp(ALambdaUnaryOp node);
    void caseAArithmeticNotNativeUnaryOp(AArithmeticNotNativeUnaryOp node);
    void caseALogicalNotNativeUnaryOp(ALogicalNotNativeUnaryOp node);
    void caseAHeadNativeUnaryOp(AHeadNativeUnaryOp node);
    void caseATailNativeUnaryOp(ATailNativeUnaryOp node);
    void caseAReverseNativeUnaryOp(AReverseNativeUnaryOp node);
    void caseAFlattenNativeUnaryOp(AFlattenNativeUnaryOp node);
    void caseAIdentityNativeUnaryOp(AIdentityNativeUnaryOp node);
    void caseAPrintNativeUnaryOp(APrintNativeUnaryOp node);
    void caseALengthNativeUnaryOp(ALengthNativeUnaryOp node);
    void caseAUserDefinedUserFunc(AUserDefinedUserFunc node);
    void caseALambdaUserFunc(ALambdaUserFunc node);
    void caseANoArgsArgList(ANoArgsArgList node);
    void caseAHasArgsArgList(AHasArgsArgList node);
    void caseAMoreArgsArgs(AMoreArgsArgs node);
    void caseALastArgArgs(ALastArgArgs node);
    void caseAArgRest(AArgRest node);
    void caseAFunctionArg(AFunctionArg node);
    void caseAListArg(AListArg node);
    void caseAIntArg(AIntArg node);
    void caseAFloatArg(AFloatArg node);
    void caseAIdentifierArg(AIdentifierArg node);
    void caseAStringArg(AStringArg node);
    void caseAEmptyStringString(AEmptyStringString node);
    void caseAStringString(AStringString node);
    void caseAMoreBodyStringMiddle(AMoreBodyStringMiddle node);
    void caseALastBodyStringMiddle(ALastBodyStringMiddle node);
    void caseAMoreEscapeStringMiddle(AMoreEscapeStringMiddle node);
    void caseALastEscapeStringMiddle(ALastEscapeStringMiddle node);
    void caseAStringEscapeSeq(AStringEscapeSeq node);
    void caseAAlarmEscapeChar(AAlarmEscapeChar node);
    void caseABackspaceEscapeChar(ABackspaceEscapeChar node);
    void caseAFormfeedEscapeChar(AFormfeedEscapeChar node);
    void caseACrEscapeChar(ACrEscapeChar node);
    void caseANewlineEscapeChar(ANewlineEscapeChar node);
    void caseATabEscapeChar(ATabEscapeChar node);
    void caseAVerticalTabEscapeChar(AVerticalTabEscapeChar node);
    void caseABackslashEscapeChar(ABackslashEscapeChar node);
    void caseASingleQuoteEscapeChar(ASingleQuoteEscapeChar node);
    void caseADoubleQuoteEscapeChar(ADoubleQuoteEscapeChar node);
    void caseAQuestionMarkEscapeChar(AQuestionMarkEscapeChar node);
    void caseAOctalEscapeChar(AOctalEscapeChar node);
    void caseAHexEscapeChar(AHexEscapeChar node);
    void caseAUnicodeEscapeChar(AUnicodeEscapeChar node);
    void caseADefaultEscapeChar(ADefaultEscapeChar node);
    void caseABranch(ABranch node);
    void caseAEmptyListList(AEmptyListList node);
    void caseAListList(AListList node);

    void caseTStringStart(TStringStart node);
    void caseTStringEscape(TStringEscape node);
    void caseTEscapeAlarm(TEscapeAlarm node);
    void caseTEscapeBackspace(TEscapeBackspace node);
    void caseTEscapeFormfeed(TEscapeFormfeed node);
    void caseTEscapeCarriageReturn(TEscapeCarriageReturn node);
    void caseTEscapeNewline(TEscapeNewline node);
    void caseTEscapeTab(TEscapeTab node);
    void caseTEscapeVerticalTab(TEscapeVerticalTab node);
    void caseTEscapeBackslash(TEscapeBackslash node);
    void caseTEscapeSingleQuote(TEscapeSingleQuote node);
    void caseTEscapeDoubleQuote(TEscapeDoubleQuote node);
    void caseTEscapeQuestionMark(TEscapeQuestionMark node);
    void caseTEscapeOctalChar(TEscapeOctalChar node);
    void caseTEscapeHexChar(TEscapeHexChar node);
    void caseTEscapeUnicodeChar(TEscapeUnicodeChar node);
    void caseTEscapeDefault(TEscapeDefault node);
    void caseTStringBody(TStringBody node);
    void caseTStringEnd(TStringEnd node);
    void caseTHead(THead node);
    void caseTTail(TTail node);
    void caseTReverse(TReverse node);
    void caseTAppend(TAppend node);
    void caseTPrepend(TPrepend node);
    void caseTFoldl(TFoldl node);
    void caseTFoldr(TFoldr node);
    void caseTMap(TMap node);
    void caseTFlatten(TFlatten node);
    void caseTLength(TLength node);
    void caseTLambdaStart(TLambdaStart node);
    void caseTLambdaArrow(TLambdaArrow node);
    void caseTIdentity(TIdentity node);
    void caseTPrint(TPrint node);
    void caseTDef(TDef node);
    void caseTComment(TComment node);
    void caseTSpace(TSpace node);
    void caseTAssign(TAssign node);
    void caseTEqual(TEqual node);
    void caseTLt(TLt node);
    void caseTGt(TGt node);
    void caseTNotEqual(TNotEqual node);
    void caseTLte(TLte node);
    void caseTGte(TGte node);
    void caseTAnd(TAnd node);
    void caseTOr(TOr node);
    void caseTXor(TXor node);
    void caseTLogicalNot(TLogicalNot node);
    void caseTPlus(TPlus node);
    void caseTMinus(TMinus node);
    void caseTMult(TMult node);
    void caseTDiv(TDiv node);
    void caseTMod(TMod node);
    void caseTNeg(TNeg node);
    void caseTLParen(TLParen node);
    void caseTRParen(TRParen node);
    void caseTStartList(TStartList node);
    void caseTEndList(TEndList node);
    void caseTComma(TComma node);
    void caseTSemi(TSemi node);
    void caseTTernaryQuestionMark(TTernaryQuestionMark node);
    void caseTTernaryColon(TTernaryColon node);
    void caseTIdentifier(TIdentifier node);
    void caseTIntNumber(TIntNumber node);
    void caseTFloatNumber(TFloatNumber node);
    void caseTMisc(TMisc node);
    void caseEOF(EOF node);
    void caseInvalidToken(InvalidToken node);
}
