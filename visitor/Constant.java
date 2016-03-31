/**
 * A numeric constant. Holds an integer value
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 29 2016
 */
public class Constant extends Exp {
  Integer value;

  /**
   * Create a constant
   * @param value an Integer constant
   */
  public Constant(Integer value) {
    this.value = value;
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
