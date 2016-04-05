/**
 * A variable expression. A variable may or may not be assigned a value.
 * The left expression holds the constant when assigned.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 29 2016
 */
public class Variable extends Exp{

  String letter;

  /**
   * Create a Variable
   * @param letter a character to represent this variable
   */
  public Variable(String letter) {
    this.letter = letter;
  }

  /**
   * Accept a visitor.
   * @param v a visitor
   * @return the object produced by the visit.
   */
  public Object accept(Visitor v) {
    return v.visit(this);
  }

  /**
   * Assign an expression to this variable
   * @param e an expression
   */
  public void assign(Exp e) {
    left = e;
  }
}
