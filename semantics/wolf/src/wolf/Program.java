package wolf;

import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;
import java.util.List;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class Program {
    List<Def> def_list;
    WolfFunction function;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
