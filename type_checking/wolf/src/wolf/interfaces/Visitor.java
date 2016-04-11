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
    
    Type visit(Type n);
}