package wolf;

import wolf.interfaces.Arg;
import wolf.interfaces.BinOp;
import wolf.interfaces.ListArgument;
import wolf.interfaces.UnaryOp;
import wolf.interfaces.UserFuncName;
import wolf.interfaces.Visitor;
import wolf.node.TIdentifier;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class Identifier implements BinOp, UnaryOp, Arg, ListArgument,
    UserFuncName {
    TIdentifier identifier;
    
    public void accept(Visitor v) {
        // We'll be back
    }
}
