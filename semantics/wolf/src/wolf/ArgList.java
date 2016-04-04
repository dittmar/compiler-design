package wolf;
import wolf.interfaces.Arg;
import java.util.List;
import wolf.interfaces.Visitor;
/**
 *
 * @author Kevin Dittmar
 * @version Apr 3, 2016
 */
public class ArgList {
    List<Arg> arg_list;
    
    public void accept(Visitor v) {
        v.visit(this);
    }
}
