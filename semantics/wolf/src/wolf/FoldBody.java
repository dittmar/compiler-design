package wolf;

import wolf.interfaces.ListArgument;
import wolf.interfaces.BinOp;
import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class FoldBody {
    BinOp bin_op;
    ListArgument list_argument;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
