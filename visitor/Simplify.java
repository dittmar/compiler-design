public class Simplify implements Visitor {
  public Exp visit(Constant n) {
    return n;
  }

  public Exp visit(Sum n) {
    return new Sum((Exp) n.left.accept(this), (Exp) n.right.accept(this));
  }

  public Exp visit(Difference n) {
    Exp result =
      new Difference((Exp) n.left.accept(this), (Exp) n.right.accept(this));
    Equals eq = new Equals();
    if ((Boolean) eq.visit(result)) {
      return new Constant(0);
    }
    return result;
    
  }

  public Exp visit(Exp n) {
    if (n instanceof Sum) {
      return visit((Sum) n);
    } else if (n instanceof Difference) {
      return visit((Difference) n);
    } else if (n instanceof Constant) {
      return visit((Constant) n);
    } else {
      throw new IllegalArgumentException(
        "Error:  unknown Exp " + n.getClass().getName()
      );
    }
  }
}
