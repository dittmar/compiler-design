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
    
    /**
     * Accepts a visitor
     * @param v a visitor
     * @return the type returned by this unary op.
     */
    @Override
    public Object accept(Visitor v) {
        Type argType = (Type) v.visit(arg);
        switch(unary_op) {
            case NEG:
                if(argType != Type.INTEGER && argType != Type.FLOAT && argType != Type.PARAMETER) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "NEG. Expecting Integer or Float.");
                    return null;
                }
                return argType;
            case LOGICAL_NOT:
                if(argType != Type.INTEGER && argType != Type.PARAMETER) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "LOGICAL_NOT. Expecting Integer.");
                    return null;
                }
                return argType;
            case HEAD:
                if(argType != Type.LIST && argType != Type.PARAMETER) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "HEAD. Expecting List.");
                    return null;
                }
                Arg head = ((WolfList) arg).arg_list.arg_list.get(0);
                return v.visit(arg);
            case TAIL:
                if(argType != Type.LIST && argType != Type.PARAMETER) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "TAIL. Expecting List.");
                    return null;
                }
                return argType;
            case REVERSE:
                if(argType != Type.LIST && argType != Type.PARAMETER) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "REVERSE. Expecting List.");
                    return null;
                }
                return argType;
            case FLATTEN:
                if(argType != Type.LIST && argType != Type.PARAMETER) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "FLATTEN. Expecting List.");
                    return null;
                }
                return argType;
            case IDENTITY:
            case PRINT:
                return argType;
            case LENGTH:
                if(argType != Type.LIST && argType != Type.STRING && argType != Type.PARAMETER) {
                    System.err.println("Invalid Argument " + argType + " for "
                            + "LENGTH. Expecting List.");
                    return null;
                }
                return Type.INTEGER;
            default:
                System.err.println("Invalid Native Unary Operation");
                return null;
        }
    }
}
