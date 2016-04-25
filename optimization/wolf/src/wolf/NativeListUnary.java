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
public class NativeListUnary extends WolfFunction implements UnaryOp {
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
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    /**
     * @return string representation of a list unary op
     */
    @Override
    public String toString() {
        return list_unary_op.toString() + "(" + list_argument.toString() + ")";
    }
}

