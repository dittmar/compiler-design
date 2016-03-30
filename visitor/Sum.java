public class Sum extends Exp{
  public Sum(Exp l, Exp r) {
    left = l;
    right = r;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }
}
