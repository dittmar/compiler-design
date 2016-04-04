package wolf.interfaces;

import wolf.enums.*;
import wolf.*;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public interface Visitor {

    void visit(Program n);

    void visit(Def n);

    void visit(Sig n);
    
    void visit(WolfFunction n);
    
    void visit(UserFunc n);
    
    void visit(NativeUnary n);
    
    void visit(Branch n);
    
    void visit(WolfMap n);
    
    void visit(Fold n);
    
    void visit(FoldSymbol n);
    
    void visit(UnaryOp n);
    
    void visit(ListArgument n);
    
    void visit(WolfLambda n);
    
    void visit(FoldBody n);
    
    void visit(BinOp n);
    
    void visit(WolfList n);
    
    void visit(Identifier n);
    
    void visit(Arg n);
    
    void visit(UserFuncName n);
    
    void visit(ArgList n);
    
    void visit(IntLiteral n);
    
    void visit(FloatLiteral n);
    
    void visit(WolfString n);
    
    void visit(StringMiddle n);
    
    void visit(StringBody n);
    
    void visit(StringEscapeSeq n);
    
    void visit(EscapeChar n);
    
}
