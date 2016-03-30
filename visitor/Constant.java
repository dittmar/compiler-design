public class Constant extends Exp {
  Integer value;

  public Constant(Integer value) {
    this.value = value;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }
}
