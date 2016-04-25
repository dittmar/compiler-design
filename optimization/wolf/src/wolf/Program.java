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
     * @return It doesn't matter.  Program won't be accepted.
     */
    public Object accept(Visitor v) {
        return v.visit(this);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Def def : def_list) {
            sb.append(def.toString()).append("\n");
        }
        return sb.append(function.toString()).toString();
    }
}
