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

    Object visit(NativeBinOp n);

    Object visit(NativeUnaryOp n);
    
    Object visit(Type n);
    
    Object visit(InputArg n);
}