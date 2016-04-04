package wolf;

import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class Branch implements WolfFunction {
    WolfFunction condition;
    WolfFunction true_branch;
    WolfFunction false_branch;
    
        public void accept(Visitor v) {
        v.visit(this);
    }
}
