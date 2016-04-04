package wolf;

import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A branch, works like an if-else statement. 
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joe Alacqua
 * @version Apr 3, 2016
 */
public class Branch implements WolfFunction {
    WolfFunction condition;
    WolfFunction true_branch;
    WolfFunction false_branch;
    
   /**
    * Accept a visitor.
    * @param v a visitor
    */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
