/**
 * The Eval visitor. Evaluates any expression.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 29 2016
 */
public class Eval implements Visitor {

  /**
   * Visit a constant
   * @param n a constant
   * @return the value of the constant.
   */
  public Integer visit(Constant n) {
    return n.value;
  }

  /**
   * Visit a Sum
   * @param n a sum
   * @return the sum of the evaluated left and right expressions.
   */
  public Integer visit(Sum n) {
    return (Integer) n.left.accept(this) + (Integer) n.right.accept(this);
  }

  /**
   * Visit a Difference
   * @param n a difference
   * @return the difference of the evaluated left and right expressions.
   */
  public Integer visit(Difference n) {
    return (Integer) n.left.accept(this) - (Integer) n.right.accept(this);
  }

  /**
   * Visit a Product
   * @param n a product
   * @return the product of the evaluated left and right expressions.
   */
  public Integer visit(Product n) {
    return (Integer) n.left.accept(this) * (Integer) n.right.accept(this);
  }

  /**
   * Visit a Quotient
   * @param n a quotient
   * @return the quotient of the evaluated left and right expressions.
   */
  public Integer visit(Quotient n) {
    return (Integer) n.left.accept(this) / (Integer) n.right.accept(this);
  }

  /**
   * Visit a Mod
   * @param n a mod
   * @return the mod of the evaluated left and right expressions.
   */
  public Integer visit(Mod n) {
    return (Integer) n.left.accept(this) % (Integer) n.right.accept(this);
  }

  /**
   * Visit a Variable
   * @param n a variable
   * @return the value of the evaluated variable. Throws exception if variable is unassigned.
   */
  public Integer visit(Variable n) {
    if(n.left != null) {
      return (Integer) n.left.accept(this);
    }
    throw new IllegalArgumentException("Variable is unassigned!");
  }

  /**
   * Visit an Assign
   * @param n a assign
   * @return the mod of the evaluated assign.
   */
  public Integer visit(Assign n) {
    Variable left = (Variable) n.left;
    Integer evalRight = (Integer) n.right.accept(this);
    left.assign(new Constant(evalRight));
    return evalRight;
  }

  /**
   * Visit any expression.
   * @param n an expression
   * @return the numeric value after the expression has been evaluated.
   */
  public Integer visit(Exp n) {
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
