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
public class NativeUnary implements WolfFunction, UnaryOp {
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
    public Type accept(Visitor v) {
        Type argType = (Type) v.visit(arg);
        switch(unary_op) {
            case NEG:
                if(argType.flat_type != FlatType.INTEGER && 
                   argType.flat_type != FlatType.FLOAT) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "NEG. Expecting Integer or Float.");
                    return null;
                }
                return argType;
            case LOGICAL_NOT:
                if(argType.flat_type != FlatType.INTEGER) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "LOGICAL_NOT. Expecting Integer.");
                    return null;
                }
                return argType;
            case IDENTITY:
            case PRINT:
                return argType;
            default:
                System.err.println("Invalid Native Unary Operation");
                return null;
        }
    }
}
