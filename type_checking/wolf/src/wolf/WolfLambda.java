package wolf;

import wolf.interfaces.BinOp;
import wolf.interfaces.UnaryOp;
import wolf.interfaces.UserFuncName;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A lambda object used for lambdas in the WOLF language
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Apr 3, 2016
 */
public class WolfLambda implements WolfFunction, BinOp, UnaryOp, UserFuncName {
    Sig sig;
    WolfFunction function;
    
    public WolfLambda(Sig sig, WolfFunction function) {
        this.sig = sig;
        this.function = function;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the type of the lambda
     */
    @Override
    public Type accept(Visitor v) {
        return v.visit(this);
    }
}
