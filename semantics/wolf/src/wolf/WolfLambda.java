package wolf;

import wolf.interfaces.BinOp;
import wolf.interfaces.UnaryOp;
import wolf.interfaces.UserFuncName;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A lambda object used for lambdas in the WOLF language
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class WolfLambda implements WolfFunction, BinOp, UnaryOp, UserFuncName {
    Sig sig;
    WolfFunction function;
    
    /**
     * Accept a visitor
     * @param v a visitor
     */
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
