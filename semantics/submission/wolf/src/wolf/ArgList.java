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
    
    public ArgList(List<Arg> arg_list) {
        this.arg_list = arg_list;
    }
    
   /**
    * Accept a visitor.
    * @param v a visitor
    */
    public Object accept(Visitor v) {
       return v.visit(this);
    }
}
