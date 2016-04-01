/**
 * Driver. Tests Part A of Assignment 7
 * @author (Kevin Dittmar)
 * @author (William Ezekiel)
 * @author (Joseph Alacqua)
 * @version Mar 31 2016
 */
public class Driver {
  /**
   * Main function
   * @param args arguments array
   * Note: '=' user for evaluation
   *       '->' used for simplification
   */
  public static void main(String[] args) {
    Exp sum = new Sum(new Constant(3), new Constant(2)); // 3 + 2
    Exp diff = new Difference(sum, new Constant(1));  // (3+2) - 1
    Eval eval = new Eval();
    System.out.println(eval.visit(sum));  // 5
    System.out.println(eval.visit(diff)); // 4

    Exp sum2 = new Sum(new Constant(2), new Constant(3)); // 2 + 3
    Simplify simp = new Simplify();
    Exp result = simp.visit(new Difference(sum, sum2)); // (2+3) - (3+2) -> 0
    System.out.println(eval.visit(result)); // 0

    // Testing Variables
    Exp a = new Variable("a");
    Exp assign = new Assign((Variable) a,new Constant(17)); // a = 17
    Eval intrp = new Eval();
    try {
      intrp.visit(a); // assign is not evaluated, error occurs
    } catch(IllegalArgumentException iae) {
      System.out.println("Variable \'a\' is unassigned!"); // .out for now
    }
    intrp.visit(assign);
    try {
      intrp.visit(a);   // No Exception, assign has been evaluated
      System.out.println("Successfully evaluated variable \'a\'");
    } catch(IllegalArgumentException iae) {
      System.out.println("Variable \'a\' is unassigned!");
    }

    // Testing ToString
    Exp b = new Variable("b");
    Exp e = new Variable("e");
    assign = new Assign((Variable) e,new Quotient(a,
        new Sum(b, new Constant(2)))); // e = (a/(b+2))
    ToString toStr = new ToString();
    System.out.println(toStr.visit(assign));

    Exp five = new Constant(5);
    Exp three = new Constant(3);
    Exp two = new Constant(2);

    Exp product = new Product(new Sum(five,three),two); // (5 + 3) * 2
    System.out.println(toStr.visit(product));
    System.out.println(toStr.visit(product) + " = " + eval.visit(product)); // 16


    // Special Constants
    Exp zero = new Constant(0); // 0
    Exp one = new Constant(1); // 1

    // Remaining Tests for Sum
    sum = new Sum(zero,one); // 0 + 1 -> 1
    System.out.println(toStr.visit(sum) + " -> " + toStr.visit(simp.visit(sum)));

    sum = new Sum(a,a); // a + a = 2 * a
    System.out.println(toStr.visit(sum) + " -> " + toStr.visit(simp.visit(sum)));

    sum = new Sum(a,b);
    product = new Product(new Variable("v"),sum);
    sum = new Sum(product,product); // (v * (a+b)) + (v * (a+b)) = 2 * (v * (a+b))
    System.out.println(toStr.visit(sum) + " -> " + toStr.visit(simp.visit(sum)));

    // Testing Product
    System.out.println();
    product = new Product(one,two); // 1 * 2 = 2   1 * 2 -> 2
    System.out.println(toStr.visit(product) + " -> " + toStr.visit(simp.visit(product))); // 2
    System.out.println(toStr.visit(product) + " = " + eval.visit(product)); // 2

    product = new Product(zero,five); // 0 * 5 -> 0
    System.out.println(toStr.visit(product) + " -> " + toStr.visit(simp.visit(product))); // 0
    Quotient q = new Quotient(two,five);
    product = new Product(q,five); // (2/5) * 5 -> 2
    System.out.println(toStr.visit(product) + " -> " + toStr.visit(simp.visit(product))); // 2
    product = new Product(five,q); // 5 * (2/5) -> 2
    System.out.println(toStr.visit(product) + " -> " + toStr.visit(simp.visit(product))); // 2

    product = new Product(new Quotient(one,five),new Quotient(five,one)); //(1/5) * (5/1) -> 1
    System.out.println(toStr.visit(product) + " -> " + toStr.visit(simp.visit(product))); // 1

    // Testing Quotient
    System.out.println();
    Constant ten = new Constant(10);
    q = new Quotient(ten,two); // 10/2
    System.out.println(toStr.visit(q) + " = " + eval.visit(q)); // 5

    q = new Quotient(zero,five); // 0/5 -> 0
    System.out.println(toStr.visit(q) + " -> " + toStr.visit(simp.visit(q))); // 0

    try {
      q = new Quotient(zero,zero); // 0/0
      System.out.println(toStr.visit(q));
      System.out.println(toStr.visit(q) + " -> "
          + toStr.visit(simp.visit(q))); // won't print, error
    } catch(ArithmeticException ae) {
      System.out.println("Cannot divide by 0!"); // Will print this
    }

    q = new Quotient(five,five); // 5/5 -> 1
    System.out.println(toStr.visit(q) + " -> " + toStr.visit(simp.visit(q))); // 1

    q = new Quotient(five, new Quotient(five,ten)); // 5/(5/10) -> 10
    System.out.println(toStr.visit(q) + " -> " + toStr.visit(simp.visit(q))); // 10

    q = new Quotient(new Quotient(five,ten),five); // (5/10)/5 -> (1/10)
    System.out.println(toStr.visit(q) + " -> " + toStr.visit(simp.visit(q))); // (1/10)

    // Testing Mod
    System.out.println();
    Mod mod = new Mod(five,two); // 5 % 2
    System.out.println(toStr.visit(mod) + " = " + eval.visit(mod)); // 1

    mod = new Mod(five,five); // 5 % 5
    System.out.println(toStr.visit(mod) + " -> " + toStr.visit(simp.visit(mod))); // 0

    mod = new Mod(ten,one);  // 10 % 1
    System.out.println(toStr.visit(mod) + " -> " + toStr.visit(simp.visit(mod))); // 0

    mod = new Mod(one,zero); // 1 % 0

    try {
      System.out.println(toStr.visit(mod));
      System.out.println(toStr.visit(mod) + " -> "
          + toStr.visit(simp.visit(mod))); // won't print, error
    }
    catch(ArithmeticException ae) {
      System.out.println("Cannot divide by 0!"); // should print
    }

     mod = new Mod(zero,ten); // 0 % 10
    System.out.println(toStr.visit(mod) + " -> " + toStr.visit(simp.visit(mod))); // 0
  }
}
