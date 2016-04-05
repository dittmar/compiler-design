/**
 * The Equals visitor. Checks if the left and right side of an expression are equal.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 29 2016
 */
public class Equals implements Visitor {

  /**
   * Visit a constant
   * @param n a constant
   * @return the value of the constant.
   */
  public Object visit(Constant n) {
    return n.value;
  }


  /**
   * Visit a Sum
   * @param n a sum
   * @return true if the left and right expressions of this sum are equal.
   */
  public Object visit(Sum n) {
    return visit(n.left, n.right);
  }


  /**
   * Visit a Difference
   * @param n a difference
   * @return true if the left and right expressions of this difference
   *    are equal.
   */
  public Object visit(Difference n) {
    return visit(n.left, n.right);
  }

  /**
   * Visit a Product
   * @param n a product
   * @return true if the left and right expressions of this product
   *    are equal.
   */
  public Object visit(Product n) {
    return visit(n.left, n.right);
  }

  /**
   * Visit a Quotient
   * @param n a quotient
   * @return true if the left and right expressions of this quotient
   *    are equal.
   */
  public Object visit(Quotient n) {
    return visit(n.left, n.right);
  }

  /**
   * Visit a Mod
   * @param n a mod
   * @return true if the left and right expressions of this mod
   *    are equal.
   */
  public Object visit(Mod n) {
    return visit(n.left, n.right);
  }

  /**
   * Visit a Variable
   * @param n a variable
   * @return true if the variable are equal.
   */
  public Object visit(Variable n) {
    return n.letter;
  }

  /**
   * Visit a Assign
   * @param n an assign
   * @return true if the left and right expressions of this assign
   *    are equal.
   */
  public Object visit(Assign n) {
    return visit(n.left, n.right);
  }



  /**
   * Visit any expression.
   * @param n an expression
   * @return an object containing the result of the visit.
   */
  public Object visit(Exp n) {
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

  /**
   * Visits two expressions. Checks if they are equal.
   * @param e1 an expression
   * @param e2 an expression
   * @return true if the two expressions are equal
   */
  private Object visit (Exp e1, Exp e2) {
    if (e1.getClass().equals(e2.getClass())) {
      if (e1 instanceof Sum || e1 instanceof Product) {
        return ((Boolean) visit(e1.left, e2.left) &&
                (Boolean) visit(e1.right, e2.right)) ||
               ((Boolean) visit(e1.left, e2.right) &&
                (Boolean) visit(e1.right, e2.left));
      } else if (e1 instanceof Difference || e1 instanceof Quotient || e1 instanceof Mod ||
          e1 instanceof Assign) {
        return ((Boolean) visit(e1.left, e2.left) &&
                (Boolean) visit(e1.right, e2.right));
      } else if (e1 instanceof Constant) {
        return ((Constant) e1).value.equals(((Constant) e2).value);
      } else if (e1 instanceof Variable) {
        if(e1.left == null && e2.left == null) {
          return true;
        }
        else if(e1.left != null && e2.left != null) {
          return visit(e1.left, e2.left);
        }
        else {
          return false;
        }
      }
    }
    return new Boolean(false);
  }
}
