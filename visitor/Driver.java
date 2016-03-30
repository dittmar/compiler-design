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
  }
  //Sum.accept(eval) = eval.visit(Sum)
  //                 = Const_3.accept(eval) + Const_2.accept(eval)
  //                 = eval.visit(Const_3) + eval.visit(Const_2)
  //                 = 3 + 2
  //                 = 5
}
