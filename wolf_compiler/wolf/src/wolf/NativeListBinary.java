package wolf;

import wolf.enums.NativeListBinaryOp;
import wolf.interfaces.Arg;
import wolf.interfaces.BinOp;
import wolf.interfaces.ListArgument;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A Native List Binary, or binary functions native to WOLF that operate
 * on lists
 * 
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class NativeListBinary extends WolfFunction {
    NativeListBinaryOp binary_op;
    Arg arg;
    ListArgument list_argument;
    
    public NativeListBinary(NativeListBinaryOp binary_op, Arg arg, 
        ListArgument list_argument) {
        this.binary_op = binary_op;
        this.arg = arg;
        this.list_argument = list_argument;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the return type of the binary list function
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    /**
     * @return string representation of a list binary op
     */
    @Override
    public String toString() {
        return binary_op.toString() + "(" + 
            arg.toString() + ", " + list_argument.toString() + ")";
    }
}
