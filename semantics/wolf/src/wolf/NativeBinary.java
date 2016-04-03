package wolf;

import wolf.enums.NativeBinOp;
import wolf.interfaces.WolfFunction;
import wolf.interfaces.Arg;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class NativeBinary implements WolfFunction {
    NativeBinOp binary_op;
    Arg arg_left;
    Arg arg_right;
}
