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
    public Type accept(Visitor v) {
        Type leftType = (Type) v.visit(arg_left);
        Type rightType = (Type) v.visit(arg_right);
        boolean areSameType = (leftType.equals(rightType) ||
                              (leftType.isNumeric() && rightType.isNumeric()));
        switch(binary_op) {
            case PLUS:
            case MINUS:
            if(!areSameType) {
                TypeErrorReporter.mismatchErrorBinary(
                    arg_left, leftType,
                    arg_right, rightType,
                    binary_op.toString(), null
                );
            }
            return leftType;
            case MULT:
            case DIV:
            case MOD:
                if (!leftType.isNumeric() || !rightType.isNumeric()) {
                    ArrayList<Type> types = new ArrayList<>();
                    types.add(new Type(FlatType.INTEGER));
                    types.add(new Type(FlatType.FLOAT));
                    TypeErrorReporter.mismatchErrorBinary(
                        arg_left, leftType,
                        arg_right, rightType,
                        binary_op.toString(), types);
                }
                return leftType;
            case LT:
            case GT:
            case LTE:
            case GTE:
                if (!leftType.isNumeric() || !rightType.isNumeric()) {
                    ArrayList<Type> types = new ArrayList<>();
                    types.add(new Type(FlatType.INTEGER));
                    types.add(new Type(FlatType.FLOAT));
                    TypeErrorReporter.mismatchErrorBinary(
                        arg_left, leftType,
                        arg_right, rightType,
                        binary_op.toString(), types);
                }
                return new Type(FlatType.INTEGER);
            case AND:
            case OR:
            case XOR:
                if (leftType.flat_type != FlatType.INTEGER ||
                    rightType.flat_type != FlatType.INTEGER) {
                    ArrayList<Type> types = new ArrayList<>();
                    types.add(new Type(FlatType.INTEGER));
                    TypeErrorReporter.mismatchErrorBinary(
                        arg_left, leftType,
                        arg_right, rightType,
                        binary_op.toString(), types);
                }
                return new Type(FlatType.INTEGER);
            case EQUAL:
            case NOT_EQUAL:
                return new Type(FlatType.INTEGER);
            default:
                System.err.println("Invalid Binary Operator!");
                return null;
        }
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
