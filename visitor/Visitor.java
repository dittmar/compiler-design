public interface Visitor {
  Object visit(Sum n);
  Object visit(Difference n);
  Object visit(Constant n);
}
