public class Equals implements Visitor {
  public Object visit(Constant n) {
    return n.value;
  }

  public Object visit(Sum n) {
    return visit(n.left, n.right);
  }

  public Object visit(Difference n) {
    return visit(n.left, n.right);
  }

  public Object visit(Exp n) {
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

  private Object visit (Exp e1, Exp e2) {
    if (e1.getClass().equals(e2.getClass())) {
      if (e1 instanceof Sum) {
        return ((Boolean) visit(e1.left, e2.left) &&
                (Boolean) visit(e1.right, e2.right)) ||
               ((Boolean) visit(e1.left, e2.right) &&
                (Boolean) visit(e1.right, e2.left));
      } else if (e1 instanceof Difference) {
        return ((Boolean) visit(e1.left, e2.left) &&
                (Boolean) visit(e1.right, e2.right));
      } else if (e1 instanceof Constant) {
        return ((Constant) e1).value.equals(((Constant) e2).value);
      }
    }
    return new Boolean(false);
  }
}
