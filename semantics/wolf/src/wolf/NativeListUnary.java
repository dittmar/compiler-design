package wolf;

import wolf.enums.NativeListUnaryOp;
import wolf.interfaces.ListArgument;
import wolf.interfaces.UnaryOp;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A native unary, or unary operations native to WOLF
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class NativeListUnary implements WolfFunction, UnaryOp {
    NativeListUnaryOp list_unary_op;
    ListArgument list_argument;
    
    public NativeListUnary(NativeListUnaryOp list_unary_op, 
            ListArgument list_argument) {
        this.list_unary_op = list_unary_op;
        this.list_argument = list_argument;
    }
    
    /**
     * Accepts a visitor
     * @param v a visitor
     * @return the type returned by this unary op.
     */
    @Override
    public Type accept(Visitor v) {
        Type argType = (Type) v.visit(list_argument);
        switch(list_unary_op) {
            case HEAD:
                if(!argType.is_list) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "HEAD. Expecting List.");
                    return null;
                }
                //Arg head = ((WolfList) list_argument).arg_list.get(0);
                return new Type(argType.flat_type);
            case TAIL:
                if(!argType.is_list) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "TAIL. Expecting List.");
                    return null;
                }
                return argType;
            case REVERSE:
                if(!argType.is_list) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "REVERSE. Expecting List.");
                    return null;
                }
                return argType;
            case FLATTEN:
                if(!argType.is_list) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "FLATTEN. Expecting List.");
                    return null;
                }
                return argType;
            case LENGTH:
                if(!argType.is_list) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "LENGTH. Expecting List.");
                    return null;
                }
                return new Type(argType.flat_type);
            default:
                System.err.println("Invalid Native List Unary Operation");
                return null;
        }
    }
}

