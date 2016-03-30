public class Difference extends Exp {
  public Difference(Exp l, Exp r) {
    left = l;
    right = r;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }
}
