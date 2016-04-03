package wolf;

import wolf.node.TDef;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class Def {
    TDef def_name;
    Sig sig;
    Function function;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
