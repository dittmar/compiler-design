package wolf;

import wolf.interfaces.Arg;
import wolf.interfaces.Visitor;
import wolf.node.TFloatNumber;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class FloatLiteral implements Arg {
    TFloatNumber float_literal;
    public void accept(Visitor v) {
        v.visit(this);
    }
}
