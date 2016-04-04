package wolf;
import wolf.interfaces.Arg;
import java.util.List;
import wolf.interfaces.Visitor;
/**
 * A list of arguments
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Apr 3, 2016
 */
public class ArgList {
    List<Arg> arg_list;
    
   /**
    * Accept a visitor.
    * @param v a visitor
    */
    public void accept(Visitor v) {
        v.visit(this);
    }
}
