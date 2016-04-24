package wolf;

import wolf.interfaces.Arg;
import wolf.interfaces.ListElement;
import wolf.interfaces.Visitor;
import wolf.node.TInputArgNumber;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 24, 2016
 */
public class InputArg extends Arg implements ListElement {
    Type type;
    TInputArgNumber arg_number;
    
    public InputArg(Type type, TInputArgNumber arg_number) {
        this.type = type;
        this.arg_number = arg_number;
    }
    
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
