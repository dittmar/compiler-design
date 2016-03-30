public class Eval implements Visitor {
  public Integer visit(Constant n) {
    return n.value;
  }

  public Integer visit(Sum n) {
    return (Integer) n.left.accept(this) + (Integer) n.right.accept(this);
  }

  public Integer visit(Difference n) {
    return (Integer) n.left.accept(this) - (Integer) n.right.accept(this);
  }

  public Integer visit(Exp n) {
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
