package wolf;

import java.util.ArrayList;
import wolf.enums.NativeBinOp;
import wolf.interfaces.Arg;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

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
    
    public NativeBinary(NativeBinOp binary_op, Arg arg_left, Arg arg_right) {
        this.binary_op = binary_op;
        this.arg_left = arg_left;
        this.arg_right = arg_right;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return type of the return value of the binary function
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    /**
     * @return string representation of a binary op
     */
    @Override
    public String toString() {
        return binary_op.toString() + "(" + 
            arg_left.toString() + ", " + arg_right.toString() + ")";
    }
}
