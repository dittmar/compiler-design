package wolf;

import wolf.enums.NativeBinOp;
import wolf.interfaces.WolfFunction;
import wolf.interfaces.Arg;
import wolf.interfaces.Visitor;

/**
 * A Native Binary, or binary functions native to WOLF
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class NativeBinary implements WolfFunction {
    NativeBinOp binary_op;
    Arg arg_left;
    Arg arg_right;
    
    /**
     * Accept a visitor
     * @param v a visitor
     */
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
