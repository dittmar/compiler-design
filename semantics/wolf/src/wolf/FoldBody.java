package wolf;

import wolf.interfaces.ListArgument;
import wolf.interfaces.BinOp;
import wolf.interfaces.Visitor;

/**
 * The body of a fold function
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class FoldBody {
    BinOp bin_op;
    ListArgument list_argument;
    
    /**
     * Accept a visitor
     * @param v a visitor
     */
    public void accept(Visitor v) {
        v.visit(this);
    }
}
