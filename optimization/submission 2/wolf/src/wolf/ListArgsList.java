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
     * Accept a visitor
     * @param v a visitor
     * @return type of the list arguments
     */
   @Override
    public Object accept(Visitor v) {
       return v.visit(this);
    }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("(");
    for(int i = 0; i < getArgList().size()-1; i++) {
      sb.append(getArgList().get(i)).append(", ");
    }
    sb.append(getArgList().get(getArgList().size()-1));
    sb.append(")");
    return sb.toString();
  }
}

