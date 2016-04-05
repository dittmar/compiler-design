package wolf;

import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;
import java.util.List;

/**
 * A WOLF program
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joseph Alacqua
 * @version Apr 3, 2016
 */
public class Program {
    List<Def> def_list;
    WolfFunction function;
    
    public Program(List<Def> def_list, WolfFunction function) {
        this.def_list = def_list;
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
