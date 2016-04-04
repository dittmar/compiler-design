package wolf;

import wolf.interfaces.Arg;
import wolf.interfaces.Visitor;
import wolf.node.TIntNumber;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class IntLiteral implements Arg {
    TIntNumber int_literal;
    
    public void accept(Visitor v) {
        
    }
}
