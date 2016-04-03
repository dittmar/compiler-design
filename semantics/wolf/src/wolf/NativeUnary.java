package wolf;

import wolf.enums.NativeUnaryOp;
import wolf.interfaces.WolfFunction;
import wolf.interfaces.Arg;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class NativeUnary implements WolfFunction {
    NativeUnaryOp unary_op;
    Arg arg;
}
