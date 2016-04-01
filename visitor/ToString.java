/**
 * The ToString visitor. Returns the String representation of different expressions.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 31 2016
 */
public class ToString implements Visitor {

  /**
   * Visit a constant
   * @param n a constant
   * @return the string representation of the constant's value.
   */
  public String visit(Constant n) {
    return n.value.toString();
  }

  /**
   * Visit a Sum
   * @param n a sum
   * @return the string representation of this sum.
   */
  public String visit(Sum n) {
    return "("+ n.left.accept(this) + " + " + n.right.accept(this) + ")";
  }

  /**
   * Visit a Difference
   * @param n a difference
   * @return the string representation of this difference.
   */
  public String visit(Difference n) {
    return "(" + n.left.accept(this) +  " - " +  n.right.accept(this) + ")";
  }

  /**
   * Visit a Product
   * @param n a product
   * @return the string representation of this product.
   */
  public String visit(Product n) {
    return "(" + n.left.accept(this) + " * " + n.right.accept(this) + ")";
  }

  /**
   * Visit a Quotient
   * @param n a quotient
   * @return the string representation of this quotient.
   */
  public String visit(Quotient n) {
    return "(" + n.left.accept(this) + " / " + n.right.accept(this) + ")";
  }

  /**
   * Visit a Mod
   * @param n a mod
   * @return the string representation of this mod.
   */
  public String visit(Mod n) {
    return "(" + n.left.accept(this) + " % " + n.right.accept(this) + ")";
  }

  /**
   * Visit a Variable
   * @param n a variable
   * @return the string representation of this variable.
   */
  public String visit(Variable n) {
    return n.letter;
  }

  /**
   * Visit an Assign
   * @param n a assign
   * @return the string representation of this assign.
   */
  public String visit(Assign n) {
    return "(" + n.left.accept(this) + " = " +  n.right.accept(this) + ")";
  }

  /**
   * Visit any expression.
   * @param n an expression
   * @return the string representation of the expression.
   */
  public String visit(Exp n) {
    if (n instanceof Sum) {
      return visit((Sum) n);
    } else if (n instanceof Difference) {
      return visit((Difference) n);
    } else if (n instanceof Constant) {
      return visit((Constant) n);
    } else if (n instanceof Product) {
      return visit((Product) n);
    } else if (n instanceof Quotient) {
      return visit((Quotient) n);
    } else if (n instanceof Mod) {
      return visit((Mod) n);
    } else if (n instanceof Variable) {
      return visit((Variable) n);
    } else if (n instanceof Assign) {
      return visit((Assign) n);
    } else {
      throw new IllegalArgumentException(
          "Error:  unknown Exp " + n.getClass().getName()
      );
    }
  }
}
