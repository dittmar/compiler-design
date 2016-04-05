/**
 * The Simplify visitor. Simplifies any expression.
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 29 2016
 */
public class Simplify implements Visitor {

  /**
   * Simplify a Constant
   * @param n a constant
   * @return the given constant, it's already simplified.
   */
  public Exp visit(Constant n) {
    return n;
  }

  /**
   * Simplify a variable
   * @param n a variable
   * @return the given variable, it's already simplified.
   */
  public Exp visit(Variable n) {
    return n;
  }

  /**
   * Simplify a Sum
   * @param n a sum
   * @return the simplified sum
   */
  public Exp visit(Sum n) {
    Exp simpLeft = (Exp) n.left.accept(this);
    Exp simpRight = (Exp) n.right.accept(this);
    Exp result = new Sum(simpLeft,simpRight);
    Sum zeroLeft = new Sum(new Constant(0), simpLeft);
    // is right 0
    Sum zeroRight = new Sum(new Constant(0),simpRight);
    Equals eq = new Equals();

    if((Boolean) eq.visit(zeroLeft)) {
      return simpRight;
    }
    if((Boolean) eq.visit(zeroRight)) {
      return simpLeft;
    }
    if((Boolean) eq.visit(result)) {
      return new Product(new Constant(2),simpLeft);
    }

    return result;
  }

  /**
   * Simplify a Difference
   * @param n a difference
   * @return the simplified difference
   */
  public Exp visit(Difference n) {
    Exp result =
      new Difference((Exp) n.left.accept(this), (Exp) n.right.accept(this));
    Equals eq = new Equals();
    if ((Boolean) eq.visit(result)) {
      return new Constant(0);
    }
    return result;
    
  }

  /**
   * Simplify a Product
   * @param n a product
   * @return the simplified product
   */
  public Exp visit(Product n) {
    Exp simpLeft = (Exp) n.left.accept(this);
    Exp simpRight = (Exp) n.right.accept(this);
    Exp result =
        new Product (simpLeft, simpRight);
    Equals eq = new Equals();
    // is left 0
    Product zeroLeft = new Product(new Constant(0), simpLeft);
    // is right 0
    Product zeroRight = new Product(new Constant(0),simpRight);

    if ((Boolean) eq.visit(zeroLeft) || (Boolean)eq.visit(zeroRight)) {
      return new Constant(0);
    }

    Exp productWithQuotientResult;
    if((productWithQuotientResult = productQuotientSimplification(simpLeft,simpRight)) != null) {
      return productWithQuotientResult;
    }

    if((productWithQuotientResult = productQuotientSimplification(simpRight,simpLeft)) != null) {
      return productWithQuotientResult;
    }

    Exp productWithTwoQuotientsResult;
    if((productWithTwoQuotientsResult = productDoubleQuotientSimplification(simpLeft,simpRight)) != null) {
      return productWithTwoQuotientsResult;
    }

    // is left 1
    Product oneLeft = new Product(new Constant(1),simpLeft);
    // is right 1
    Product oneRight = new Product(new Constant(1),simpRight);

    if((Boolean) eq.visit(oneLeft)) {
      return simpRight;
    }
    if((Boolean) eq.visit(oneRight)) {
      return simpLeft;
    }
    return result;
  }

  /**
   * Simplification in which a quotient and simplified expression are multiplied. The denominator of
   * the quotient is equal to the other expression.
   * (b/a) * a = b
   * @param left the left expression.
   * @param right the right expression
   * @return a simplified version of product or null if it cannot be simplified.
   */
  private Exp productQuotientSimplification(Exp left, Exp right) {
    Equals eq = new Equals();
    if(left instanceof Quotient) {
      Exp denom = left.right;
      Product p = new Product(denom,right);
      if((Boolean) eq.visit(p)) {
        return left.left;
      }
    }
    return null;
  }

  /**
   * Simplification in which the left and right of a product are both quotients that
   * are reciprocals of one another.
   * (a/b) * (b/a) = 1
   * @param left the left expression
   * @param right the right expression.
   * @return a simplified version of product or null if it cannot be simplified.
   */
  private Exp productDoubleQuotientSimplification(Exp left, Exp right) {
    Equals eq = new Equals();
    if(left instanceof Quotient && right instanceof Quotient) {
      Product p1 = new Product(left.left,right.right);
      Product p2 = new Product(left.right,right.left);
      if((Boolean) eq.visit(p1) && (Boolean) eq.visit(p2)) {
        return new Constant(1);
      }
    }
    return null;
  }

  /**
   * Simplify a Quotient
   * @param n a quotient
   * @return the simplified quotient
   */
  public Exp visit(Quotient n) {
    Exp simpLeft = (Exp) n.left.accept(this);
    Exp simpRight = (Exp) n.right.accept(this);
    Exp result =
        new Quotient (simpLeft, simpRight);
    Equals eq = new Equals();
    // is left 0
    Quotient zeroLeft = new Quotient(new Constant(0), simpLeft);
    // is right 0
    Quotient zeroRight = new Quotient (new Constant(0),simpRight);

    if((Boolean) eq.visit(zeroRight)) {
      throw new ArithmeticException("Attempt to Divide by 0!");
    }

    if ((Boolean) eq.visit(zeroLeft)) {
      return new Constant(0);
    }

    // safe to check if both are equal
    if((Boolean) eq.visit(result)) {
      return new Constant(1);
    }

    // is right 1
    Quotient oneRight = new Quotient(new Constant(1),simpRight);

    if((Boolean) eq.visit(oneRight)) {
      return simpLeft;
    }

    Exp rightIsQuotientSimplified;
    if((rightIsQuotientSimplified =  quotientInQuotientSimplification(simpLeft,simpRight)) != null) {
      return rightIsQuotientSimplified;
    }

    Exp leftIsQuotientSimplified;
    if((leftIsQuotientSimplified =  quotientInQuotientSimplification(simpRight,simpLeft)) != null) {
      return new Quotient(new Constant(1), leftIsQuotientSimplified);
    }

    return result;
  }


  /**
   * Simplification in which a quotient contains one quotient. Two possible simplifications.
   * a/(a/b) = b  or  (a/b)/a = (1/b)
   * @param left the left expression.
   * @param right the right expression
   * @return a simplified version of quotient or null if it cannot be simplified.
   */
  private Exp quotientInQuotientSimplification(Exp left, Exp right) {
    Equals eq = new Equals();
    if(right instanceof Quotient) {
      Quotient q = new Quotient(left, right.left);
      if((Boolean) eq.visit(q)) {
        return right.right;
      }
    }
    return null;
  }

  /**
   * Simplify a Mod
   * @param n a mod
   * @return the simplified mod
   */
  public Exp visit(Mod n) {
    Exp simpLeft = (Exp) n.left.accept(this);
    Exp simpRight = (Exp) n.right.accept(this);
    Exp result =
        new Mod (simpLeft, simpRight);
    Equals eq = new Equals();
    // is left 0
    Mod zeroLeft = new Mod(new Constant(0), simpLeft);
    // is right 0
    Mod zeroRight = new Mod (new Constant(0),simpRight);
    // is right 1
    Mod oneRight = new Mod(new Constant(1),simpRight);

    if((Boolean) eq.visit(zeroRight)) {
      throw new ArithmeticException("Attempt to Divide by 0!");
    }

    if ((Boolean) eq.visit(zeroLeft) || (Boolean) eq.visit(result) ||
        (Boolean) eq.visit(oneRight)) {
      return new Constant(0);
    }
    return result;
  }

  /**
   * Simplify an Assign
   * @param n an Assign
   * @return the simplified assign
   */
  public Exp visit(Assign n) {
    return new Assign((Variable) n.left.accept(this),(Exp) n.right.accept(this));
  }



  /**
   * Vist any Expression
   * @param n an expression
   * @return the simplified expression
   */
  public Exp visit(Exp n) {
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
