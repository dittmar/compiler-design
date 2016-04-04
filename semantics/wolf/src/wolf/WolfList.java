package wolf;

import wolf.interfaces.ListArgument;
import wolf.interfaces.Arg;
import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class WolfList implements Arg, ListArgument {
    ArgList arg_list;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
