package wolf;

import wolf.interfaces.Visitor;
import wolf.interfaces.WolfFunction;

/**
 * A branch, works like an if-else statement. 
 * @author Kevin Dittmar
 * @author William Ezekiel
 * @author Joe Alacqua
 * @version Apr 3, 2016
 */
public class Branch extends WolfFunction {
    WolfFunction condition;
    WolfFunction true_branch;
    WolfFunction false_branch;
    
    public Branch (WolfFunction condition, WolfFunction true_branch,
        WolfFunction false_branch) {
        this.condition = condition;
        this.true_branch = true_branch;
        this.false_branch = false_branch;
    }
    
   /**
    * Accept a visitor.
    * @param v a visitor
    * @return return type of the branch
    */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" ; ").append(condition).append(" ? ").append(true_branch).append(" : ")
      .append(false_branch);
    return sb.toString();
  }
}
