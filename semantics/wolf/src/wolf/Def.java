package wolf;

import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A function definition.
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class Def {
    Identifier def_name;
    Sig sig;
    WolfFunction function;
    
    public Def(Identifier def_name, Sig sig, WolfFunction function) {
        this.def_name = def_name;
        this.sig = sig;
        this.function = function;
    }
    
    /**
     * Accept a visitor
     * @param v a visitor
     */
    public void accept(Visitor v) {
        v.visit(this);
    }
}
