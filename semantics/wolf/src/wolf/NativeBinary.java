package wolf;

import wolf.enums.NativeBinOp;
import wolf.interfaces.WolfFunction;
import wolf.interfaces.Arg;
import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class NativeBinary implements WolfFunction {
    NativeBinOp binary_op;
    Arg arg_left;
    Arg arg_right;
    public void accept(Visitor v) {
        v.visit(this);
    }
}
