package wolf;

import wolf.interfaces.StringMiddle;
import wolf.interfaces.Visitor;
import wolf.node.TStringBody;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class StringBody implements StringMiddle {
    TStringBody string_body;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
