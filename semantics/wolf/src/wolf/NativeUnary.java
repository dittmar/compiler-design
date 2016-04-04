package wolf;

import wolf.enums.NativeUnaryOp;
import wolf.interfaces.WolfFunction;
import wolf.interfaces.Arg;
import wolf.interfaces.UnaryOp;
import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class NativeUnary implements WolfFunction, UnaryOp {
    NativeUnaryOp unary_op;
    Arg arg;
    public void accept(Visitor v) {
        v.visit(this);
    }
}
