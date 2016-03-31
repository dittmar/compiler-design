public class Driver {
  public static void main(String[] args) {
    Exp sum = new Sum(new Constant(3), new Constant(2));
    Exp diff = new Difference(sum, new Constant(1));
    Eval eval = new Eval();
    System.out.println(eval.visit(sum));
    System.out.println(eval.visit(diff));

    Exp sum2 = new Sum(new Constant(2), new Constant(3));
    Simplify simp = new Simplify();
    Exp result = simp.visit(new Difference(sum, sum2));
    System.out.println(eval.visit(result));

    // Testing Variables
    Exp a = new Variable("a");
    Exp assign = new Assign((Variable) a,new Constant(17));
    Eval intrp = new Eval();
    try {
      intrp.visit(a);
    } catch(IllegalArgumentException iae) {
      System.out.println("Variable \'a\' is unassigned!"); // .out for now
    }
    intrp.visit(assign);
    try {
      intrp.visit(a);   // No Exception
      System.out.println("Successfully evaluated variable \'a\'");
    } catch(IllegalArgumentException iae) {
      System.out.println("Variable \'a\' is unassigned!");
    }

    // Testing ToString
    Exp b = new Variable("b");
    Exp e = new Variable("e");
    assign = new Assign((Variable) e,new Quotient(a, new Sum(b, new Constant(2))));
    ToString toStr = new ToString();
    System.out.println(toStr.visit(assign));

    Exp five = new Constant(5);
    Exp three = new Constant(3);
    Exp two = new Constant(2);

    Exp product = new Product(new Sum(five,three),two);
    System.out.println(toStr.visit(product));

    // Special Constants
    Exp zero = new Constant(0);
    Exp one = new Constant(1);

    // Remaining Tests for Sum
    sum = new Sum(zero,one);
    System.out.println(toStr.visit(sum) + " -> " + toStr.visit(simp.visit(sum)));

    sum = new Sum(a,a);
    System.out.println(toStr.visit(sum) + " -> " + toStr.visit(simp.visit(sum)));

    sum = new Sum(a,b);
    product = new Product(new Variable("v"),sum);
    sum = new Sum(product,product);
    System.out.println(toStr.visit(sum) + " -> " + toStr.visit(simp.visit(sum)));

    // Testing Product
    System.out.println();
    product = new Product(one,two);
    System.out.println(toStr.visit(product) + " -> " + toStr.visit(simp.visit(product))); // 2
    System.out.println(toStr.visit(product) + " = " + eval.visit(product)); // 2

    product = new Product(zero,five);
    System.out.println(toStr.visit(product) + " -> " + toStr.visit(simp.visit(product))); // 0
    Quotient q = new Quotient(two,five);
    product = new Product(q,five);
    System.out.println(toStr.visit(product) + " -> " + toStr.visit(simp.visit(product))); // 2
    product = new Product(five,q);
    System.out.println(toStr.visit(product) + " -> " + toStr.visit(simp.visit(product))); // 2

    product = new Product(new Quotient(one,five),new Quotient(five,one));
    System.out.println(toStr.visit(product) + " -> " + toStr.visit(simp.visit(product))); // 1

    // Testing Quotient
    System.out.println();
    Constant ten = new Constant(10);
    q = new Quotient(ten,two);
    System.out.println(toStr.visit(q) + " = " + eval.visit(q)); // 5

    q = new Quotient(zero,five);
    System.out.println(toStr.visit(q) + " -> " + toStr.visit(simp.visit(q))); // 0

    try {
      q = new Quotient(zero,zero);
      System.out.println(toStr.visit(q));
      System.out.println(toStr.visit(q) + " -> "
          + toStr.visit(simp.visit(q))); // won't print, error
    } catch(ArithmeticException ae) {
      System.out.println("Cannot divide by 0!"); // Will print this
    }

    q = new Quotient(five,five);
    System.out.println(toStr.visit(q) + " -> " + toStr.visit(simp.visit(q))); // 1

    // Testing Mod, Mods pls b& joe
    System.out.println();
    Mod mod = new Mod(five,two);
    System.out.println(toStr.visit(mod) + " = " + eval.visit(mod)); // 1

    mod = new Mod(five,five);
    System.out.println(toStr.visit(mod) + " -> " + toStr.visit(simp.visit(mod))); // 0

    mod = new Mod(ten,one);
    System.out.println(toStr.visit(mod) + " -> " + toStr.visit(simp.visit(mod))); // 0

    mod = new Mod(one,zero);

    try {
      System.out.println(toStr.visit(mod));
      System.out.println(toStr.visit(mod) + " -> "
          + toStr.visit(simp.visit(mod))); // won't print, error
    }
    catch(ArithmeticException ae) {
      System.out.println("Cannot divide by 0!"); // should print
    }

     mod = new Mod(zero,ten);
    System.out.println(toStr.visit(mod) + " -> " + toStr.visit(simp.visit(mod))); // 0







  }
  //Sum.accept(eval) = eval.visit(Sum)
  //                 = Const_3.accept(eval) + Const_2.accept(eval)
  //                 = eval.visit(Const_3) + eval.visit(Const_2)
  //                 = 3 + 2
  //                 = 5
}
