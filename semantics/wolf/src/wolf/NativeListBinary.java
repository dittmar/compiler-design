package wolf;

import wolf.enums.NativeListBinaryOp;
import wolf.interfaces.Arg;
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
public class NativeListBinary implements WolfFunction {
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
     */
    @Override
    public Object accept(Visitor v) {
        Type arg_type = (Type) v.visit(arg);
        Type list_type = (Type) v.visit(list_argument);
        switch(binary_op) {
            case APPEND:
            case PREPEND:
                if(list_type != Type.LIST && list_type != Type.PARAMETER) {
                    System.err.println("Expecting list, found " + arg_type);
                }
                return Type.LIST;
            default:
                System.err.println("Invalid Native List Binary Operation!");
                return null;
        }
    }
}
