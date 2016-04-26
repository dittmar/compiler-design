package wolf;

import wolf.enums.NativeUnaryOp;
import wolf.interfaces.WolfFunction;
import wolf.interfaces.Arg;
import wolf.interfaces.UnaryOp;
import wolf.interfaces.Visitor;

/**
 * A native unary, or unary operations native to WOLF
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class NativeUnary extends WolfFunction {
    NativeUnaryOp unary_op;
    Arg arg;
    
    public NativeUnary(NativeUnaryOp unary_op, Arg arg) {
        this.unary_op = unary_op;
        this.arg = arg;
    }
    
    /**
     * Accepts a visitor
     * @param v a visitor
     * @return the type returned by this unary op.
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    /**
     * @return String representation of a unary op
     */
    @Override
    public String toString() {
        return unary_op.toString() + "(" + arg.toString() + ")";
    }
}
