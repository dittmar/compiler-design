package wolf.interfaces;

import wolf.*;
import wolf.enums.*;

/**
 * The visitor interface. Visitors define an operation to separate a class
 * from said operation.
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public interface Visitor {

    Object visit(Program n);

    Object visit(Def n);

    Object visit(Sig n);

    Object visit(SigArg n);

    Object visit(UserFunc n);

    Object visit(Branch n);

    Object visit(WolfLambda n);

    Object visit(Fold n);

    Object visit(FoldSymbol n);

    Object visit(FoldBody n);

    Object visit(WolfMap n);

    Object visit(ListArgsList n);

    Object visit(ArgsList n);

    Object visit(NativeUnary n);

    Object visit(NativeListUnary n);

    Object visit(NativeBinary n);

    Object visit(NativeListBinary n);

    Object visit(Identifier n);

    Object visit(FloatLiteral n);

    Object visit(IntLiteral n);

    Object visit(WolfList n);

    Object visit(WolfString n);

    Object visit(StringBody n);

    Object visit(StringEscapeSeq n);




    /*void visit(Program n);

    Type visit(Def n);

    void visit(Sig n);

    Type visit(SigArg n);

    Type visit(WolfFunction n);

    Type visit(UserFunc n);

    Type visit(NativeUnary n);

    Type visit(NativeBinary n);

    Type visit(Branch n);

    Type visit(WolfMap n);

    Type visit(Fold n);

    void visit(FoldSymbol n);

    Type visit(UnaryOp n);

    Type visit(ListArgument n);

    Type visit(WolfLambda n);

    Type visit(FoldBody n);

    Type visit(BinOp n);

    Type visit(WolfList n);

    Type visit(Identifier n);

    Type visit(Arg n);

    Type visit(UserFuncName n);

    void visit(ArgsList n);

    Type visit(ListArgsList n);

    Type visit(IntLiteral n);

    Type visit(FloatLiteral n);

    Type visit(WolfString n);

    void visit(StringMiddle n);

    void visit(StringBody n);

    void visit(StringEscapeSeq n);

    Type visit(Type n);*/

    //SymbolTable getCurrentDefTable();
}