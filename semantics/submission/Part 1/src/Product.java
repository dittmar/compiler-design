/**
 * A product expression. Represents multiplication of two expressions.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 29 2016
 */
public class Product extends Exp{

  /**
   * Create a Product
   * @param l the left expression
   * @param r the right expression
   */
  public Product(Exp l, Exp r) {
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
