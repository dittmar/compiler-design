package wolf;
import wolf.interfaces.Arg;
import java.util.List;
import wolf.interfaces.Args;
import wolf.interfaces.Visitor;
/**
 * A list of arguments
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Apr 3, 2016
 */
public class ListArgsList extends Args {
   public ListArgsList(List<Arg> arg_list) {
       super(arg_list);
   }
   /**
    * Accept a visitor.
    * @param v a visitor
    */
    public Type accept(Visitor v) {
       return v.visit(this);
    }
}

