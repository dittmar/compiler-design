package wolf;

import wolf.interfaces.UnaryOp;
import wolf.interfaces.ListArgument;
import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A mapping function used in the WOLF language
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class WolfMap extends WolfFunction {
    UnaryOp unary_op;
    ListArgument list_argument;
    
    public WolfMap(UnaryOp unary_op, ListArgument list_argument) {
        this.unary_op = unary_op;
        this.list_argument = list_argument;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     * @return the type of this map function
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public String toString() {
        return ".(" + unary_op.toString() + "," + list_argument.toString() + ")";
    }
}
