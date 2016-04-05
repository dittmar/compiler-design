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
    
    public NativeBinary(NativeBinOp binary_op, Arg arg_left, Arg arg_right) {
        this.binary_op = binary_op;
        this.arg_left = arg_left;
        this.arg_right = arg_right;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     */
    @Override
    public Object accept(Visitor v) {
        Type leftType = (Type) v.visit(arg_left);
        Type rightType = (Type) v.visit(arg_right);
        boolean areSameType = (leftType.equals(rightType));
        switch(binary_op) {
            case PLUS:
            case MINUS:
            if(!areSameType) {
                    System.err.println("Left should be of type " + rightType);
                    return null;
                }
            return leftType;
            case MULT:
            case DIV:
            case MOD:
                if(!areSameType) {
                    System.err.println("Left should be of type " + rightType);
                    return null;
                }
                if(leftType.flat_type != FlatType.INTEGER && 
                   leftType.flat_type != FlatType.FLOAT) {
                    System.err.println("Left of type" + leftType + 
                            ". Expecting integer or float.");
                }
                if(rightType.flat_type != FlatType.INTEGER && 
                   rightType.flat_type  != FlatType.FLOAT) {
                    System.err.println("Right of type" + leftType + 
                            ". Expecting integer or float.");
                }
                return leftType;
            case LT:
            case GT:
            case LTE:
            case GTE:
            case AND:
            case OR:
            case XOR:
                if(!areSameType) {
                    System.err.println("Left should be of type " + rightType);
                    return null;
                }
                if(leftType.flat_type != FlatType.INTEGER) {
                    System.err.println("Left of type" + leftType + 
                            ". Expecting integer.");
                }
                if(rightType.flat_type != FlatType.INTEGER) {
                    System.err.println("Right of type" + leftType + 
                            ". Expecting integer.");
                }
                return leftType;
            case EQUAL:
            case NOT_EQUAL:
                return new Type(FlatType.INTEGER);
            default:
                System.err.println("Invalid Binary Operator!");
                return null;
        }
    }
}
