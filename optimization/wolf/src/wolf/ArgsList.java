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
public class ArgsList extends Args {
    public ArgsList(List<Arg> arg_list) {
        super(arg_list);
    }
    
   /**
    * Accept a visitor.
    * @param v a visitor
    * @return type should never be returned from this type of Args
    */
    @Override
    public Object accept(Visitor v) {
      return v.visit(this);
    }
}
