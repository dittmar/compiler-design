package wolf.interfaces;

import wolf.enums.*;
import wolf.*;

/**
 * The visitor interface. Visitors define an operation to separate a class
 * from said operation.
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public interface Visitor {

    void visit(Program n);

    void visit(Def n);

    void visit(Sig n);
    
    Object visit(WolfFunction n);
    
    Object visit(UserFunc n);
    
    Object visit(NativeUnary n);
    
    Object visit(Branch n);
    
    Object visit(WolfMap n);
    
    Object visit(Fold n);
    
    Object visit(FoldSymbol n);
    
    Object visit(UnaryOp n);
    
    Object visit(ListArgument n);
    
    Object visit(WolfLambda n);
    
    Object visit(FoldBody n);
    
    Object visit(BinOp n);
    
    Object visit(WolfList n);
    
    Object visit(Identifier n);
    
    Object visit(Arg n);
    
    Object visit(UserFuncName n);
    
    Object visit(ArgList n);
    
    Object visit(IntLiteral n);
    
    Object visit(FloatLiteral n);
    
    Object visit(WolfString n);
    
    Object visit(StringMiddle n);
    
    Object visit(StringBody n);
    
    Object visit(StringEscapeSeq n);   
}