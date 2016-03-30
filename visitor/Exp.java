public abstract class Exp {
  Exp left;
  Exp right;

  public abstract Object accept(Visitor v);
}
