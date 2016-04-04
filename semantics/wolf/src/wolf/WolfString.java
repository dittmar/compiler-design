package wolf;

import java.util.List;
import wolf.interfaces.Arg;
import wolf.interfaces.StringMiddle;
import wolf.interfaces.Visitor;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class WolfString implements Arg {
    List<StringMiddle> string_middle;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
