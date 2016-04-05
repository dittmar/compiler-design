/**
 * An assign expression. Assigns a constant or expression to a variable.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 30 2016
 */
public class Assign extends Exp{

  /**
   * Create an Assign
   * @param v a variable
   * @param r the right expression
   */
  public Assign(Variable v, Exp r) {
    left = v;
    right = r;
  }

  /**
   * Accept a visitor.
   * @param v a visitor
   * @return the object produced by the visit.
   */
  public Object accept(Visitor v) {
    return v.visit(this);
  }
}
