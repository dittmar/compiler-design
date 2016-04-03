package wolf;

import java.util.List;

/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class Program {
    List<Def> def_list;
    Function function;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
