package wolf;

import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;
import wolf.node.TDef;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class Def {
    Identifier def_name;
    Sig sig;
    WolfFunction function;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
