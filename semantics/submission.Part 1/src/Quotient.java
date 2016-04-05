/**
 * A quotient expression. Represents division of two expressions.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 29 2016
 */
public class Quotient extends Exp{

  /**
   * Create a Quotient
   * @param l the left expression
   * @param r the right expression
   */
  public Quotient(Exp l, Exp r) {
    left = l;
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
